package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap;
        censusStateMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusStateMap;
    }
}
