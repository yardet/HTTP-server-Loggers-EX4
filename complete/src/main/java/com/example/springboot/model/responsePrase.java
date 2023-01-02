package com.example.springboot.model;

public class responsePrase {
    private  Integer result;
    private  String error_message;

    public responsePrase(Integer result, String error_message) {
        this.result = result;
        this.error_message = error_message;
    }

    public responsePrase(int result) {
        this.result = result;
       // this.error_message = "";
    }

    public responsePrase(String error_message) {
       // this.result = result;
        this.error_message = "";
    }



    public Integer getResult() {
        return result;
    }
    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
    public void setResult(Integer result) {
        this.result = result;
    }
}
