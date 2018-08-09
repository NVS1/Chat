package Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Account {
    private final String login;
    private final String pass;
    private boolean isOnline;

    public Account(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
    public int sign (String InOrUp) throws IOException {
        URL url = new URL(Utils.getURL() + "/"+InOrUp+"?login="+login+"&pass="+pass);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        return connection.getResponseCode();
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
