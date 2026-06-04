package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BedDto;
import com.example.demo.dto.BedGetDto;
import com.example.demo.dto.VacentBedDto;
import com.example.demo.entity.Beds;
import com.example.demo.entity.Buildings;
import com.example.demo.entity.Floors;
import com.example.demo.entity.Rooms;
import com.example.demo.exception.BedServiceException;
import com.example.demo.interfaces.BedServiceInt;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class BedServiceImpl implements BedServiceInt {

	@Autowired
	BedRepository bedRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public String addBed(BedDto bedDto) {
		Optional<Rooms> o = roomRepository.findById(bedDto.getRoomId());
		if (o.isEmpty()) {
			throw new BedServiceException("Room not found", HttpStatus.NOT_FOUND);
		}
		Rooms r1 = o.get();

		int bedCount = r1.getBed().size();
		int roomBedCount = bedRepository.findRoomCapacityByRoomId(bedDto.getRoomId());

		if (bedCount >= roomBedCount) {
			throw new BedServiceException("Beds Capacity : " + roomBedCount + " you added " + bedCount,
					HttpStatus.BAD_REQUEST);
		}

		if (bedDto.getBedNo() <= 0 || bedDto.getSharing() <= 0 || bedDto.getStatus() == null
				|| bedDto.getStatus().trim().isEmpty() || bedDto.getPrice() <= 0) {
			throw new BedServiceException("Invalid or Empty field", HttpStatus.BAD_REQUEST);
		}

		boolean beds = bedRepository.existsByBedNoAndRoomId(bedDto.getBedNo(), bedDto.getRoomId());
		if (beds) {
			throw new BedServiceException("Bed No already exist in room", HttpStatus.BAD_REQUEST);
		}

		if (!(bedDto.getStatus().equalsIgnoreCase("Available") || bedDto.getStatus().equalsIgnoreCase("Booked"))) {
			throw new BedServiceException("Invalid Status", HttpStatus.BAD_REQUEST);
		}

		Beds b1 = new Beds();
		b1.setBedNo(bedDto.getBedNo());
		b1.setSharing(bedDto.getSharing());
		b1.setStatus(bedDto.getStatus());
		b1.setPrice(bedDto.getPrice());
		b1.setDate(LocalDate.now());
		b1.setDeposite(bedDto.getDeposite());
		b1.setRoom(r1);
		bedRepository.save(b1);
		return "Bed Saved";

	}

	@Override
	public String deleteBed(int id) {
		if (id <= 0) {
			throw new BedServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		if (!bedRepository.existsById(id)) {
			throw new BedServiceException("Not found", HttpStatus.NOT_FOUND);
		}
		bedRepository.deleteById(id);
		return "Bed Deleted";
	}

	@Override
	public Beds getBed(int id) {
		if (id <= 0) {
			throw new BedServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}
		Optional<Beds> o = bedRepository.findById(id);
		if (o.isEmpty()) {
			throw new BedServiceException("Not found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<BedGetDto> getAllBeds() {

		List<Beds> bl = bedRepository.findAll();

		List<BedGetDto> listBgd = new ArrayList<>();

		for (Beds bed : bl) {

			BedGetDto dto = new BedGetDto();
			dto.setId(bed.getId());
			dto.setBedNo(bed.getBedNo());
			dto.setSharing(bed.getSharing());
			dto.setStatus(bed.getStatus());
			dto.setPrice(bed.getPrice());
			dto.setDate(bed.getDate());
			dto.setRoomId(bed.getRoom().getId());
			listBgd.add(dto);
		}
		return listBgd;
	}

	@Override
	public List<VacentBedDto> getVacentBed(String status, int sharing) {

		List<Beds> beds = bedRepository.findVacantBeds(status, sharing);

		List<VacentBedDto> dtoList = new ArrayList<>();

		for (Beds bed : beds) {

			VacentBedDto dto = new VacentBedDto();

			Rooms rooms = bed.getRoom();
			Floors floors = rooms.getFloors();
			Buildings buildings = floors.getBuilding();

			dto.setId(bed.getId());
			dto.setBedNo(bed.getBedNo());
			dto.setPrice(bed.getPrice());
			dto.setSharing(bed.getSharing());
			dto.setStatus(bed.getStatus());
			dto.setRoomNo(rooms.getRoomNo());
			dto.setFloorNo(floors.getFloorNo());
			dto.setBuildingName(buildings.getName());
			dto.setHostelName(buildings.getHostel().getHostelName());
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<VacentBedDto> getVacantBedsByHostel(String status, int sharing, int hostelId) {

		List<Beds> beds = bedRepository.findVacantBeds(status, sharing);

		List<VacentBedDto> dtoList = new ArrayList<>();

		for (Beds bed : beds) {
			Rooms rooms = bed.getRoom();
			if (rooms != null && rooms.getFloors() != null && rooms.getFloors().getBuilding() != null
					&& rooms.getFloors().getBuilding().getHostel() != null) {

				if (rooms.getFloors().getBuilding().getHostel().getId() == hostelId) {
					VacentBedDto dto = new VacentBedDto();
					Floors floors = rooms.getFloors();
					Buildings buildings = floors.getBuilding();

					dto.setId(bed.getId());
					dto.setBedNo(bed.getBedNo());
					dto.setPrice(bed.getPrice());
					dto.setSharing(bed.getSharing());
					dto.setStatus(bed.getStatus());
					dto.setRoomNo(rooms.getRoomNo());
					dto.setFloorNo(floors.getFloorNo());
					dto.setBuildingName(buildings.getName());
					dto.setHostelName(buildings.getHostel().getHostelName());
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
	}
}