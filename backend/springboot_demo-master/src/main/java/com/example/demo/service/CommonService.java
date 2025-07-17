package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.MenuDTO;

public interface CommonService {
    
    List<MenuDTO> getAllMenu(String userId);
}
