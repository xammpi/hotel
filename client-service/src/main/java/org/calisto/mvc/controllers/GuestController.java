package org.calisto.mvc.controllers;


import org.calisto.mvc.models.Guest;
import org.calisto.mvc.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }


    @GetMapping
    public String showAllGuests(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Guest> pages = guestService.findGuestsUsingPagination(page);
        model.addAttribute("guests", pages);
        return "guest/show-guests";
    }

    @GetMapping("/new")
    public String newGuest(@ModelAttribute("guest") Guest guest) {
        return "guest/new-guest";
    }

    @PostMapping
    public String createGuest(@Validated @ModelAttribute("guest") Guest guest, BindingResult result) {
        if (result.hasErrors()) {
            return "guest/new-guest";
        }
        guestService.save(guest);
        return "redirect:/guests";
    }

    @GetMapping("/{id}")
    public String showGuest(@PathVariable("id") int id, Model model) {
        Guest guest = guestService.findById(id);
        model.addAttribute("guest", guest);
        return "guest/edit-guest";
    }

    @PostMapping("/{id}")
    public String editGuest(@Validated @ModelAttribute("guest") Guest guest, BindingResult result,
                            @PathVariable("id") int id) {
        if (result.hasErrors()) {
            return "guest/edit-guest";
        }
        guestService.update(id, guest);
        return "redirect:/guests";
    }

    @DeleteMapping("/{id}")
    public String deleteGuest(@PathVariable("id") int id) {
        guestService.deleteById(id);
        return "redirect:/guests";
    }
}
