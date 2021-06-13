package ru.job4j.grabber;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class GrabberTest {

    @Test
    public void sum() {
        Grabber grabber = new Grabber();
        assertThat(grabber.sum(), is(5));
    }
}