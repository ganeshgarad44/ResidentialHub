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
public class Rooms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int roomNo;
	private int totalBed;
	private String type;

	@ManyToOne
	@JoinColumn(name = "floor_id", referencedColumnName = "id")
	private Floors floors;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Beds> bed;

	public Floors getFloors() {
		return floors;
	}

	public void setFloors(Floors floors) {
		this.floors = floors;
	}

	public List<Beds> getBed() {
		return bed;
	}

	public void setBed(List<Beds> bed) {
		this.bed = bed;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
