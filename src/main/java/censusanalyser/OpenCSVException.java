package censusanalyser;

public class OpenCSVException extends Exception {

    public OpenCSVException(String message, ExceptionType unableToParse) {

    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE
    }

    CensusAnalyserException.ExceptionType type;

    public OpenCSVException(String message, CensusAnalyserException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public OpenCSVException(String message, CensusAnalyserException.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
