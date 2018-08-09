package Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

//1.Добавить  ф-ю  авторизациипользователей.
//2.Добавить  ф-ю  приватныхсообщений.
//3.Добавить  ф-ю  получения  списка  всех  пользователей.
//4.Добавить  ф-ю  чат-комнат.
//5.Добавить  ф-ю  проверки  статуса  пользователя.
// command /help view all commands

public class Main {
    static String login;
    static String room = "global";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int statusCode = 0;
            while (statusCode != 200) {
                statusCode = registration(scanner);
            }

            GetThread getThread = startChat();

            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()){
                    logout(login);
                    return;
                }else if (text.equals("/create")){
                    System.out.println("Enter room name");
                    String n = scanner.nextLine();
                    int resp = createRoom(n);
                    if (resp == 200){
                        System.out.println("Room "+n+" created");
                        room = n;
                        changeRoom(n,getThread);
                    } else {
                        System.out.println("this room is present");
                    }
                } else if (text.equals("/come")){
                    System.out.println("Enter room name");
                    room = scanner.nextLine();
                    changeRoom(room,getThread);
                } else if (text.equals("/rooms")){
                    getRooms();
                }else if (text.equals("/users")){
                    getUsers();
                } else if (text.equals("/pm")){
                    myMsg();
                } else if (text.equals("/help")){
                    help();
                }  else {
                    String[] split = text.split(":");
                    String to = null;
                    text = split[0];
                    if (split.length > 1) {
                        to = split[0];
                        text = split[1];
                    }
                    Message m = new Message(login, text, to, room);
                    int res = m.send(Utils.getURL() + "/add");
                    if (res == 404) {
                        System.out.println("Recipient not found");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static GetThread startChat() {
        GetThread getThread = new GetThread(room);
        Thread roomMsgReader = new Thread(getThread);
        roomMsgReader.setDaemon(true);
        roomMsgReader.start();

        Thread myMsgReader = new Thread(new GetMyMsgThread(login));
        myMsgReader.setDaemon(true);
        myMsgReader.start();
        return getThread;
    }
    private static int registration(Scanner scanner) throws IOException {
        int r;
        System.out.println("Enter your login: ");
        login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String pass = scanner.nextLine();
        Account account = new Account(login, pass);
        r = account.sign("sign_in");
        if (r == 400) {
            System.out.println("Illegal password");
        } else if (r == 404) {
            System.out.println("server.Account not register");
            System.out.println("Register?");
            System.out.println("Y/N");
            String resp = scanner.nextLine();
            switch (resp) {
                case "Y":
                    int status = account.sign("sign_up");
                    if (status == 400) {
                        System.out.println("This login already used");
                    } else {
                        System.out.println("Account create. Login " + account.getLogin() + "; " +
                                "Password " + account.getPass());
                    }
                    break;
                case "N":
                    break;
                default:
                    System.out.println("Wrong");
            }
        }
        return r;
    }
    private static int createRoom(String name) throws IOException {
        URL url = new URL(Utils.getURL() + "/create?name=" + name);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        return connection.getResponseCode();
    }
    private static void changeRoom(String name, GetThread getThread) {
        getThread.setRoom(name);
    }
    private static void logout(String login)throws IOException {
        URL url = new URL("http://localhost:8080/logout?login=" + login);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        int response = http.getResponseCode();
    }
    private static void getRooms()throws IOException {
        URL url = new URL(Utils.getURL() + "/rooms");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        InputStream is = connection.getInputStream();
        try {
            byte[] buf = ComonOperation.requestBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            JsonRooms rooms = gson.fromJson(strBuf, JsonRooms.class);
            if (rooms != null) {
                for (String s : rooms.getList()) {
                    System.out.println(s);
                }
            }
        } finally {
            is.close();
        }
    }
    private static void getUsers() throws IOException {
        URL url = new URL(Utils.getURL() + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        InputStream is = connection.getInputStream();
        try {
            byte[] buf = ComonOperation.requestBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            JsonAccounts accounts = gson.fromJson(strBuf, JsonAccounts.class);
            if (accounts != null) {
                for (Map.Entry<String, Boolean> stringAccountEntry : accounts.getList().entrySet()) {
                    System.out.println(stringAccountEntry);
                }
            }
        } finally {
            is.close();
        }
    }
    private static void myMsg() throws IOException {
        URL url = new URL(Utils.getURL() + "/my_msg?from=0&login=" + login);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        if (http.getResponseCode() == 404) {
            System.out.println("recipient not found");
        }
        InputStream is = http.getInputStream();
        try {
            byte[] buf = ComonOperation.requestBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
            if (list != null) {
                for (Message m : list.getList()) {
                    System.out.println(m);
                }
            }
        } finally {
            is.close();
        }
    }
    private static void help(){
        System.out.println("/create + enter = create new room");
        System.out.println("/rooms + enter = list available rooms");
        System.out.println("/come + enter = go into the room");
        System.out.println("/users + enter = list users");
        System.out.println("/pm + enter = view all personal messages");
        System.out.println("user_name: .... = personal message");
        System.out.println("empty message = logout");
    }
}
