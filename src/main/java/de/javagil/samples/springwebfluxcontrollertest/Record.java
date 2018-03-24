package de.javagil.samples.springwebfluxcontrollertest;

/**
 * Some example data record.
 */
public class Record {
    public final Long id;
    public final String title;

    public Record(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
