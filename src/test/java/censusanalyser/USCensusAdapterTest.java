package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAdapterTest {
    private static final String US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenMessage_loadUS_CensusData_ShouldReturn_ExactCount() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        } catch (CensusAnalyserException e) {
        }
    }
}
