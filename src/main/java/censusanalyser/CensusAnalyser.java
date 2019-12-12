package censusanalyser;

import com.google.gson.Gson;
import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser<E> {

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

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        censusStateMap = new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        return censusStateMap.size();
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        censusStateMap = new CensusLoader().loadCensusData(csvFilePath, USCensusCSV.class);
        return censusStateMap.size();
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCsv> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCsv.class);
            Iterable<IndiaStateCodeCsv> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.stateName) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.stateName).stateCode = csvState.stateCode);
            return censusStateMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
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
