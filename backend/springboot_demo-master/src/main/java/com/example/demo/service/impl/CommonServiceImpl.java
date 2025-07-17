package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MenuDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.MenuEntity;
import com.example.demo.repository.MenuRepository;
import com.example.demo.service.CommonService;
import com.example.demo.service.UserService;

@Service
public class CommonServiceImpl implements CommonService{
    
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserService userService;
    
    @Override
    public List<MenuDTO> getAllMenu(String userId) {
        
        UserDto userDto = userService.getUserByUserId(userId);
        
        return convertToDtoList(menuRepository.findMenuByRole(userDto.getRoles().stream().toList()));
    }
    
    
    public List<MenuDTO> convertToDtoList(List<MenuEntity> menuEntities) {
        // Dùng stream để map danh sách entity sang DTO
        return menuEntities.stream()
                           .map(this::convertToDto)  // Dùng hàm convertToDto để map từng entity
                           .collect(Collectors.toList());
    }
    
    public MenuDTO convertToDto(MenuEntity menuEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(menuEntity, MenuDTO.class);
    }


}
