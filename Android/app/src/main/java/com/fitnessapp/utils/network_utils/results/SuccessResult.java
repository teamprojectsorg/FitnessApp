package com.fitnessapp.utils.network_utils.results;

import com.fitnessapp.utils.network_utils.NetworkResult;

public class SuccessResult<T> extends NetworkResult<T>
{
    public SuccessResult(T data)
    {
        super(data);
    }
}
