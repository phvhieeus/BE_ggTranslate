package com.translate.domain.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    private int statusCode;
    private String error;

    //message co the la String, hoac arrayList
    private Object message;
    private T data;
}
