package pl.db.gogreen.transaction;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.db.gogreen.transaction.model.Account;
import pl.db.gogreen.transaction.model.Transaction;
import pl.db.gogreen.transaction.service.ReportService;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final ReportService reportService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/transactions/report",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Stream<Account> report(
            @Parameter(name = "Transaction", required = true)
            @Valid @Size(max = 100000) @RequestBody List<Transaction> transaction) {
        return reportService.prepareAccountNetOperations(transaction);
    }

}
