package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoomDto;
import com.example.demo.dto.RoomGetDto;
import com.example.demo.entity.Beds;
import com.example.demo.entity.Floors;
import com.example.demo.entity.Rooms;
import com.example.demo.exception.OrganisationServiceException;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.interfaces.RoomServiceInt;
import com.example.demo.repository.FloorsRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomServiceInt {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	FloorsRepository floorsRepository;

	@Override
	public String addRoom(List<RoomDto> listRoomDto) {

		for (RoomDto dto : listRoomDto) {

			Optional<Floors> o = floorsRepository.findById(dto.getFloorId());
			if (o.isEmpty()) {
				throw new RoomServiceException("No floor found", HttpStatus.NOT_FOUND);
			}
			Floors f1 = o.get();

			if (dto.getRoomNo() <= 0 || dto.getTotalBed() <= 0 || dto.getType() == null
					|| dto.getType().trim().isEmpty()) {
				throw new RoomServiceException("Invalid or Empty field", HttpStatus.BAD_REQUEST);
			}

			boolean exists = roomRepository.existsByRoomNoAndFloors_Id(dto.getRoomNo(), dto.getFloorId());

			if (exists) {
				throw new RoomServiceException("Room already exists in this floor", HttpStatus.BAD_REQUEST);
			}

			Rooms r1 = new Rooms();
			r1.setRoomNo(dto.getRoomNo());
			r1.setTotalBed(dto.getTotalBed());
			r1.setType(dto.getType());
			r1.setFloors(f1);

			List<Beds> listBeds = new ArrayList<>();
			r1.setBed(listBeds);

			for (Beds bedDto : dto.getBed()) {

				if (bedDto.getBedNo() <= 0 || bedDto.getSharing() <= 0 || bedDto.getStatus() == null
						|| bedDto.getStatus().trim().isEmpty() || bedDto.getPrice() <= 0) {
					throw new RoomServiceException("Invalid or Empty field", HttpStatus.BAD_REQUEST);
				}

				Beds bed = new Beds();
				bed.setBedNo(bedDto.getBedNo());
				bed.setSharing(bedDto.getSharing());
				bed.setStatus(bedDto.getStatus());
				bed.setPrice(bedDto.getPrice());
				bed.setDate(LocalDate.now());
				bed.setRoom(r1);
				listBeds.add(bed);
			}
			try {
				roomRepository.save(r1);
			} catch (Exception e) {
				throw new OrganisationServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return "Room Saved";
	}

	@Override
	public String deleteRoom(int id) {
		if (id <= 0) {
			throw new RoomServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}

		if (!roomRepository.existsById(id)) {
			throw new RoomServiceException("Not found", HttpStatus.NOT_FOUND);
		}

		roomRepository.deleteById(id);
		return "Room Deletd";
	}

	@Override
	public Rooms getRoom(int id) {
		if (id <= 0) {
			throw new RoomServiceException("Invalid id", HttpStatus.BAD_REQUEST);
		}

		Optional<Rooms> o = roomRepository.findById(id);
		if (o.isEmpty()) {
			throw new RoomServiceException("Not found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<RoomGetDto> getAllRoom() {
		List<RoomGetDto> listRgd = new ArrayList<>();

		List<Rooms> rl = roomRepository.findAll();

		for (Rooms room : rl) {

			RoomGetDto roomGetDto = new RoomGetDto();
			roomGetDto.setId(room.getId());
			roomGetDto.setRoomNo(room.getRoomNo());
			roomGetDto.setTotalBed(room.getTotalBed());
			roomGetDto.setType(room.getType());
			roomGetDto.setFloorId(room.getFloors().getId());
			roomGetDto.setCountAddedBed(room.getBed().size());
			listRgd.add(roomGetDto);
		}

		return listRgd;
	}

	@Override
	public List<RoomGetDto> getRoomsByFloorId(int floorId) {
		List<RoomGetDto> listRgd = new ArrayList<>();

		List<Rooms> rl = roomRepository.findAll();

		for (Rooms room : rl) {
			if (room.getFloors() != null && room.getFloors().getId() == floorId) {

				RoomGetDto roomGetDto = new RoomGetDto();
				roomGetDto.setId(room.getId());
				roomGetDto.setRoomNo(room.getRoomNo());
				roomGetDto.setTotalBed(room.getTotalBed());
				roomGetDto.setType(room.getType());
				roomGetDto.setFloorId(room.getFloors().getId());
				roomGetDto.setCountAddedBed(room.getBed().size());
				listRgd.add(roomGetDto);
			}
		}

		return listRgd;
	}
}