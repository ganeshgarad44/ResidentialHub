package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BuildingDto;
import com.example.demo.dto.BuildingGetDto;
import com.example.demo.entity.Buildings;
import com.example.demo.entity.Hostels;
import com.example.demo.exception.BuildingServiceException;
import com.example.demo.exception.OrganisationServiceException;
import com.example.demo.interfaces.BuildingServiceInt;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.HostelRepository;

@Service
public class BuildingServiceImpl implements BuildingServiceInt {

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Override
	public String addBuiding(BuildingDto buildingDto) {

		Optional<Hostels> o = hostelRepository.findById(buildingDto.getHostelId());
		if (o.isEmpty()) {
			throw new BuildingServiceException("No hostel found", HttpStatus.NOT_FOUND);
		}
		Hostels hostel = o.get();

		if (buildingDto.getName() == null || buildingDto.getName().trim().isEmpty() || buildingDto.getWarden() == null
				|| buildingDto.getWarden().trim().isEmpty()) {
			throw new BuildingServiceException("Empty field", HttpStatus.BAD_REQUEST);
		}

		Buildings b1 = new Buildings();
		b1.setName(buildingDto.getName());
		b1.setWarden(buildingDto.getWarden());
		b1.setHostel(hostel);

		try {
			buildingRepository.save(b1);
		} catch (Exception e) {
			throw new OrganisationServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return "Building Saved";
	}

	@Override
	public String deleteBuilding(int id) {
		if (id <= 0) {
			throw new BuildingServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		if (!buildingRepository.existsById(id)) {
			throw new BuildingServiceException("Id doesn't exist", HttpStatus.NOT_FOUND);
		}
		buildingRepository.deleteById(id);
		return "Building Deleted";
	}

	@Override
	public Buildings getBuilding(int id) {
		if (id <= 0) {
			throw new BuildingServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		Optional<Buildings> o = buildingRepository.findById(id);
		if (o.isEmpty()) {
			throw new BuildingServiceException("Id doesn't exist", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<BuildingGetDto> getAllBuilding() {

		List<BuildingGetDto> listBgd = new ArrayList<>();

		List<Buildings> bl = buildingRepository.findAll();

		for (Buildings buildings : bl) {

			BuildingGetDto dto = new BuildingGetDto();
			dto.setId(buildings.getId());
			dto.setName(buildings.getName());
			dto.setWarden(buildings.getWarden());
			dto.setHostelId(buildings.getHostel().getId());
			listBgd.add(dto);
		}
		return listBgd;
	}

	@Override
	public List<BuildingGetDto> getBuildingsByHostelId(int hostelId) {

		List<BuildingGetDto> listBgd = new ArrayList<>();

		List<Buildings> bl = buildingRepository.findAll();

		for (Buildings buildings : bl) {
			if (buildings.getHostel() != null && buildings.getHostel().getId() == hostelId) {

				BuildingGetDto dto = new BuildingGetDto();
				dto.setId(buildings.getId());
				dto.setName(buildings.getName());
				dto.setWarden(buildings.getWarden());
				dto.setHostelId(buildings.getHostel().getId());
				listBgd.add(dto);
			}
		}
		return listBgd;
	}
}