package com.company.cinema.controllers;

import com.company.cinema.entity.Session;
import com.company.cinema.services.FilmsService;
import com.company.cinema.services.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TemporalType;

import java.util.Date;
import java.util.List;

import static com.company.cinema.controllers.HomePageController.listToGrid;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/redactor")
public class RedactorController {

    private final SessionsService sessionsService;
    private final FilmsService filmsService;

    @Autowired
    public RedactorController(SessionsService sessionsService, FilmsService filmsService) {
        this.sessionsService = sessionsService;
        this.filmsService = filmsService;
    }

    @GetMapping
    public String redactorForm(Model model, @RequestParam(required = false) Long day){

        int todayId = Days.getTodayWeekDayId();
        model.addAttribute("todayId", todayId);
        model.addAttribute("loggedIn", true);
        model.addAttribute("admin", true);

        day = (day == null) ? todayId : day;

        List<Session> sessions = sessionsService.findAllByDayIdWithSorting(day, Sorting.getSorting(""));
        sessions.add(null);
        model.addAttribute("sessions", listToGrid(sessions, 4));
        model.addAttribute("days", Days.values());
        return "redactor";
    }

    @GetMapping("/sessions")
    public String sessionForm(Model model, @RequestParam(required = false) Boolean error){
        model.addAttribute("loggedIn", true);
        model.addAttribute("admin", true);

        if(error != null){
            model.addAttribute("showMessage", true);
        }

        model.addAttribute("days", Days.values());
        model.addAttribute("films", filmsService.getAllFilms());
        return "session_add_form";
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<String> deleteSession(@RequestParam Long sessionId){
        sessionsService.removeSession(sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/sessions")
    public String putSession(@RequestParam Long filmId,
                             @RequestParam Long dayId,
                             @Temporal(TemporalType.TIME) @DateTimeFormat(pattern = "HH:mm") @RequestParam Date startTime){

        if (sessionsService.addSession(filmId, dayId, startTime) != 0){
            return "redirect:/redactor?day=" + dayId;
        }
        return "redirect:/redactor/sessions?error=true";
    }

}
