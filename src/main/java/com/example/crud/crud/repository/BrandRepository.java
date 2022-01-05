package com.example.crud.crud.repository;

import com.example.crud.crud.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select c from Brand c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<Brand> search(@Param("searchTerm") String searchTerm);
}