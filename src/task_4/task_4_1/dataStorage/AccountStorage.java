package task_4.task_4_1.dataStorage;

import java.util.HashMap;
import java.util.Map;

public class AccountStorage {

    private static int countId = 100;
    private static final Map<Integer,Account> accountMap = new HashMap<>(){
        {
            put(++countId, new Account("Валерий", "0000"));
            put(++countId, new Account("Ирина", "2022"));
        }
    };

    public static Map<Integer, Account> getAccountMap() {
        return accountMap;
    }

    public static void addAccount(Account account) {
        accountMap.put(++countId, account);
    }

}