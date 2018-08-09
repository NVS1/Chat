package Server;

import Server.Json.JsonAccounts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class AccountList {
    private final Gson gson = new GsonBuilder().create();
    private static final AccountList accountList = new AccountList();
    private final Map<String, Account> accountMap = new HashMap<>();

    private AccountList() {
    }
    public static AccountList getInstance (){
        return accountList;
    }

    public synchronized boolean add (String login, Account account){
        if (accountMap.containsKey(login)){
            return false;
        }
        accountMap.put(login,account);
        return true;
    }
    public synchronized Account get (String login) {
        return accountMap.get(login);
    }
    public synchronized boolean isRegistered (String login){
        return accountMap.containsKey(login);
    }
    public synchronized String toJSON (){
        return gson.toJson(new JsonAccounts(accountMap));
    }
}
