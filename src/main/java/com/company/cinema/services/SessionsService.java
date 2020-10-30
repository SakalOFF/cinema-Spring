package com.company.cinema.services;

import com.company.cinema.entity.Session;
import com.company.cinema.repositories.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SessionsService {

    private final SessionsRepository sessionsRepository;

    @Autowired
    public SessionsService(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    public List<Session> findAllByDayIdAndFilmIdWithSortingAndFiltered(Long dayId, Long filmId, Sort sorting, String filtered){
        if (filmId == null){
            return findAllByDayIdWithSortingAndFiltered(dayId, sorting, filtered);
        }
        if ("true".equals(filtered)){
            return sessionsRepository.findAllByDayIdAndFilmIdAndSeatsCounterLessThan(dayId, filmId, 100, sorting);
        }
        return sessionsRepository.findAllByDayIdAndFilmId(dayId, filmId, sorting);
    }

    @Transactional
    public void removeSession(Long id){
        sessionsRepository.removeById(id);
    }

    @Transactional
    public int addSession(Long filmId, Long dayId, Date startTime) {
        if (sessionsRepository.findAllByDayIdAndStartTime(dayId, startTime).size() == 0) {
            return sessionsRepository.insertSession(filmId, dayId, startTime);
        }
        return 0;
    }

    public List<Session> findAllByDayIdWithSorting(Long id, Sort sorting) {
        return sessionsRepository.findAllByDayId(id, sorting);
    }

    public List<Session> findAllByDayIdWithSortingAndFiltered(Long id, Sort sorting, String filtered) {
        if ("true".equals(filtered)){
            return sessionsRepository.findAllByDayIdAndSeatsCounterLessThan(id, 100, sorting);
        }
        return findAllByDayIdWithSorting(id, sorting);
    }
}
