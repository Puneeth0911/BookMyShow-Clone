package com.alpha.theaterService.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.theaterService.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByShow_IdAndStatus(long showId, String status);

    List<Seat> findByShow_IdAndSeatnoIn(long showId, List<String> seatNumbers);
    
    List<Seat> findByShow_Id(long showId);

}