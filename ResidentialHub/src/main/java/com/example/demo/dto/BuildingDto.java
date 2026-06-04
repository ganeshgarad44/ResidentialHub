package com.example.demo.dto;

public class BuildingDto {

	private String name;
	private String warden;
	private int hostelId;

	public int getHostelId() {
		return hostelId;
	}

	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWarden() {
		return warden;
	}

	public void setWarden(String warden) {
		this.warden = warden;
	}
}
