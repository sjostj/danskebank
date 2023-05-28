package dk.jost.danskebank;

import dk.jost.danskebank.pojo.Account;
import dk.jost.danskebank.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/v1/accounts")
@Component
public class AccountController
{
    @Autowired
    public AccountRepository accountRepository;

    @GetMapping
    public Collection<Account> all() {
        Collection<Account> rv = accountRepository.getAccounts();
        return accountRepository.getAccounts();
    }

    @PostMapping
    public ResponseEntity<Integer> createAccount(@RequestBody String name) {
        Account account = accountRepository.createAccount(name);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();
        return ResponseEntity.created(location).body(account.getId());
    }

    @GetMapping(value = "{id}")
    public Account getAccountByid(@PathVariable int id) {
        return getAccount(id);
    }

    @PostMapping(value = "{id}/transactions")
    public ResponseEntity<Void> createTransaction(@PathVariable int id, @RequestBody double amount) {
        Transaction t = new Transaction(amount);
        Account account = getAccount(id);
        if (account.getBalance() + amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance would become negative.");
        }
        accountRepository.createTransaction(account, t);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/transactions")
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{id}/transactions")
    public Collection<Transaction> getTransactions(@PathVariable int id) {
        Account a = getAccount(id);
        return a.getTransactions().subList(0, Math.min(10, a.getTransactions().size()));
    }

    // Utility functions
    private Account getAccount(int id) {
        Account rv = accountRepository.getAccount(id);
        if (rv == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return rv;
    }

}
