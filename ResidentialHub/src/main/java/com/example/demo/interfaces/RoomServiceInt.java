package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.RoomDto;
import com.example.demo.dto.RoomGetDto;
import com.example.demo.entity.Rooms;

public interface RoomServiceInt {

	String addRoom(List<RoomDto> listRoomDto);

	String deleteRoom(int id);

	Rooms getRoom(int id);

	List<RoomGetDto> getAllRoom();

	List<RoomGetDto> getRoomsByFloorId(int floorId);
}
