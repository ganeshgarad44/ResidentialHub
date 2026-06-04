package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.HostelDto;
import com.example.demo.dto.HostelGetDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Hostels;
import com.example.demo.entity.Organisation;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.interfaces.HostelServiceInt;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganisationRepository;

@Service
public class HostelServiceImpl implements HostelServiceInt {

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	OrganisationRepository organisationRepository;

	@Override
	public String addHostel(HostelDto hostelDto) {

		Optional<Organisation> o1 = organisationRepository.findById(hostelDto.getOrganisationId());
		if (o1.isEmpty()) {
			throw new HostelServiceException("Empty Organisation", HttpStatus.BAD_REQUEST);
		}
		Organisation organisation = o1.get();

		if (hostelDto.getHostelName() == null || hostelDto.getHostelName().trim().isEmpty()
				|| hostelDto.getCapacity() <= 0 || hostelDto.getContactNo() <= 0 || hostelDto.getType() == null
				|| hostelDto.getType().trim().isEmpty()) {
			throw new HostelServiceException("Empty Field", HttpStatus.BAD_REQUEST);
		}

		if (String.valueOf(hostelDto.getContactNo()).length() != 10) {
			throw new HostelServiceException("Invalid Contact No", HttpStatus.BAD_REQUEST);
		}

		Hostels hostels = new Hostels();
		hostels.setHostelName(hostelDto.getHostelName());
		hostels.setCapacity(hostelDto.getCapacity());
		hostels.setContactNo(hostelDto.getContactNo());
		hostels.setType(hostelDto.getType());
		hostels.setImage(hostelDto.getImage());
		hostels.setWebsite(hostelDto.getWebsite());
		hostels.setOrganisation(organisation);

		AddressDto addressDto = hostelDto.getAddressDto();
		if (addressDto == null) {
			throw new HostelServiceException("Empty Address", HttpStatus.BAD_REQUEST);
		}

		if (addressDto.getArea() == null || addressDto.getArea().trim().isEmpty() || addressDto.getCity() == null
				|| addressDto.getCity().trim().isEmpty() || addressDto.getPin() <= 0 || addressDto.getState() == null
				|| addressDto.getState().trim().isEmpty() || addressDto.getCountry() == null
				|| addressDto.getCountry().trim().isEmpty()) {
			throw new HostelServiceException("Empty Address Fields", HttpStatus.BAD_REQUEST);
		}

		Address address = new Address();
		address.setArea(addressDto.getArea());
		address.setCity(addressDto.getCity());
		address.setPin(addressDto.getPin());
		address.setState(addressDto.getState());
		address.setCountry(addressDto.getCountry());
		address.setHostels(hostels);

		hostels.setAddress(address);

		try {
			hostelRepository.save(hostels);
		} catch (Exception e) {
			throw new HostelServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return "Hostel Saved";
	}

	@Override
	public String deletehostel(int id) {
		if (id <= 0) {
			throw new HostelServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		if (!hostelRepository.existsById(id)) {
			throw new HostelServiceException("Id doesn't exist", HttpStatus.NOT_FOUND);
		}
		hostelRepository.deleteById(id);
		return "Hostel Deleted";
	}

	@Override
	public Hostels getHostel(int id) {
		if (id <= 0) {
			throw new HostelServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		Optional<Hostels> o = hostelRepository.findById(id);
		if (o.isEmpty()) {
			throw new HostelServiceException("Hostel not found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	public List<HostelGetDto> getAllHostel() {

		List<HostelGetDto> listHgd = new ArrayList<>();

		List<Hostels> hl = hostelRepository.findAll();

		for (Hostels hostels : hl) {

			HostelGetDto hostelGetDto = new HostelGetDto();
			hostelGetDto.setId(hostels.getId());
			hostelGetDto.setHostelName(hostels.getHostelName());
			hostelGetDto.setCapacity(hostels.getCapacity());
			hostelGetDto.setContactNo(hostels.getContactNo());
			hostelGetDto.setType(hostels.getType());
			hostelGetDto.setImage(hostels.getImage());
			hostelGetDto.setWebsite(hostels.getWebsite());
			hostelGetDto.setOrganisationId(hostels.getOrganisation().getId());
			listHgd.add(hostelGetDto);
		}
		return listHgd;
	}

	@Override
	public List<HostelGetDto> getHostelsByOrganisationId(int organisationId) {

		List<HostelGetDto> listHgd = new ArrayList<>();

		List<Hostels> hl = hostelRepository.findAll();

		for (Hostels hostels : hl) {
			if (hostels.getOrganisation() != null && hostels.getOrganisation().getId() == organisationId) {

				HostelGetDto hostelGetDto = new HostelGetDto();
				hostelGetDto.setId(hostels.getId());
				hostelGetDto.setHostelName(hostels.getHostelName());
				hostelGetDto.setCapacity(hostels.getCapacity());
				hostelGetDto.setContactNo(hostels.getContactNo());
				hostelGetDto.setType(hostels.getType());
				hostelGetDto.setImage(hostels.getImage());
				hostelGetDto.setWebsite(hostels.getWebsite());
				hostelGetDto.setOrganisationId(hostels.getOrganisation().getId());
				listHgd.add(hostelGetDto);
			}
		}
		return listHgd;
	}
}