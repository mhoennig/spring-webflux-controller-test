package de.javagil.samples.springwebfluxcontrollertest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrates testing a Spring WebFlux @Controller integrated with Thymeleaf.
 */

// integrates Spring Framework and Spring Boot test annotations
@RunWith(SpringRunner.class)

// only enable these controllers (default is all)
@WebFluxTest(controllers = {RecordsController.class})

// additional beans we want configured other than Spring WebFlux controllers
@Import({ThymeleafAutoConfiguration.class})

public class RecordsControllerThymeleafTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    RecordsService recordsService;

    private RecordsServiceFaker recordsServiceFaker;

    @Before
    public void init() {
        recordsServiceFaker = new RecordsServiceFaker(recordsService);
    }

    @Test
    public void getRecordsPage() throws Exception {

        // given some fake records
        recordsServiceFaker.givenRecords( new Record(1, "first-test-record"), new Record(2, "second-test-record"), new Record(3, "third-test-record"));

        EntityExchangeResult<String> result = this.webTestClient.get().uri("/records")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();
        assertThat(result.getResponseBody())
                // this meta tag is hardcoded in the records.html template
                // not really necessary, as otherwise the actual content would hardly get rendered
                .contains("<meta name=\"thymeleaf-template\" content=\"records\">")

                // tests if the actual content gets rendered by Thymeleaf
                .contains("<td>first-test-record</td>")
                .contains("<td>second-test-record</td>")
                .contains("<td>third-test-record</td>");
    }
}