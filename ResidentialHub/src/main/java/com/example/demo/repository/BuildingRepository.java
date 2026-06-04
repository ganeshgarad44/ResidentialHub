package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Buildings;

public interface BuildingRepository extends JpaRepository<Buildings, Integer> {

}
