package org.openchat.acceptance.formatters

import com.tngtech.jgiven.annotation.Format
import com.tngtech.jgiven.format.ArgumentFormatter

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Format(value = ArrayFormatter.class)
@Retention(RetentionPolicy.RUNTIME)
@interface ArrayFormat {}

class ArrayFormatter implements ArgumentFormatter<String[]> {
    @Override
    String format(String[] argumentToFormat, String... formatterArguments) {
        def result = argumentToFormat
                .toList()
                .stream()
                .map { "\"$it\"" }
                .toArray()
                .join(", ")
        int lastComma = result.lastIndexOf(", ")
        return result.substring(0, lastComma) + " and" + result.substring(lastComma + 1)
    }
}
