package pl.db.gogreen.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pl.db.gogreen.game.model.Players;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
class OnlineGameControllerTest {

    public static final String TEST_RESOURCES_GAME = "src/test/resources/game/";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldPassFirstExample() throws IOException {
        // given
        Players request = loadFromFile("example_request.json");
        String expectedResponse = getExpectedResponse("example_response.json");

        // when
        webTestClient.post()
                .uri("/onlinegame/calculate")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
        // then
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    private Players loadFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(TEST_RESOURCES_GAME + fileName), Players.class);
    }

    private static String getExpectedResponse(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEST_RESOURCES_GAME + fileName)));
    }

}