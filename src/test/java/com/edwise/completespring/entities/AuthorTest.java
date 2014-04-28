package com.edwise.completespring.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class AuthorTest {

    @Before
    public void init() {
    }

    @Test
    public void testCopyFrom() {
        Author authorFrom = new Author().setName("Nombre").setSurname("App");
        Author author = new Author();

        author.copyFrom(authorFrom);

        assertEquals("No son iguales", author, authorFrom);
    }
}
