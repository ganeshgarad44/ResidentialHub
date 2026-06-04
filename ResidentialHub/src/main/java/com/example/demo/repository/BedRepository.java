package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Beds;

public interface BedRepository extends JpaRepository<Beds, Integer> {

	@Query("SELECT b FROM Beds b WHERE b.status = :status AND b.sharing = :sharing")
	List<Beds> findVacantBeds(@Param("status") String status, @Param("sharing") int sharing);

	boolean existsByBedNoAndRoomId(int bedNo, int roomId);

	@Query("SELECT r.totalBed FROM Rooms r WHERE r.id = :roomId")
	Integer findRoomCapacityByRoomId(@Param("roomId") int roomId);

//	@Query("select r.totalBed from Rooms r where r.id = :roomId ")
//	int findBedCountByRoomId(@Param("roomId") int roomId);
}
