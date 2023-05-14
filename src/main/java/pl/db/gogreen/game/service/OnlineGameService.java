package pl.db.gogreen.game.service;

import org.springframework.stereotype.Service;
import pl.db.gogreen.game.model.Clan;
import pl.db.gogreen.game.model.Players;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class OnlineGameService {


    public List<List<Clan>> orderClansForSpecialEvent(Players players) {
        List<List<Clan>> result = new ArrayList<>();
        int groupCount = players.getGroupCount();
        
        List<Clan> orderByPointsAndPlayers = players.getClans().stream().unordered()
                .sorted(Comparator.comparingInt(Clan::getPoints).reversed().thenComparingInt(Clan::getNumberOfPlayers))
                .toList();

        NavigableMap<Integer, List<Integer>> leftSpacesWithIndexesToResult = new ConcurrentSkipListMap<>(Comparator.reverseOrder());

        initFirstEventGroup(result, leftSpacesWithIndexesToResult, groupCount);


        for (Clan clan : orderByPointsAndPlayers) {
            int clansNumberOfPlayers = clan.getNumberOfPlayers();
            
            SortedMap<Integer, List<Integer>> groupsInWhichFitsClan = leftSpacesWithIndexesToResult.headMap(clansNumberOfPlayers, true);
            
            Optional<Integer> firstIndexInWhichClanFitsOptional = groupsInWhichFitsClan.values()
                    .stream()
                    .flatMap(List::stream)
                    .min(Comparator.comparingInt(t -> t));
            
            if (firstIndexInWhichClanFitsOptional.isPresent()) {
                Integer firstIndexInWhichClanFits = firstIndexInWhichClanFitsOptional.get();
                List<Clan> eventGroupWithSpaceForClan = result.get(firstIndexInWhichClanFits);
                
                int leftSizeBeforeAdding = groupCount - eventGroupWithSpaceForClan.stream()
                        .mapToInt(Clan::getNumberOfPlayers)
                        .sum();
                eventGroupWithSpaceForClan.add(clan);
                int leftSizeAfterAdding = groupCount - eventGroupWithSpaceForClan.stream()
                        .mapToInt(Clan::getNumberOfPlayers)
                        .sum();

                cleanOldSpaceInfo(result, groupCount, leftSpacesWithIndexesToResult, groupsInWhichFitsClan,
                        firstIndexInWhichClanFits, leftSizeBeforeAdding);
                addNewSpaceInfo(leftSpacesWithIndexesToResult, leftSizeAfterAdding, firstIndexInWhichClanFits);
            } else {
                throw new IllegalStateException("Min state was expected: " + clan);
            }
        }

        cleanLastEmptyArray(result);

        return result;
    }

    private static void initFirstEventGroup(List<List<Clan>> result,
                                            NavigableMap<Integer, List<Integer>> leftSpacesInEventGroups,
                                            int groupCount) {
        List<Integer> groupsIndexes = new ArrayList<>();
        leftSpacesInEventGroups.put(groupCount, groupsIndexes);
        List<Clan> firstEmptyGroup = new ArrayList<>();
        result.add(firstEmptyGroup);
        groupsIndexes.add(result.size() - 1);
    }

    private static void cleanOldSpaceInfo(List<List<Clan>> result, int groupCount,
                                          NavigableMap<Integer, List<Integer>> leftSpacesWithResultIndexes,
                                          SortedMap<Integer, List<Integer>> clanGroupFits,
                                          Integer firstIndexInWhichClanFits, int leftSizeBeforeAdding) {
        List<Integer> beforeIndexes = clanGroupFits.get(leftSizeBeforeAdding);
        beforeIndexes.remove(firstIndexInWhichClanFits);

        if (leftSizeBeforeAdding == groupCount) {
            ArrayList<Clan> newWaiting = new ArrayList<>();
            result.add(newWaiting);

            int lastIndex = result.size() - 1;
            addNewSpaceInfo(leftSpacesWithResultIndexes, groupCount, lastIndex);
        } else if (beforeIndexes.isEmpty()) {
            leftSpacesWithResultIndexes.remove(leftSizeBeforeAdding);
        }
    }
    private static void addNewSpaceInfo(NavigableMap<Integer, List<Integer>> leftSpacesWithIndexesToResult,
                                        int leftSizeAfterAdding, Integer firstIndexInWhichClanFits) {
        leftSpacesWithIndexesToResult.compute(leftSizeAfterAdding, (missing, listOfNotFullGroups) -> {
            List<Integer> list = listOfNotFullGroups;
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(firstIndexInWhichClanFits);
            return list;
        });
    }

    private static void cleanLastEmptyArray(List<List<Clan>> result) {
        int lastElementIndex = result.size() - 1;
        List<Clan> lastElement = result.get(lastElementIndex);
        if (lastElement != null && lastElement.isEmpty()) {
            result.remove(lastElementIndex);
        }
    }

}
