package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser<E> {

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusStateMap = null;
    Map<SortingField, Comparator<CensusDAO>> sortingMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
        this.sortingMap = new HashMap<>();
        this.sortingMap.put(SortingField.state, Comparator.comparing(census -> census.state));
        this.sortingMap.put(SortingField.population, Comparator.comparing(census -> census.population));
        this.sortingMap.put(SortingField.areaInSqKm, Comparator.comparing(census -> census.totalArea));
        this.sortingMap.put(SortingField.densityPerSqKm, Comparator.comparing(census -> census.populationDensity));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = new CensusLoader().loadCensusData(country, csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData(SortingField fieldName) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, this.sortingMap.get(fieldName));
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
}
