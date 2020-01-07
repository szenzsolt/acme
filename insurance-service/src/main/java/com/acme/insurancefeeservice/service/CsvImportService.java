package com.acme.insurancefeeservice.service;

import com.acme.insurancefeeservice.data.IData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CsvImportService<T extends IData> {

    public List<T> readAll(Reader reader, Class<T> classObject) throws IOException, CsvValidationException {
        CSVParser parser = new CSVParserBuilder()
            .withSeparator(';')
            .withIgnoreQuotations(true)
            .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
            .withSkipLines(0)
            .withCSVParser(parser)
            .build();

        List<T> list = readAllLines(csvReader, classObject);

        reader.close();
        csvReader.close();

        return list;
    }

    private String[] captureHeader(CSVReader reader) throws IOException, CsvValidationException {
        return reader.readNext();
    }

    private List<T> readAllLines(CSVReader csvReader, Class<T> classObject) throws IOException, CsvValidationException {
        List<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String[] header = captureHeader(csvReader);
        String[] line;

        while (Objects.nonNull(header) && Objects.nonNull(line = csvReader.readNext())) {

            T feeData = mapper.convertValue(readLine(header, line), classObject);
            list.add(feeData);
        }
        return list;
    }

    private Map<String, String> readLine(String[] header, String[] line){
        Map<String, String> fields = new HashMap<>();
        for(int col = 0; col < header.length; ++col) {
            fields.put(header[col], line[col]);
        }
        return fields;
    }

}
