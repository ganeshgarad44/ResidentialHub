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
public class Buildings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int totalFloors;
	private String warden;

	@ManyToOne
	@JoinColumn(name = "hostel_id", referencedColumnName = "id")
	private Hostels hostel;

	@OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Floors> floors;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalFloors() {
		return totalFloors;
	}

	public void setTotalFloors(int totalFloors) {
		this.totalFloors = totalFloors;
	}

	public String getWarden() {
		return warden;
	}

	public void setWarden(String warden) {
		this.warden = warden;
	}

	public Hostels getHostel() {
		return hostel;
	}

	public void setHostel(Hostels hostel) {
		this.hostel = hostel;
	}

	public List<Floors> getFloors() {
		return floors;
	}

	public void setFloors(List<Floors> floors) {
		this.floors = floors;
	}

}
