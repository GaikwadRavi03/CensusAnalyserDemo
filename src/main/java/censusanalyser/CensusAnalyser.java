package censusanalyser;

import com.google.gson.Gson;

import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser<E> {

    public enum Country {INDIA, US}

    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
        this.censusStateMap = new HashMap<>();
        this.sortingMap = new HashMap<>();
        this.sortingMap.put(SortingField.state, Comparator.comparing(census -> census.state));
        this.sortingMap.put(SortingField.population, Comparator.comparing(census -> census.population));
        this.sortingMap.put(SortingField.areaInSqKm, Comparator.comparing(census -> census.totalArea));
        this.sortingMap.put(SortingField.densityPerSqKm, Comparator.comparing(census -> census.populationDensity));
        Comparator<CensusDAO> comparing = Comparator.comparing(census ->
                census.population);
        this.sortingMap.put(SortingField.population, comparing.thenComparing(census -> census.populationDensity));
    }

    Map<String, CensusDAO> censusStateMap = null;
    Map<SortingField, Comparator<CensusDAO>> sortingMap = null;

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = CensusAnalyserFactory.loadCensusData(country);
        censusStateMap = censusAdapter.loadCensusData(csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData(SortingField fieldName) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTOS = censusStateMap.values().stream()
                .sorted(this.sortingMap.get(fieldName))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
}
