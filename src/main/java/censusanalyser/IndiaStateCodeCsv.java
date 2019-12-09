package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCsv {

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;
}
