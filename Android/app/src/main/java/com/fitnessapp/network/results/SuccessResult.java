package com.fitnessapp.network.results;

import com.fitnessapp.network.NetworkResult;

public class SuccessResult<T> extends NetworkResult<T>
{
    public SuccessResult(T data)
    {
        super(data);
    }
}
