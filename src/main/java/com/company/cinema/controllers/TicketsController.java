package com.company.cinema.controllers;

import com.company.cinema.entity.Seat;
import com.company.cinema.entity.Session;
import com.company.cinema.entity.User;
import com.company.cinema.services.SeatsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tickets/{filmSessionId}")
public class TicketsController {

    private final SeatsService seatsService;
    static final Logger logger = Logger.getLogger(TicketsController.class);
    private int rowsQuantity;
    private int seatsInARow;

    @Autowired
    public TicketsController(SeatsService seatsService) {
        this.seatsService = seatsService;
    }

    @PostConstruct
    public void init(){
        Properties cinemaProperties = new Properties();
        try (FileInputStream propertyFile = new FileInputStream(Paths.get("src", "main", "resources", "cinema.properties").toString())){
            cinemaProperties.load(propertyFile);
            rowsQuantity = Integer.parseInt(cinemaProperties.get("cinema.hall.rows.quantity").toString());
            seatsInARow = Integer.parseInt(cinemaProperties.get("cinema.hall.rows.seats.quantity").toString());
            if(rowsQuantity == 0 || seatsInARow == 0) throw new IOException();
        } catch (IOException e){
            logger.error(e);
            throw new RuntimeException();
        }
    }

    @GetMapping
    public String ticketPage(Model model,
                             @AuthenticationPrincipal User user,
                             @PathVariable(name = "filmSessionId") Session filmSession){

        if (filmSession == null){
            return "";
        }

        model.addAttribute("loggedIn", true);
        model.addAttribute("admin", user.isAdmin());
        model.addAttribute("rowsQuantity", rowsQuantity);
        model.addAttribute("seatsInARow", seatsInARow);
        model.addAttribute("filmSession", filmSession);

        List<Integer> occupiedSeats = filmSession.getOccupiedSeats().stream().map(Seat::getNumber).collect(Collectors.toList());
        model.addAttribute("bookedSeats", occupiedSeats);
        return "tickets";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public String buyTickets(@AuthenticationPrincipal User user,
                             @RequestParam("filmSessionId") Session session,
                             @RequestParam("basket[]") List<Integer> seats){
        seatsService.insertSeats(user, session, seats);
        return "redirect:/tickets/{filmSessionId}";
    }

}
