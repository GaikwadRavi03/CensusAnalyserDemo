package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INCORRECT_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode2.csv";
    private static final String US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCsv_ShouldReturn_ExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfStateCode = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INCORRECT_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFileType_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.ONE_FILE_PATH, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.ONE_FILE_PATH, e.type);
        }
    }

    @Test
    public void givenStateCSV_FileForSortStateAlphabetical_ShouldReturnLowestAlphabetState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_WhenNull_FileForSortStateAlphabetical_ShouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaStateCsv_MergeBothCsvFiles_ShouldReturn_ExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            int numOfStateCode = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortPopulation_ShouldReturnLowestPopulatedState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.population);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortPopulation_ShouldReturnHighestPopulatedState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.population);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortArea_ShouldReturnLowestSize_AreaState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.areaInSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Goa", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortArea_ShouldReturnHighestSize_AreaState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.areaInSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortDensity_ShouldReturnLowestSize_DensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.densityPerSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCSV_FileSortDensity_ShouldReturnHighestSize_DensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.densityPerSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int data = censusAnalyser.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        } catch (CensusAnalyserException e) {
        }
    }

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
    public void givenUSCensusData_whenSortedOnState_ShouldReturnLowestState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.state);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_whenSortedOnState_ShouldReturnHighestState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortingField.state);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Wyoming", censusCSV[50].state);
        } catch (CensusAnalyserException e) {
        }
    }
}
