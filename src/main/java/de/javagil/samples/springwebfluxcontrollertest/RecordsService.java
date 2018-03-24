package de.javagil.samples.springwebfluxcontrollertest;

import reactor.core.publisher.Flux;

/**
 * Just some service to show service mocking in the JUnit tests.
 */
public interface RecordsService {

    Flux<Record> getRecords();

}
