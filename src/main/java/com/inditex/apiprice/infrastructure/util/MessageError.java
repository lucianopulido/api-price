package com.inditex.apiprice.infrastructure.util;

public class MessageError {
    private MessageError() {
    }

    public static final String PRICE_NOT_FOUND = "No price found for the given parameters";
    public static final String MISSING_REQUEST_PARAMETER = "Required request parameter is missing";
    public static final String ARGUMENT_TYPE_MISMATCH_TEMPLATE = "The parameter '%s' of value '%s' could not be converted to type '%s'";
    public static final String MALFORMED_JSON_REQUEST = "Malformed JSON request body";
    public static final String UNEXPECTED_ERROR = "Unexpected internal server error";
    public static final String ARGUMENT_TYPE_MISMATCH = "Argument type mismatch";
}
