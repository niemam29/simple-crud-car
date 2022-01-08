package com.example.crud.crud.repository;

import com.example.crud.crud.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c join c.brand b " +
            "where lower(b.name) like lower(concat('%', :searchTerm, '%')) or lower(c.model) like lower(concat('%', :searchTerm, '%'))")
    List<Car> search(@Param("searchTerm") String searchTerm);
}
