package pl.db.gogreen.game.model;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Players {

    private Integer groupCount;

    @Valid
    private List<@Valid Clan> clans;


}
