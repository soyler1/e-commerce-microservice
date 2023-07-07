package com.ecommercemicro.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseResponse<T> {
    private int responseCode;
    private String responseDesc;
    private T data;
}