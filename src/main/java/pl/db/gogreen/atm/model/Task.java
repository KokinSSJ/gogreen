package pl.db.gogreen.atm.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task {

    @Min(1) @Max(9999)
    @Schema(name = "region", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer region;

    @Schema(name = "requestType", example = "STANDARD", description = "Type of request", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private RequestTypeEnum requestType;

    @Min(1) @Max(9999)
    @Schema(name = "atmId", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer atmId;

}
