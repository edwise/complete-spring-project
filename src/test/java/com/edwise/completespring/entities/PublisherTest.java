package com.edwise.completespring.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class PublisherTest {

    @Before
    public void init() {
    }

    @Test
    public void testCopyFrom() {
        Publisher publisherFrom = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        Publisher publisher = new Publisher();

        publisher.copyFrom(publisherFrom);

        assertEquals("No son iguales", publisher, publisherFrom);
    }
}
