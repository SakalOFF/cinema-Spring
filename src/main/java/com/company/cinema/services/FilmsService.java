package com.company.cinema.services;

import com.company.cinema.entity.Film;
import com.company.cinema.repositories.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmsService {

    private final FilmsRepository filmsRepository;

    @Autowired
    public FilmsService(FilmsRepository filmsRepository) {
        this.filmsRepository = filmsRepository;
    }

    public List<Film> getAllFilms(){
        return new ArrayList<>(filmsRepository.findAll());
    }

}
