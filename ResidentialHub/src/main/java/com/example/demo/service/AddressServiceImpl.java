package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HostelGetDto;
import com.example.demo.entity.Hostels;
import com.example.demo.exception.AddressServiceException;
import com.example.demo.interfaces.AddressServiceInt;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.HostelRepository;

@Service
public class AddressServiceImpl implements AddressServiceInt {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Override
	public List<HostelGetDto> searchHostels(String city, String area) {

		if ((city == null || city.trim().isEmpty()) && (area == null || area.trim().isEmpty())) {
			throw new AddressServiceException("Empty fields", HttpStatus.BAD_REQUEST);
		}

		List<Integer> l = null;

		if (city != null && area != null) {
			l = addressRepository.findHostelIdByCityAndArea(city, area);
		} else if (city != null && area == null) {
			l = addressRepository.findHostelIdByCity(city);
		} else if (city == null && area != null) {
			l = addressRepository.findHostelIdByArea(area);
		}

		if (l.isEmpty()) {
			throw new AddressServiceException("Not found", HttpStatus.NOT_FOUND);
		}

		List<Hostels> hostels = hostelRepository.findAllById(l);

		List<HostelGetDto> listhgd = new ArrayList<>();

		for (Hostels h : hostels) {

			HostelGetDto dto = new HostelGetDto();
			dto.setId(h.getId());
			dto.setHostelName(h.getHostelName());
			dto.setCapacity(h.getCapacity());
			dto.setContactNo(h.getContactNo());
			dto.setType(h.getType());
			dto.setImage(h.getImage());
			dto.setWebsite(h.getWebsite());
			dto.setOrganisationId(h.getOrganisation().getId());
			listhgd.add(dto);
		}
		return listhgd;
	}

}
