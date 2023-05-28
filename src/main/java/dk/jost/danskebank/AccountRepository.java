package dk.jost.danskebank;

import dk.jost.danskebank.pojo.Account;
import dk.jost.danskebank.pojo.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountRepository {

    private Map<Integer, Account> accounts = new HashMap<Integer, Account>();


    public AccountRepository() {
    }

    public Account createAccount(String name) {
        Account account = new Account(accounts.size() + 1, name);
        accounts.put(account.getId(), account);
        return account;
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    public void createTransaction(Account account, Transaction transaction) {
        account.addTransaction(transaction);
    }

    public Account getAccount(int id) {
        return accounts.get(id);
    }
}
