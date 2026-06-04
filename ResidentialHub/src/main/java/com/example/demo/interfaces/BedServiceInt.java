package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.BedDto;
import com.example.demo.dto.BedGetDto;
import com.example.demo.dto.VacentBedDto;
import com.example.demo.entity.Beds;

public interface BedServiceInt {

	String addBed(BedDto bedDto);

	String deleteBed(int id);

	Beds getBed(int id);

	List<BedGetDto> getAllBeds();

	List<VacentBedDto> getVacentBed(String status, int sharing);

	List<VacentBedDto> getVacantBedsByHostel(String status, int sharing, int hostelId);

}
