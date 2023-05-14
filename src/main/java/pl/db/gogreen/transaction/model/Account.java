package pl.db.gogreen.transaction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Account {

    @Size(min = 26, max = 26)
    @Schema(name = "account", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String account;

    @Schema(name = "debitCount", example = "2", description = "Number of debit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer debitCount;

    @Schema(name = "creditCount", example = "2", description = "Number of credit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer creditCount;

    @Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BigDecimal balance;

    public static Account sumAccounts(Account account1, Account account2) {
        return Account.builder().account(account1.account)
                .debitCount(account1.debitCount + account2.debitCount)
                .creditCount(account1.creditCount + account2.creditCount)
                .balance(account1.balance.add(account2.balance))
                .build();
    }

}
