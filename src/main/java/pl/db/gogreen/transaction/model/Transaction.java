package pl.db.gogreen.transaction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class Transaction {

    @Size(min = 26, max = 26)
    @Schema(name = "debitAccount", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String debitAccount;

    @Size(min = 26, max = 26)
    @Schema(name = "creditAccount", example = "3.107431869813706E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String creditAccount;

    @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BigDecimal amount;

}
