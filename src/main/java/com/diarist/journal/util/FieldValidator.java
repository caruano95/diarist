package com.diarist.journal.util;

import spark.Request;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Implementation of a simple for validation that simply validates that a request holds all
 * necessary parameters.
 */
public class FieldValidator {
    private final List<String> fields;

    public FieldValidator(String[] fields) {
        this.fields = Arrays.asList(fields);
    }

    public boolean valid(Request request) {
        Predicate<String> p1 = e -> e == "INVALID";

        Stream<String> validations = fields.stream().map(p -> {
            if (request.queryParams(p).length() == 0) {
                return "INVALID";
            } else {
                return "VALID";
            }
        });

        return validations.noneMatch(p1);
    }
}
