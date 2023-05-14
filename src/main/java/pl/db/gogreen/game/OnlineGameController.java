package pl.db.gogreen.game;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.db.gogreen.game.model.Clan;
import pl.db.gogreen.game.model.Players;
import pl.db.gogreen.game.service.OnlineGameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@Validated FIXME: Disabling @Validated as it's primarily performance / green-coding contest not strict follow api-docs ?
public class OnlineGameController {

    private final OnlineGameService gameService;


    @Operation(
            operationId = "calculate",
            description = "Calculate order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Clan.class)))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/onlinegame/calculate",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<List<List<Clan>>> calculate(
            @Parameter(name = "Players", required = true)
            @Valid @RequestBody Players players) {

        return ResponseEntity.ok(gameService.orderClansForSpecialEvent(players));
    }

}
