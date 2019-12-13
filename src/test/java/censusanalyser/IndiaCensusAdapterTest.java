package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode12.csv";

    @Test
    public void givenMessage_loadIndianCensusData_ShouldReturn_ExactCount() {
        IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenMessage_loadIndianCensusData_WhenSendOneFilePath_ShouldReturn_Exception() {
        IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
        try {
            indianCensusAdapter.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.ONE_FILE_PATH, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
            indianCensusAdapter.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFileType_ShouldReturnsException() {
        try {
            IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
            indianCensusAdapter.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
            indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException() {
        try {
            IndianCensusAdapter indianCensusAdapter = new IndianCensusAdapter();
            indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NOT_SUFFICIENT_FILES, e.type);
        }
    }
}
