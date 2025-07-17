package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TableEntity;

public interface TableRepository extends JpaRepository<TableEntity, Integer> {

//    Optional<List<TableEntity>> findAllByStatus();
    List<TableEntity> findByStatus(String status);
}
