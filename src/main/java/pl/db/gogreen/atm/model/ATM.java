package pl.db.gogreen.atm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "ATM", description = "ATMs details")
@Data
@NoArgsConstructor
public class ATM {

    @Min(1) @Max(9999)
    @Schema(name = "region", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("region")
    private Integer region;

    @Min(1) @Max(9999)
    @Schema(name = "atmId", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("atmId")
    private Integer atmId;

    private ATM(Integer region, Integer atmId) {
        this.region = region;
        this.atmId = atmId;
    }
    public static ATM from(Task from) {
        return new ATM(from.getRegion(), from.getAtmId());
    }
}
