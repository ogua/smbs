package com.oguses.cartcount.Api.Apierror;

import java.util.List;
import java.util.Map;

public class Apierrormodel {

    private String message;
    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

}
