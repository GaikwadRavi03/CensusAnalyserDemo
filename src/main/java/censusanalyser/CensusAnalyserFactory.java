package censusanalyser;

import java.util.Map;

public class CensusAnalyserFactory {
    public static CensusAdapter loadCensusData(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA)) {
            return new IndianCensusAdapter();
        }
        if (country.equals(CensusAnalyser.Country.US)) {
            return new USCensusAdapter();
        }
        return null;
    }
}
