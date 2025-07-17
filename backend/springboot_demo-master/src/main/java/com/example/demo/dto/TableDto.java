package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private Integer tableId;
    private Integer tableNumber;
    private String tableName;
    private String status;
    private Boolean isOccupied;
}
