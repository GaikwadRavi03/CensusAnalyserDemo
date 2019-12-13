package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAdapterTest {
    private static final String US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData123.csv";
    private static final String INCORRECT_DELIMITER_US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/IncorrectDelimeterUSCensusData.csv";

    @Test
    public void givenMessage_loadUS_CensusData_ShouldReturn_ExactCount() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFilePathIncorrect_ShouldReturn_Exception() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFilePathNull_ShouldReturn_Exception() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData("");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFileHasIncorrectDelimiter_ShouldReturn_Exception() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(INCORRECT_DELIMITER_US_CENSUS_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}
