package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorItemTest {
    private static final String FIELD_TEST1 = "authorName";
    private static final String MESSAGE_TEXT1 = "El campo no puede ser nulo";
    private static final String MESSAGE_TEXT2 = "El campo no puede venir vacio";

    @Test
    public void testEquals() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);

        assertThat(errorItem1.equals(errorItem2) && errorItem2.equals(errorItem1)).isTrue();
    }

    @Test
    public void testNotEqualsWithDifferentFields() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT2);

        assertThat(errorItem1.equals(errorItem2) || errorItem2.equals(errorItem1)).isFalse();
    }

    @Test
    public void testNotEqualsWithDifferentObjects() {
        ErrorItem errorItem = createErrorItem(FIELD_TEST1, null);

        assertThat(errorItem).isNotEqualTo(new Object());
    }

    @Test
    public void testHashCode() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);

        assertThat(errorItem1.hashCode()).isEqualTo(errorItem2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT2);

        assertThat(errorItem1.hashCode()).isNotEqualTo(errorItem2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorItem errorItem = createErrorItem(null, null);

        assertThatErrorItemStringContainsAllFields(errorItem.toString());
    }

    private void assertThatErrorItemStringContainsAllFields(String errorItemString) {
        assertThat(errorItemString).contains("field=null");
        assertThat(errorItemString).contains("message=null");
    }

    private ErrorItem createErrorItem(String field, String message) {
        return new ErrorItem()
                .setField(field)
                .setMessage(message);
    }

}