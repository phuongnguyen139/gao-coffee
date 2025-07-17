package com.example.demo.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper.TableMapper;
import com.example.demo.entity.TableEntity;
import com.example.demo.model.response.BaseResponse;
import com.example.demo.repository.TableRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableRepository tableRepository;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllTablesByStatus(@RequestParam("status") String status) {
        BaseResponse responseData =  BaseResponse.newInstance();
        responseData.setData(TableMapper.INSTANCE.tableToTableDtoList(tableRepository.findByStatus(status)));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public TableEntity getTableById(@PathVariable Integer id) {
        return tableRepository.findById(id).orElse(null);
    }

    @PostMapping
    public TableEntity createTable(@RequestBody TableEntity tableEntity) { 
        return tableRepository.save(tableEntity);
    }

    @PutMapping("/{id}")
    public TableEntity updateTable(@PathVariable Integer id, @RequestBody TableEntity tableEntity) {
        tableEntity.setTableId(id);
        return tableRepository.save(tableEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Integer id) {
        tableRepository.deleteById(id);
    }
}