package dk.jost.danskebank.pojo;

import java.util.Date;

/**
 * Encapsulates the transaction that can be added to an account.
 */
public class Transaction implements Comparable<Transaction> {

    private double amount;

    private Date transactiondate;

    private Transaction() {
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.transactiondate = new Date();
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactiondate() {
        return transactiondate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", transactiondate=" + transactiondate +
                '}';
    }

    @Override
    public int compareTo(Transaction o) {
        return o.transactiondate.compareTo(this.transactiondate);
    }
}
