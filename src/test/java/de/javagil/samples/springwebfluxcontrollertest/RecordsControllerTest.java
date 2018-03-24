package de.javagil.samples.springwebfluxcontrollertest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Demonstrates testing a Spring WebFlux directly.
 */

// integrates Spring Framework and Spring Boot test annotations
@RunWith(SpringRunner.class)

// only enable these controllers (default is all)
@WebFluxTest(controllers = {RecordsController.class})

public class RecordsControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    RecordsService recordsService;

    @Mock
    Model model;

    @Autowired
    RecordsController controller;

    private RecordsServiceFaker recordsServiceFaker;

    @Before
    public void init() {
        recordsServiceFaker = new RecordsServiceFaker(recordsService);
    }

    @Test
    public void getRecordsPage() throws Exception {

        // given some fake records
        recordsServiceFaker.givenRecords( new Record(1, "first-test-record"), new Record(2, "second-test-record"), new Record(3, "third-test-record"));

        // when
        String viewName = controller.getRecordsPage(model);

        // then view name for template equals "records"
        assertEquals("records", viewName);

        // and recordService.getRecords() got called once
        verify(recordsService, times(1)).getRecords();

        // and "records" attribute is added to Model with 3 records
        ArgumentCaptor<Flux<Record>> argumentCaptor = ArgumentCaptor.forClass(Flux.class);
        verify(model, times(1)).addAttribute(eq("records"), argumentCaptor.capture());
        assertEquals(3, argumentCaptor.getValue().collectList().block().size());
    }
}