package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,NO_CENSUS_DATA,INCORRECT_DELIMITER, INCORRECT_COUNTRY,ONE_FILE_PATH,NOT_SUFFICIENT_FILES
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
