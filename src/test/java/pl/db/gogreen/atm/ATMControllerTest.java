package pl.db.gogreen.atm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pl.db.gogreen.atm.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest
@AutoConfigureWebTestClient
class ATMControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldPassFirstExample() throws IOException {
        // given
        List<Task> request = loadListFromFile("example_1_request.json");
        String expectedResponse = getExpectedResponse("example_1_response.json");

        // when
        webTestClient.post()
                .uri("/atms/calculateOrder")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                // then
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldPassSecondExample() throws IOException {
        // given
        List<Task> request = loadListFromFile("example_2_request.json");
        String expectedResponse = getExpectedResponse("example_2_response.json");

        // when
        webTestClient.post()
                .uri("/atms/calculateOrder")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                // then
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    private List<Task> loadListFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Task.class);
        return objectMapper.readValue(new File("src/test/resources/atm/" + fileName), listType);
    }

    private static String getExpectedResponse(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/atm/" + fileName)));
    }


}