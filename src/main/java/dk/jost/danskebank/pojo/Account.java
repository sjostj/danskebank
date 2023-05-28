package dk.jost.danskebank.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Encapsulates the account and the functionality needed to add a transaction to the account
 */
public class Account {

    private Integer id;

    private String name;

    private double balance = 0;

    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public Account(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance += transaction.getAmount();
        Collections.sort(transactions);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
