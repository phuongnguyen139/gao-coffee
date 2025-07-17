package com.example.demo.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.response.BaseResponse;
import com.example.demo.service.CommonService;

@RestController
@RequestMapping("/menu")
public class MenusController {
    
    @Autowired
    private CommonService commonService;
    
    @GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    private ResponseEntity<BaseResponse> getMenuByRole(@PathVariable String userId) {
//        commonService.getAllMenu(userId);
        BaseResponse resposeData = BaseResponse.newInstance();
        resposeData.setData(commonService.getAllMenu(userId));
        return ResponseEntity.ok(resposeData);
    }
}
