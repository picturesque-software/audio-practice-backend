package com.example.demo.common;

public class Response<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public static Response success() {
        Response response = new Response<>();
        response.setCode("0");
        response.setMsg("success");
        return response;
    }

    public static Response success(String msg) {
        Response response = new Response<>();
        response.setCode("0");
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> success(T data, String msg) {
        Response<T> response = new Response<>(data);
        response.setCode("0");
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>(data);
        response.setCode("0");
        response.setMsg("success");
        return response;
    }

    public static Response error(String code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
