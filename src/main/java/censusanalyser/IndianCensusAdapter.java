package censusanalyser;

import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = null;
        censusStateMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length != 2) {
            throw new CensusAnalyserException("Not Sufficient file", CensusAnalyserException.ExceptionType.NOT_SUFFICIENT_FILES);
        }
        this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndiaStateCode(Map<String, CensusDAO> censusStateMap, String csvFilePath) throws CensusAnalyserException {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER);
        }
    }
}