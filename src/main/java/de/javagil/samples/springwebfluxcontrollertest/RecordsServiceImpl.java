package de.javagil.samples.springwebfluxcontrollertest;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementation of a service, just to show service mocking in the JUnit tests.
 * In the tests, this service is mocked, just used of you run the application.
 */
@Service
public class RecordsServiceImpl implements RecordsService {
    @Override
    public Flux<Record> getRecords() {
        // just some dummy data to display (not used in the tests of this demo)
        return Flux.just( new Record(1, "the-first-record"), new Record(2, "the-second-record"), new Record(3, "the-third-record"));
    }
}
