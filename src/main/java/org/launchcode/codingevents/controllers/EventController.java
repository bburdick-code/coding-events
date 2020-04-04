package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    // findAll(), save(), findById()

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model) {

        if(categoryId == null) {
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("title", "All Events"); }
        else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isPresent()) {
                EventCategory category = result.get();
                model.addAttribute("events", category.getEvents());
                model.addAttribute("title", "Events in category: " + category.getName());
            } else {
                model.addAttribute("events", eventRepository.findAll());
                model.addAttribute("title", "Invalid ID");
                return "events/index";
            }
        }
        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model)  {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad data!");
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String renderDeleteForm(Model model) {
        model.addAttribute("title","Delete Event");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {
        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{eventId}")
    public String renderEditForm(Model model, @PathVariable int eventId) {
        // code
        Optional<Event> basic = eventRepository.findById(eventId);
        Event current = basic.get();
        model.addAttribute("title", "Edit Event " + current.getName() + " (id=" + current.getId() + ")");
        model.addAttribute("event", basic.get());
        return "events/edit";
    }

    @PostMapping("edit")
    public String editEvent(@RequestParam int eventId, @RequestParam String name, @RequestParam String description, @RequestParam String contactEmail) {
        //code
        Optional<Event> basic = eventRepository.findById(eventId);
        Event current = basic.get();
        current.setName(name);
        return "redirect:";
    }
}