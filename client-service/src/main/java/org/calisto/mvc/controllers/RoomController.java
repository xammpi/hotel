package org.calisto.mvc.controllers;


import org.calisto.mvc.models.Room;
import org.calisto.mvc.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String showAllRooms(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Room> pages = roomService.findRoomsUsingPagination(page);
        model.addAttribute("rooms", pages);
        return "room/show-rooms";
    }

    @GetMapping("/new")
    public String newRoom(@ModelAttribute("room") Room room) {
        return "room/new-room";
    }

    @PostMapping
    public String createRoom(@Validated Room room, BindingResult result) {
        if (result.hasErrors()) {
            return "room/new-room";
        }
        roomService.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}")
    public String showRoom(@PathVariable("id") int id, Model model) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "room/edit-room";
    }

    @PostMapping("/{id}")
    public String editRoom(@Validated @ModelAttribute("room") Room room, BindingResult result,
                           @PathVariable("id") int id) {
        if (result.hasErrors()) {
            return "room/edit-room";
        }
        roomService.update(id, room);
        return "redirect:/rooms";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable("id") int id) {
        roomService.deleteById(id);
        return "redirect:/rooms";
    }
}
