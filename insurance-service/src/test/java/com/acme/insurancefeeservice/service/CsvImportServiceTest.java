package com.acme.insurancefeeservice.service;

import com.acme.insurancefeeservice.data.Insurance;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CsvImportServiceTest {

    @InjectMocks
    CsvImportService csvImportService;

    private static final String CATEGORY_FEE_PATH = "import/fee.csv";
    private static final String CATEGORY_FEE_PATH2 = "import/fee2.csv";
    private static final String CATEGORY_FEE_PATH3 = "import/fee3.csv";
    private static final String CATEGORY_FEE_PATH4 = "import/fee4.csv";
    private static final String CATEGORY_FEE_PATH5 = "import/fee5.csv";

    @Test
    public void testFileReadSuccess() throws URISyntaxException, IOException, CsvValidationException {
        List<Insurance> insuranceFees = getInsuranceFees(CATEGORY_FEE_PATH);

        assertThat(insuranceFees.size()).isEqualTo(1);
        assertThat(insuranceFees.get(0).getCategory()).isEqualTo("Toxic");
        assertThat(insuranceFees.get(0).getLimitLow()).isEqualTo(new BigDecimal("0"));
        assertThat(insuranceFees.get(0).getLimitTop()).isEqualTo(new BigDecimal("49999"));
        assertThat(insuranceFees.get(0).getFee()).isEqualTo(new BigDecimal("0.00150"));
    }

    @Test
    public void testReadMultipleLines() throws URISyntaxException, IOException, CsvValidationException {
        List<Insurance> insuranceFees = getInsuranceFees(CATEGORY_FEE_PATH2);

        assertThat(insuranceFees.size()).isEqualTo(2);
    }

    @Test
    public void testReadDataWithoutPercentage() throws URISyntaxException, IOException, CsvValidationException {
        List<Insurance> insuranceFees = getInsuranceFees(CATEGORY_FEE_PATH3);

        assertThat(insuranceFees.size()).isEqualTo(1);
        assertThat(insuranceFees.get(0).getFee()).isEqualTo(new BigDecimal("0.0015"));
    }

    @Test
    public void testReadUnlimitedValueData() throws URISyntaxException, IOException, CsvValidationException {
        List<Insurance> insuranceFees = getInsuranceFees(CATEGORY_FEE_PATH4);

        assertThat(insuranceFees.size()).isEqualTo(1);
        assertThat(insuranceFees.get(0).getLimitTop()).isEqualTo(new BigDecimal("-1"));
    }

    @Test
    public void testReadDataWithoutDecimalDigit() throws URISyntaxException, IOException, CsvValidationException {
        List<Insurance> insuranceFees = getInsuranceFees(CATEGORY_FEE_PATH5);
        assertThat(insuranceFees.size()).isEqualTo(1);
        assertThat(insuranceFees.get(0).getLimitTop()).isEqualTo(new BigDecimal("49999"));
    }

    private List<Insurance> getInsuranceFees(String filePath) throws URISyntaxException, IOException, CsvValidationException {
        Path path = Paths.get(ClassLoader.getSystemResource(filePath).toURI());
        return csvImportService.readAll(new FileReader(path.toFile()),
            Insurance.class);
    }
}
