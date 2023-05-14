package pl.db.gogreen.transaction.service;

import org.springframework.stereotype.Service;
import pl.db.gogreen.transaction.model.Account;
import pl.db.gogreen.transaction.model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService {

    public Stream<Account> prepareAccountNetOperations(List<Transaction> transactions) {
        ConcurrentMap<String, List<Account>> accountsSubTransactions = transactions.parallelStream().<Account>mapMulti((tranasction, consumer) -> {
            Account creditAccount = Account.builder()
                    .account(tranasction.getCreditAccount())
                    .creditCount(1)
                    .debitCount(0)
                    .balance(tranasction.getAmount())
                    .build();
            Account debitAccount = Account.builder()
                    .account(tranasction.getDebitAccount())
                    .creditCount(0)
                    .debitCount(1)
                    .balance(tranasction.getAmount().negate())
                    .build();
            consumer.accept(creditAccount);
            consumer.accept(debitAccount);
        }).collect(Collectors.groupingByConcurrent(Account::getAccount));

        return accountsSubTransactions
                .values()
                .parallelStream()
                .flatMap(x -> x.stream().reduce(Account::sumAccounts).stream())
                .sorted(Comparator.comparing(Account::getAccount));
    }
}
