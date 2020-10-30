package com.company.cinema.repositories;

import com.company.cinema.entity.Session;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface SessionsRepository extends JpaRepository<Session, Long> {
    List<Session> findAllByDayId(Long id, Sort sort);

    List<Session> findAllByDayIdAndStartTime(long day_id, Date startTime);

    void removeById(Long id);

    List<Session> findAllByDayIdAndSeatsCounterLessThan(Long dayId, Integer seatsCounter, Sort sorting);

    List<Session> findAllByDayIdAndFilmId(Long dayId, Long filmId, Sort sorting);

    List<Session> findAllByDayIdAndFilmIdAndSeatsCounterLessThan(Long dayId, Long filmId, Integer seatsCounter, Sort sorting);

    @Modifying
    @Query(
            value = "insert into sessions (film_id, day_id, start_time) VALUES (:film_id, :day_id, :start_time)",
            nativeQuery = true)
    int insertSession(@Param("film_id") Long filmId, @Param("day_id") Long dayId, @Param("start_time") Date startTime);
}
