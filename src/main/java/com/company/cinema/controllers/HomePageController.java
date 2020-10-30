package com.company.cinema.controllers;

import com.company.cinema.entity.Session;
import com.company.cinema.entity.User;
import com.company.cinema.services.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.company.cinema.controllers.Sorting.getSorting;

@Controller
public class HomePageController {

    private final SessionsService sessionsService;

    @Autowired
    public HomePageController(SessionsService sessionsService) {
        this.sessionsService = sessionsService;
    }

    @GetMapping("/home")
    public String mainPage(Model model,
                           @AuthenticationPrincipal User user,
                           @RequestParam(required = false) String sorting,
                           @RequestParam(required = false) Long day,
                           @RequestParam(required = false) String filter,
                           @RequestParam(required = false) Long film){

        boolean loggedIn = user != null;
        boolean admin = false;

        if (loggedIn){
            admin = user.isAdmin();

            model.addAttribute("login", user.getUsername());
            model.addAttribute("admin", admin);
            model.addAttribute("sortingOptions", Sorting.values());
        } else {
            sorting = null;
        }

        int todayId = Days.getTodayWeekDayId();
        model.addAttribute("todayId", todayId);

        day = (day == null) ? todayId : day;

        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("days", Days.values());

        filter = (
                (filter == null || filter.equals("true"))
                && loggedIn
                && !admin)
                ? "true"
                : "false";

        List<Session> sessions = sessionsService.findAllByDayIdAndFilmIdWithSortingAndFiltered(day, film, getSorting(sorting), filter);
        model.addAttribute("sessions", listToGrid(sessions, 4));

        return "main_page";
    }


    public static <T> List<List<T>> listToGrid(List<T> list, int columns){
        List<T> row = new ArrayList<>(columns);
        List<List<T>> result = new LinkedList<>();

        for (int i = 0; i < list.size(); i++){
            row.add(list.get(i));
            if ((i+1) % columns == 0){
                result.add(row);
                row = new ArrayList<>(columns);
            }
        }

        if (0 < row.size() && row.size() < columns){
            result.add(row);
        }
        return result;
    }


}
