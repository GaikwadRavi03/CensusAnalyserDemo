package CSVBuilder;

import censusanalyser.CensusAnalyserException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws OpenCSVException;

    public List<E> getCSVFileList(Reader reader, Class csvClass) throws OpenCSVException;
}
