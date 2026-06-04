package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrganisationDto;
import com.example.demo.dto.OrganisationGetDto;
import com.example.demo.entity.Organisation;
import com.example.demo.exception.OrganisationServiceException;
import com.example.demo.interfaces.OrganisationServiceInt;
import com.example.demo.repository.OrganisationRepository;

@Service
public class OrganisationServiceImpl implements OrganisationServiceInt {

	@Autowired
	OrganisationRepository organisationRepository;

	@Override
	public String addOrganisation(OrganisationDto organisationDto) {
		if (organisationDto.getOrganisationName() == null || organisationDto.getOrganisationName().trim().isEmpty()
				|| organisationDto.getOwnerName() == null || organisationDto.getOwnerName().trim().isEmpty()) {
			throw new OrganisationServiceException("Empty Field", HttpStatus.BAD_REQUEST);
		}

		Organisation organisation = new Organisation();
		organisation.setOrganisationName(organisationDto.getOrganisationName());
		organisation.setOwnerName(organisationDto.getOwnerName());
		try {
			organisationRepository.save(organisation);
		} catch (Exception e) {
			throw new OrganisationServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return "Organisation Saved";
	}

	@Override
	public String deletOrganisation(int id) {
		if (id <= 0) {
			throw new OrganisationServiceException("Invalid Id", HttpStatus.BAD_REQUEST);
		}

		if (!organisationRepository.existsById(id)) {
			throw new OrganisationServiceException("Id does't exist", HttpStatus.NOT_FOUND);
		}
		organisationRepository.deleteById(id);
		return "Organisation Deleted";
	}

	@Override
	public Organisation getOrganisation(int id) {
		if (id <= 0) {
			throw new OrganisationServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}

		Optional<Organisation> o = organisationRepository.findById(id);
		if (o.isEmpty()) {
			throw new OrganisationServiceException("Id does't exist", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<OrganisationGetDto> getAllOrganisation() {
		List<OrganisationGetDto> listOrg = new ArrayList<>();

		List<Organisation> ol = organisationRepository.findAll();

		for (Organisation organisation : ol) {

			OrganisationGetDto dto = new OrganisationGetDto();
			dto.setId(organisation.getId());
			dto.setOrganisationName(organisation.getOrganisationName());
			dto.setOwnerName(organisation.getOwnerName());
			listOrg.add(dto);
		}
		return listOrg;
	}
}
