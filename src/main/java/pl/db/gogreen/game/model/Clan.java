package pl.db.gogreen.game.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Clan {

    @Min(1) @Max(1000)
    @Schema(name = "numberOfPlayers", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer numberOfPlayers;


    @Min(1) @Max(1000000)
    @Schema(name = "points", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer points;

}
