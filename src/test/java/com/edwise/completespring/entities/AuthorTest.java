package com.edwise.completespring.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class AuthorTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testCopyFrom() {
        Author authorFrom = new Author().setName("Nombre").setSurname("App");
        Author author = new Author();

        author.copyFrom(authorFrom);

        assertEquals("No son iguales", author, authorFrom);
    }

    @Test
    public void testEquals() {
        // same fields
        Author author1 = new Author().setName("Nombre").setSurname("App");
        Author author2 = new Author().setName("Nombre").setSurname("App");
        assertTrue("Deben ser iguales", author1.equals(author2) && author2.equals(author1));

        // same name, different surname
        author1 = new Author().setName("Nombre").setSurname("App");
        author2 = new Author().setName("Nombre").setSurname("App22");
        assertFalse("No deben ser iguales", author1.equals(author2) || author2.equals(author1));

        // different object
        assertFalse("No deben ser iguales", new Author().setName("Nombre").equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // same fields
        Author author1 = new Author().setName("Nombre").setSurname("App");
        Author author2 = new Author().setName("Nombre").setSurname("App");
        assertEquals("Deben ser iguales", author1.hashCode(), author2.hashCode());

        // different fields
        author1 = new Author().setName("Nombre").setSurname("App");
        author2 = new Author().setName("Nombre2").setSurname("App2");
        assertNotEquals("No deben ser iguales", author1.hashCode(), author2.hashCode());
    }

    @Test
    public void testToString() {
        Author author = new Author();
        assertTrue(author.toString().contains("name=null"));
        assertTrue(author.toString().contains("surname=null"));
    }
}
