package com.chandrakumar.ms.api.customer;

import com.chandrakumar.ms.api.CustomerAPIApplication;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import lombok.extern.slf4j.Slf4j;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = CustomerAPIApplication.class,
        webEnvironment = SpringBootTest.
                WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class CustomerAPIIT {

    private static final String JSON = "json";
    private static final String PATH_NAME = "target";
    private static final String PROJECT_NAME = "ms-ecom-gps-customers API";
    private static final String TAG_IGNORE = "~@ignore";

    @LocalServerPort
    int portNumber;

    @PostConstruct
    public void test() {
        System.setProperty("test.server.port", String.valueOf(portNumber));
        log.info("mock.server.port {}", System.getProperty("test.server.port"));
    }

    @BeforeAll
    public static void testBeforeAll() {
        log.info("testBeforeAll");
    }

    @AfterAll
    public static void testAfterAll() {
        log.info("testAfterAll");
    }

    @Test
    void testCustomer() {

        Results results = Runner.path("classpath:feature/customer")
                .tags(TAG_IGNORE)
                .parallel(1);
        generateReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    public static void generateReport(String karateOutputPath) {

        Collection<File> jsonFiles = FileUtils.listFiles(
                new File(karateOutputPath), new String[]{JSON}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File(PATH_NAME), PROJECT_NAME);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}