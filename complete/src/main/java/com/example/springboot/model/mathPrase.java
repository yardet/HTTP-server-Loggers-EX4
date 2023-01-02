package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class mathPrase {
    private final ArrayList<Integer> arguments;

    private final String operator;

    public mathPrase(@JsonProperty("arguments") ArrayList<Integer> arguments, @JsonProperty("operation") String operator) {
        this.arguments = arguments;
        this.operator = operator;
    }

    public ArrayList<Integer> getArguments() {
        return arguments;
    }

    public String getOperator() {
        return operator;
    }
}
