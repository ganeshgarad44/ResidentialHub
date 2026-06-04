package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.OrganisationDto;
import com.example.demo.dto.OrganisationGetDto;
import com.example.demo.entity.Organisation;

public interface OrganisationServiceInt {

	String addOrganisation(OrganisationDto organisationDto);

	String deletOrganisation(int id);

	Organisation getOrganisation(int id);

	List<OrganisationGetDto> getAllOrganisation();
}
