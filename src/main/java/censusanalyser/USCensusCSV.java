package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "State Id")
    public String stateId;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "Total area")
    public double totalArea;

    @CsvBindByName(column = "Population Density")
    public double populationDensity;

    public USCensusCSV() {
    }

    public USCensusCSV(String state, String stateId, int population, double populationDensity, double totalArea) {
        this.state = state;
        this.stateId = stateId;
        this.population = population;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
    }
}
