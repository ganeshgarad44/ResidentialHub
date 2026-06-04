package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.HostelDto;
import com.example.demo.dto.HostelGetDto;
import com.example.demo.entity.Hostels;

public interface HostelServiceInt {

	String addHostel(HostelDto hostelDto);

	String deletehostel(int id);

	Hostels getHostel(int id);

//	List<Hostels> getAllHostel();

	List<HostelGetDto> getHostelsByOrganisationId(int organisationId);
	
	List<HostelGetDto> getAllHostel();
}
