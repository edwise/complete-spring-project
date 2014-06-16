package com.edwise.completespring.entities;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class PublisherTest {

    // TODO refactorizar tests...

    @Test
    public void testCopyFrom() {
        Publisher publisherFrom = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        Publisher publisher = new Publisher();

        publisher.copyFrom(publisherFrom);

        assertEquals("No son iguales", publisher, publisherFrom);
    }

    @Test
    public void testEquals(){
        Publisher publisher1 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        Publisher publisher2 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        assertTrue("Deben ser iguales", publisher1.equals(publisher2) && publisher2.equals(publisher1));

        // different fields
        publisher1 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        publisher2 = new Publisher().setName("Nombre").setCountry("US").setOnline(false);
        assertFalse("No deben ser iguales", publisher1.equals(publisher2) || publisher2.equals(publisher1));

        // different object
        assertFalse("No deben ser iguales", new Publisher().setName("Nombre").equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Publisher publisher1 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        Publisher publisher2 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        assertEquals("Deben ser iguales", publisher1.hashCode(), publisher2.hashCode());

        // different fields
        publisher1 = new Publisher().setName("Nombre").setCountry("ES").setOnline(true);
        publisher2 = new Publisher().setName("Nombre2").setCountry("US").setOnline(false);
        assertNotEquals("No deben ser iguales", publisher1.hashCode(), publisher2.hashCode());
    }

    @Test
    public void testToString(){
        Publisher publisher = new Publisher();
        assertTrue(publisher.toString().contains("name=null"));
        assertTrue(publisher.toString().contains("country=null"));
        assertTrue(publisher.toString().contains("isOnline=false"));
    }
}
