package com.edwise.completespring.testutil;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

public class IsValidFormatDateYMDMatcher extends TypeSafeMatcher<String> {

    private static final String DATE_VALIDATION_PATTERN_YMD = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    private final Pattern pattern;

    public IsValidFormatDateYMDMatcher() {
        pattern = Pattern.compile(DATE_VALIDATION_PATTERN_YMD);
    }

    @Override
    protected boolean matchesSafely(String date) {
        boolean itMatches = false;
        if (date != null) {
            java.util.regex.Matcher matcher = pattern.matcher(date);
            itMatches = matcher.matches();
        }
        return itMatches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not a valid YMD format date");
    }

    @Factory
    public static Matcher<String> validFormatDateYMD() {
        return new IsValidFormatDateYMDMatcher();
    }

}
