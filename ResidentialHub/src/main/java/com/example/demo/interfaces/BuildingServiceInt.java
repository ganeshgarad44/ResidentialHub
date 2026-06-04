package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.BuildingDto;
import com.example.demo.dto.BuildingGetDto;
import com.example.demo.entity.Buildings;

public interface BuildingServiceInt {

	String addBuiding(BuildingDto buildingDto);

	String deleteBuilding(int id);

	Buildings getBuilding(int id);

	List<BuildingGetDto> getAllBuilding();

	List<BuildingGetDto> getBuildingsByHostelId(int hostelId);
	
}
