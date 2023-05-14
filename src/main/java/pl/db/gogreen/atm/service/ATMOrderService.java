package pl.db.gogreen.atm.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import pl.db.gogreen.atm.model.ATM;
import pl.db.gogreen.atm.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ATMOrderService {

    public Stream<ATM> calculateOrder(List<Task> tasks) {
        Map<Integer, List<Task>> groupedByRegion = tasks.stream()
                .unordered()
                .collect(Collectors.groupingByConcurrent(Task::getRegion));

        Map<Integer, List<ATM>> sortedValuesByPriority = groupedByRegion.entrySet().parallelStream().map(entry -> {
            List<ATM> list = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(x -> x.getRequestType().getPriority()))
                    .map(ATM::from)
                    .distinct()
                    .toList();
            return Pair.of(entry.getKey(), list);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return sortedValuesByPriority.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .flatMap(List::stream);
    }
}
