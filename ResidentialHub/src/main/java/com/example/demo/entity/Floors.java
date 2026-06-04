package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Floors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int floorNo;
	private int noRooms;

	@ManyToOne
	@JoinColumn(name = "building_id", referencedColumnName = "id")
	private Buildings building;

	@OneToMany(mappedBy = "floors", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rooms> room;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}

	public int getNoRooms() {
		return noRooms;
	}

	public void setNoRooms(int noRooms) {
		this.noRooms = noRooms;
	}

	public Buildings getBuilding() {
		return building;
	}

	public void setBuilding(Buildings building) {
		this.building = building;
	}

	public List<Rooms> getRoom() {
		return room;
	}

	public void setRoom(List<Rooms> room) {
		this.room = room;
	}

}
