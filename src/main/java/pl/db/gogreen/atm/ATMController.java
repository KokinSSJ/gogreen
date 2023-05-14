package pl.db.gogreen.atm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.db.gogreen.atm.model.ATM;
import pl.db.gogreen.atm.model.Task;
import pl.db.gogreen.atm.service.ATMOrderService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class ATMController {

    private final ATMOrderService atmOrderService;


    /**
     * GET /atms/calculateOrder
     * Calculates ATMs order for service team
     *
     * @param task  (required)
     * @return Successful operation (status code 200)
     */
    @Operation(
            operationId = "calculate",
            description = "Calculates ATMs order for service team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ATM.class)))
                    })
            }
    )
    @PostMapping(
            path = "/atms/calculateOrder",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public Stream<ATM> calculate(
            @Parameter(name = "Task", required = true) @Valid @RequestBody List<Task> task) {
        return  atmOrderService.calculateOrder(task);
    }

}
