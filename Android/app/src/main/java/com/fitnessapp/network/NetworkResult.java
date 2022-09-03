package com.fitnessapp.network;

public class NetworkResult<T>{
    private T data;
    private  String message;

    public NetworkResult(){}

    public NetworkResult(T data)
    {
        this.data = data;
    }

    public NetworkResult(String message)
    {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
