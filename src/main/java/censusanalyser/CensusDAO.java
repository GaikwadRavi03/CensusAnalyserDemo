package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        totalArea = indiaCensusCSV.areaInSqKm;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode = censusCSV.stateId;
        population = censusCSV.population;
        populationDensity = censusCSV.populationDensity;
        totalArea = censusCSV.totalArea;
    }
}
