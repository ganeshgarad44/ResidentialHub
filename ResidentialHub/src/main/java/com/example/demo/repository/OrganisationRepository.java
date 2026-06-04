package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {

}
