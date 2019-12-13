package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        loadCensusData(USCensusCSV.class, csvFilePath[1]);
        return censusStateMap;
    }
}
