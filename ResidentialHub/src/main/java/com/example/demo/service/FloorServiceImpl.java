package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FloorGetDto;
import com.example.demo.dto.FloorsDto;
import com.example.demo.entity.Buildings;
import com.example.demo.entity.Floors;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.exception.OrganisationServiceException;
import com.example.demo.interfaces.FloorServiceInt;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorsRepository;

@Service
public class FloorServiceImpl implements FloorServiceInt {

	@Autowired
	FloorsRepository floorsRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Override
	public String addFloor(FloorsDto floorsDto) {

		Optional<Buildings> o = buildingRepository.findById(floorsDto.getBuildingId());
		if (o.isEmpty()) {
			throw new FloorServiceException("No Building found", HttpStatus.NOT_FOUND);
		}
		Buildings b1 = o.get();

		if (floorsDto.getFloorNo() <= 0 || floorsDto.getNoRooms() <= 0) {
			throw new FloorServiceException("Invalid data", HttpStatus.BAD_REQUEST);
		}

		Floors f1 = new Floors();
		f1.setFloorNo(floorsDto.getFloorNo());
		f1.setNoRooms(floorsDto.getNoRooms());
		f1.setBuilding(b1);

		b1.getFloors().add(f1);
		b1.setTotalFloors(b1.getTotalFloors() + 1);

		try {
			buildingRepository.save(b1);
		} catch (Exception e) {
			throw new OrganisationServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return "Floors Saved";
	}

	@Override
	public String deleteFloor(int id) {
		if (id <= 0) {
			throw new FloorServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		Optional<Floors> o = floorsRepository.findById(id);
		if (o.isEmpty()) {
			throw new FloorServiceException("Floor not found", HttpStatus.NOT_FOUND);
		}
		Floors floor = o.get();
		Buildings building = floor.getBuilding();

		building.getFloors().remove(floor);
		floorsRepository.deleteById(id);

		building.setTotalFloors(building.getTotalFloors() - 1);
		buildingRepository.save(building);

		return "Floor Deleted";
	}

	@Override
	public Floors getFloor(int id) {
		if (id <= 0) {
			throw new FloorServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		Optional<Floors> o = floorsRepository.findById(id);
		if (o.isEmpty()) {
			throw new FloorServiceException("Floor not found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<FloorGetDto> getAllFloor() {
		List<FloorGetDto> listFgd = new ArrayList<>();

		List<Floors> fl = floorsRepository.findAll();

		for (Floors floors : fl) {

			FloorGetDto dto = new FloorGetDto();
			dto.setId(floors.getId());
			dto.setFloorNo(floors.getFloorNo());
			dto.setNoRooms(floors.getNoRooms());
			dto.setBuildingId(floors.getBuilding().getId());
			listFgd.add(dto);
		}
		return listFgd;
	}

	@Override
	public List<FloorGetDto> getFloorsByBuildingId(int buildingId) {
		List<FloorGetDto> listFgd = new ArrayList<>();

		List<Floors> fl = floorsRepository.findAll();

		for (Floors floors : fl) {
			if (floors.getBuilding() != null && floors.getBuilding().getId() == buildingId) {

				FloorGetDto dto = new FloorGetDto();
				dto.setId(floors.getId());
				dto.setFloorNo(floors.getFloorNo());
				dto.setNoRooms(floors.getNoRooms());
				dto.setBuildingId(floors.getBuilding().getId());
				listFgd.add(dto);
			}
		}
		return listFgd;
	}
}