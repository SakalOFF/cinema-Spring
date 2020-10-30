package com.company.cinema.services;

import com.company.cinema.entity.Seat;
import com.company.cinema.entity.Session;
import com.company.cinema.entity.User;
import com.company.cinema.repositories.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatsService {

    private final SessionsRepository sessionsRepository;

    @Autowired
    public SeatsService(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @Transactional
    public void insertSeats(User user, Session session, List<Integer> numbers) {
        Seat seat;
        for(Integer n: numbers){
            seat = new Seat();
            seat.setNumber(n);
            seat.setSession(session);
            seat.setUser(user);
            session.getOccupiedSeats().add(seat);
        }
        session.setSeatsCounter(session.getSeatsCounter() + numbers.size());

        sessionsRepository.save(session);
    }
}
