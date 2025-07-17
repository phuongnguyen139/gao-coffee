package com.example.demo.model.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    private String status;
    private String mesage;
    private boolean error;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static BaseResponse newInstance(){
        BaseResponse base = new BaseResponse();
        base.setStatus(HttpStatus.Series.SUCCESSFUL.toString());
        return base;
    }
}
