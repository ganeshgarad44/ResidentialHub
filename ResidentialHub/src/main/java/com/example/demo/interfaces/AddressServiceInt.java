package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.HostelGetDto;

public interface AddressServiceInt {

	List<HostelGetDto> searchHostels(String city, String area);

}
