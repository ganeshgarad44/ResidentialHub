package com.example.demo.dto;

import java.time.LocalDate;

public class BedDto {

	private int bedNo;
	private int sharing;
	private String status;
	private long price;
	private LocalDate date;
	private long deposite;
	private int roomId;

	public int getBedNo() {
		return bedNo;
	}

	public void setBedNo(int bedNo) {
		this.bedNo = bedNo;
	}

	public int getSharing() {
		return sharing;
	}

	public void setSharing(int sharing) {
		this.sharing = sharing;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getDeposite() {
		return deposite;
	}

	public void setDeposite(long deposite) {
		this.deposite = deposite;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
