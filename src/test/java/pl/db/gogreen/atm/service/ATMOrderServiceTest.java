package pl.db.gogreen.atm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Test;
import pl.db.gogreen.atm.model.ATM;
import pl.db.gogreen.atm.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ATMOrderServiceTest {


    @Test
    void shouldPassExample1() throws IOException {
        // given
        ATMOrderService atmService = new ATMOrderService();
        List<Task> request = loadListFromFile("example_1_request.json", Task.class);
        List<ATM> expectedResponse = loadListFromFile("example_1_response.json", ATM.class);

        // when
        Collection<ATM> calculatedResponse = atmService.calculateOrder(request).collect(Collectors.toList());

        // then
        assertThat(calculatedResponse).isEqualTo(expectedResponse);
    }


    @Test
    void shouldPassExample2() throws IOException {
        // given
        ATMOrderService atmService = new ATMOrderService();
        List<Task> request = loadListFromFile("example_2_request.json", Task.class);
        List<ATM> expectedResponse = loadListFromFile("example_2_response.json", ATM.class);

        // when
        Collection<ATM> calculatedResponse = atmService.calculateOrder(request).collect(Collectors.toList());

        // then
        assertThat(calculatedResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldOrderWhenRegionsNotStartFromZero() throws IOException {
        // given
        ATMOrderService atmService = new ATMOrderService();
        List<Task> request = loadListFromFile("request_1_extended.json", Task.class);
        List<ATM> expectedResponse = loadListFromFile("response_1_extended.json", ATM.class);

        // when
        Collection<ATM> calculatedResponse = atmService.calculateOrder(request).collect(Collectors.toList());

        // then
        assertThat(calculatedResponse).isEqualTo(expectedResponse);
    }

    private <T> List<T> loadListFromFile(String fileName, Class<T> toClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, toClass);
        return objectMapper.readValue(new File("src/test/resources/atm/" + fileName), listType);
    }

}