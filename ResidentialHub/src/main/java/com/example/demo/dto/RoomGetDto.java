package com.example.demo.dto;

public class RoomGetDto {

	private int id;
	private int roomNo;
	private int totalBed;
	private int countAddedBed;
	private String type;
	private int floorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public int getTotalBed() {
		return totalBed;
	}

	public void setTotalBed(int totalBed) {
		this.totalBed = totalBed;
	}

	public int getCountAddedBed() {
		return countAddedBed;
	}

	public void setCountAddedBed(int countAddedBed) {
		this.countAddedBed = countAddedBed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
}
