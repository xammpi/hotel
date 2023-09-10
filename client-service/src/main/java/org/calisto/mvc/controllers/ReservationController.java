package org.calisto.mvc.controllers;


import org.calisto.mvc.models.Reservation;
import org.calisto.mvc.services.GuestService;
import org.calisto.mvc.services.ReservationService;
import org.calisto.mvc.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final GuestService guestService;
    private final RoomService roomService;

    @Autowired
    public ReservationController(ReservationService reservationService, GuestService guestService,
                                 RoomService roomService) {
        this.reservationService = reservationService;
        this.guestService = guestService;
        this.roomService = roomService;
    }

    @GetMapping
    public String showAllReservations(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Reservation> pages = reservationService.findReservationUsingPagination(page);
        model.addAttribute("reservations", pages);
        return "reservation/show-reservations";
    }

    @GetMapping("/new")
    public String newReservation(@ModelAttribute("reservation") Reservation reservation, Model model) {
        model.addAttribute("guests", guestService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "reservation/new-reservation";
    }

    @PostMapping
    public String createReservation(@ModelAttribute("reservation") Reservation reservation) {
//        if (result.hasErrors()) {
//            return "reservation/new-reservation";
//        }
        try {
            reservationService.save(reservation);
            return "redirect:/reservations";
        } catch (RuntimeException e) {
//            model.addAttribute("errorMessage",
//                    "The room is occupied for the specified dates. Please choose different dates.");
//            model.addAttribute("guests", guestService.findAll());
//            model.addAttribute("rooms", roomService.findAll());
            return "reservation/new-reservation";
        }
    }

    @GetMapping("/{id}")
    public String showReservation(@PathVariable("id") int id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "reservation/edit-reservation";
    }

    @PostMapping("/{id}")
    public String editReservation(@Validated @ModelAttribute("reservation") Reservation reservation,
                                  BindingResult result, @PathVariable("id") int id, Model model) {
        if (result.hasErrors()) {
            return "reservation/edit-reservation";
        }
        try {
            reservationService.update(id, reservation);
            return "redirect:/reservations";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage",
                    "The room is occupied for the specified dates. Please choose different dates.");
            model.addAttribute("guests", guestService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            return "reservation/edit-reservation";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteReservation(@PathVariable("id") int id) {
        reservationService.deleteById(id);
        return "redirect:/reservations";
    }
}
