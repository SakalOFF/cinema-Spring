package com.company.cinema.repositories;

import com.company.cinema.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmsRepository extends JpaRepository<Film, Long> {
}
