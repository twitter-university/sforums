
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

    public static String escapeXml(String in) {
        StringBuilder out = new StringBuilder(in.length());
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            switch (ch) {
                case '<':
                    out.append("&lt;");
                    break;
                case '>':
                    out.append("&gt;");
                    break;
                case '&':
                    out.append("&amp;");
                    break;
                case '"':
                    out.append("&quot;");
                    break;
                case '\'':
                    out.append("&apos;");
                    break;
                default:
                    out.append(ch);
            }
        }
        return out.toString();
    }
}
