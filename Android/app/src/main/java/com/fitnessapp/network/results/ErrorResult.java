package com.fitnessapp.network.results;

import com.fitnessapp.network.NetworkResult;

public class ErrorResult<T> extends NetworkResult<T>
{
    public ErrorResult(String message)
    {
        super(message);
    }
}
