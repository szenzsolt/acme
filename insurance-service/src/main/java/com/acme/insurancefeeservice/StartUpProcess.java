package com.acme.insurancefeeservice;

import com.acme.insurancefeeservice.data.Insurance;
import com.acme.insurancefeeservice.service.CsvImportService;
import com.acme.insurancefeeservice.service.FeeService;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class StartUpProcess {

    @Autowired
    private FeeService feeService;

    @Autowired
    private CsvImportService csvImportService;

    private static final String CATEGORY_FEE_PATH = "import/fee.csv";

    private final Logger LOG = LoggerFactory.getLogger(StartUpProcess.class);

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(CATEGORY_FEE_PATH).toURI());
            List<Insurance> insuranceFees = csvImportService.readAll(
                new FileReader(path.toFile()),
                Insurance.class);
            feeService.saveAllFee(insuranceFees);
        } catch (URISyntaxException e) {
            LOG.error("URI syntax error: {}", e.getMessage());
        } catch (FileNotFoundException e) {
            LOG.error("File not found: {}", e.getMessage());
        } catch (IOException e) {
            LOG.error("IO exception: {}", e.getMessage());
        } catch (CsvValidationException e) {
            LOG.error("The CSV is not valid: {}", e.getMessage());
        }

       /* try {
            Path path = Paths.get(
                ClassLoader.getSystemResource(CATEGORY_FEE_PATH).toURI());
            feeService.saveAllFee(readCsv(path, InsuranceFeeData.class));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }





    /* public List<InsuranceFeeData> readCsv(Path path, Class<InsuranceFeeData> clazz) throws Exception {
        *//*HeaderColumnNameMappingStrategy<InsuranceFeeData> mappingInfo= new HeaderColumnNameMappingStrategy<>();

        mappingInfo.setType(InsuranceFeeData.class);

       // Reader reader = Files.newBufferedReader(path);
        CSVReader csvReader = new CSVReader( new FileReader(path.toFile()));//new FileReader(path),',','"',1);
        CsvToBean csv = new CsvToBean();

        CsvToBean cb = new CsvToBeanBuilder(csvReader)
            .withType(clazz)
            .withSeparator(';')
            .withMappingStrategy(mappingInfo)
            .build();

        csvReader.close();
        return cb.parse();*//*

        HeaderColumnNameMappingStrategy<InsuranceFeeData> mappingInfo= new HeaderColumnNameMappingStrategy<>();
        mappingInfo.setType(InsuranceFeeData.class);

        //Reader csvReader = new FileReader(path.toFile());
        CSVReader csvReader = new CSVReader( new FileReader(path.toFile()),';');

        final CsvToBean<InsuranceFeeData> csvToBean =
            new CsvToBean<InsuranceFeeData>(){
            @Override
            protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException,IllegalAccessException {

                if (prop.getName().equals("Fee")) {
                    // return an custom object based on the incoming value
                    double rate;
                    if (value.contains("%")) {
                        rate = Double.parseDouble(value.replaceAll("%","")) / 100;
                    } else {
                        rate = Double.parseDouble(value);
                    }
                    return rate;
                }

                return super.convertValue(value, prop);
            }
        };
        *//*new CsvToBeanBuilder(csvReader)
            .withType(clazz)
            .withMappingStrategy(mappingInfo)
            .withSeparator(';')
            .build();*//*
        final List<InsuranceFeeData> list = csvToBean.parse(mappingInfo,csvReader);


        return list;//cb.parse();
    }*/

   /* private static final CsvMapper mapper = new CsvMapper();
    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true).;
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
*/
}
