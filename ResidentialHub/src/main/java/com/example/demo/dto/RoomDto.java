package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Beds;

public class RoomDto {

	private int roomNo;
	private int totalBed;
	private String type;
	private int floorId;

	List<Beds> bed;

	public List<Beds> getBed() {
		return bed;
	}

	public void setBed(List<Beds> bed) {
		this.bed = bed;
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
