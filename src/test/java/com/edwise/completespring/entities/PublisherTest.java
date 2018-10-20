package com.edwise.completespring.entities;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(publisher).isEqualTo(publisherFrom);
    }

    @Test
    public void testEquals() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);

        assertThat(publisher1.equals(publisher2) && publisher2.equals(publisher1)).isTrue();
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST2, false);

        assertThat(publisher1.equals(publisher2) || publisher2.equals(publisher1)).isFalse();
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Publisher publisher = createPublisher(NAME_TEST1, COUNTRY_TEST1, false);

        assertThat(publisher).isNotEqualTo(new Object());
    }

    @Test
    public void testHashCode() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);

        assertThat(publisher1.hashCode()).isEqualTo(publisher2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Publisher publisher1 = createPublisher(NAME_TEST1, COUNTRY_TEST1, true);
        Publisher publisher2 = createPublisher(NAME_TEST2, COUNTRY_TEST2, false);

        assertThat(publisher1.hashCode()).isNotEqualTo(publisher2.hashCode());
    }

    @Test
    public void testToString() {
        Publisher publisher = createPublisher(null, null, false);

        assertThatPublisherStringContainsAllFields(publisher.toString());
    }

    private void assertThatPublisherStringContainsAllFields(String publisherString) {
        assertThat(publisherString).contains("name=null");
        assertThat(publisherString).contains("country=null");
        assertThat(publisherString).contains("isOnline=false");
    }

    public static Publisher createPublisher(String name, String country, boolean isOnline) {
        return new Publisher()
                .setName(name)
                .setCountry(country)
                .setOnline(isOnline);
    }
}
