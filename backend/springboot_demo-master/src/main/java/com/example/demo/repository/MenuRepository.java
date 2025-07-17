package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    
    @Query(value = "SELECT distinct m.* FROM menus m " +
            "JOIN menus_role mr ON m.id = mr.menu_id " +
            "JOIN roles r ON mr.roles_id = r.id " +
            "WHERE r.name in (:roleNames)", nativeQuery = true)
    
    List<MenuEntity> findMenuByRole(@Param("roleNames") List<String> roleNames);
}
