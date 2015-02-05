package com.edwise.completespring.entities;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PublisherTest {
    private static final String NAME_TEST1 = "Alfaguara";
    private static final String NAME_TEST2 = "Gigamesh";
    private static final String COUNTRY_TEST1 = "ES";
    private static final String COUNTRY_TEST2 = "US";

    @Test
    public void testCopyFrom() {
        Publisher publisherFrom = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher = createPublisher(null, null, false);

        publisher.copyFrom(publisherFrom);

        assertEquals(publisher, publisherFrom);
    }

    @Test
    public void testEquals() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);

        assertTrue(publisher1.equals(publisher2) && publisher2.equals(publisher1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST2, false);

        assertFalse(publisher1.equals(publisher2) || publisher2.equals(publisher1));
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Publisher publisher = createPublisher(NAME_TEST1, COUNTRY_TEST1, false);

        assertFalse(publisher.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);

        assertEquals(publisher1.hashCode(), publisher2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST2, COUNTRY_TEST2, false);

        assertNotEquals(publisher1.hashCode(), publisher2.hashCode());
    }

    @Test
    public void testToString() {
        Publisher publisher = createPublisher(null, null, false);

        String publisherString = publisher.toString();

        assertThatPublisherStringContainsAllFields(publisherString);
    }

    private void assertThatPublisherStringContainsAllFields(String publisherString) {
        assertThat(publisherString, containsString("name=null"));
        assertThat(publisherString, containsString("country=null"));
        assertThat(publisherString, containsString("isOnline=false"));
    }

    public static Publisher createPublisher(String name, String country, boolean isOnline) {
        return new Publisher()
                .setName(name)
                .setCountry(country)
                .setOnline(isOnline);
    }
}
