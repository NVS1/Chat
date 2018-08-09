package Server.Json;

import Server.Account;

import java.util.*;

public class JsonAccounts {
    private final Map<String, Boolean> accounts = new HashMap<>();

    public JsonAccounts(Map<String, Account> accountMap) {
        for (Map.Entry<String, Account> stringAccountEntry : accountMap.entrySet()) {
            accounts.put(stringAccountEntry.getKey(),stringAccountEntry.getValue().isOnline());
        }
    }
    public Map<String, Boolean> getList() {
      return accounts;
    }
}
