package utils;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ReaderTSV {

	private String columnId, columnContent, columnCreatedAt, columnCreateAtStr;

	public ReaderTSV(String line) throws IOException {
		CSVParser csvParser = new CSVParser(new StringReader(line), CSVFormat.DEFAULT.withDelimiter('\t'));
		CSVRecord currentRecord = csvParser.iterator().next();
		columnId = currentRecord.get(0);
		columnContent = currentRecord.get(1);
		columnCreatedAt = currentRecord.get(7);
		columnCreateAtStr = currentRecord.get(8);
	}

	public String getColumnId() {
		return columnId;
	}

	public String getColumnContent() {
		return columnContent;
	}

	public String getColumnCreatedAt() {
		return columnCreatedAt;
	}
	
	public String getColumnCreatedAtStr() {
		return columnCreateAtStr;
	}

}
