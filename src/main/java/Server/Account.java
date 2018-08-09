package Server;

import Server.Json.JsonMessages;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;
import java.util.List;

public class Account {
    private final String login;
    private final String pass;
    private boolean isOnline;
    private final List<Message> messages = new LinkedList<>();

    public Account(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.isOnline = false;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setOnline(boolean status) {
        isOnline = status;
    }

    public boolean isOnline() {
        return isOnline;
    }
    public void addMsg (Message message){
        messages.add(message);
    }
    public String toJSON (int n){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(new JsonMessages(messages, n));
    }
}
