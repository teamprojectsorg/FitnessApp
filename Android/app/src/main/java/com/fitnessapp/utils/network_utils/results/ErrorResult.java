package com.fitnessapp.utils.network_utils.results;

import com.fitnessapp.utils.network_utils.NetworkResult;

public class ErrorResult<T> extends NetworkResult<T>
{
    public ErrorResult(String message)
    {
        super(message);
    }
}
