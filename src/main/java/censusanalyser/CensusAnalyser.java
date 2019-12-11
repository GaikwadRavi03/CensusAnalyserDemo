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

    Map<String, IndiaCensusDAO> censusStateMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return censusStateMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        int counter=0;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCsv> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCsv.class);
            while (stateCSVIterator.hasNext()) {
                counter++;
                IndiaStateCodeCsv stateCSV = stateCSVIterator.next();
                IndiaCensusDAO censusDAO = censusStateMap.get(stateCSV.stateCode);
                if (censusDAO == null) continue;
                censusDAO.stateCode = stateCSV.stateCode;
            }
            return counter;
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterator = () -> censusCSVIterator;
        int namOfEateries = (int) StreamSupport.stream(csvIterator.spliterator(), false).count();
        return namOfEateries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusDAOS.get(j);
                IndiaCensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
}
