package Server;

public class Account {
    private final String login;
    private final String pass;
    private boolean isOnline;

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

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnline() {
        return isOnline;
    }

}
