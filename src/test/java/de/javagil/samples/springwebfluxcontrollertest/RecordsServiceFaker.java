package de.javagil.samples.springwebfluxcontrollertest;

import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

/**
 * Common test data mocking helper for use in both JUnit tests.
 */
public class RecordsServiceFaker {

    private final RecordsService recordsService;

    public RecordsServiceFaker(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    public void givenRecords(Record... records) {
        Set<Record> recordsSet = Stream.of(records).collect(Collectors.toSet());
        when(recordsService.getRecords()).thenReturn(Flux.fromIterable(recordsSet));
    }
}
