
package com.marakana.sforums.web;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSPFunctions {
    private JSPFunctions() {

    }

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\\r?\\n");

    public static Date now() {
        return new Date();
    }

    public static String convertToHtmlLineBreaks(String in) {
        Matcher matcher = NEW_LINE_PATTERN.matcher(in);
        return matcher.find() ? matcher.replaceAll("<br />") : in;
    }

    public static String trimToLength(String in, int length) {
        return (in == null || in.length() <= length) ? in : in.substring(0,
                length - Math.min(3, length))
                + "...";
    }

    public static String getType(Object o) {
        return o == null ? null : o.getClass().getName();
    }
}
