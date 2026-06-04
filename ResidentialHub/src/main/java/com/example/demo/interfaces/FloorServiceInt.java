package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.FloorGetDto;
import com.example.demo.dto.FloorsDto;
import com.example.demo.entity.Floors;

public interface FloorServiceInt {

	String addFloor(FloorsDto floorsDto);

	String deleteFloor(int id);

	Floors getFloor(int id);

	List<FloorGetDto> getAllFloor();

	List<FloorGetDto> getFloorsByBuildingId(int buildingId);
	
}
