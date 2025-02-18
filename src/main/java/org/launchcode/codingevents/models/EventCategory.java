package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class EventCategory<Events> extends AbstractEntity{

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 50, message = "Must be between 3-50 characters")
    private String name;

    @OneToMany(mappedBy = "eventCategory")
    private final List<Event> events = new ArrayList<>();

    public EventCategory(String name) {
        this.name = name;
    }

    public EventCategory() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }
}
