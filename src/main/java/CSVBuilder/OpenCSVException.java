package CSVBuilder;


public class OpenCSVException extends Exception {


    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE
    }

    ExceptionType type;

    public OpenCSVException(String message, OpenCSVException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public OpenCSVException(String message, OpenCSVException.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
