package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.TableDto;
import com.example.demo.entity.TableEntity;

@Mapper
public interface TableMapper {
    TableMapper INSTANCE = Mappers.getMapper( TableMapper.class );
    
//    @Mapping(source = "numberOfSeats", target = "seatCount")
    TableDto tableToTableDto(TableEntity entity);
    
    List<TableDto> tableToTableDtoList(List<TableEntity> entity);
}
