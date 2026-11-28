
package com.alpha.theaterService.repository;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.theaterService.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

	boolean existsByTheater_IdAndDateAndTimeAndScreennumber(long theaterId, Date date, LocalTime time,
			int screennumber);

	Optional<Show> findByDateAndTimeAndScreennumberAndTheater_Id(Date date, LocalTime time, int screennumber,
			long theaterId);

	List<Show> findByTheater_IdAndDate(long theaterid, Date date);

	List<Show> findByTitleContainingIgnoreCaseAndLanguageIgnoreCaseAndDate(String title, String language, Date date);

	List<Show> findByTheater_IdAndTitleContainingIgnoreCase(long theaterid, String title);

	List<Show> findByTheater_IdAndScreennumber(long theaterid, int screennumber);
}
