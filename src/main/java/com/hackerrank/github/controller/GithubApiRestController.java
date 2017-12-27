package com.hackerrank.github.controller;

import com.hackerrank.github.dto.ActorDTO;
import com.hackerrank.github.dto.EventDTO;
import com.hackerrank.github.service.IActorService;
import com.hackerrank.github.service.IEventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GithubApiRestController {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String TYPE = "type";
    public static final String CREATED_AT = "created_at";
    public static final String ACTOR = "actor";
    public static final String REPO = "repo";
    public static final String LOGIN = "login";
    public static final String AVATAR_URL = "avatar_url";

    private static final Logger LOGGER = Logger.getLogger(GithubApiRestController.class);

    @Autowired
    private IEventService eventService;

    @Autowired
    private IActorService actorService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/events")
    public ResponseEntity<String> addEvents(@RequestBody EventDTO eventDTO) {
        System.out.println("In the controller got data as : "+eventDTO);
        eventService.save(eventDTO);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/events/actors/{id}")
    public List<EventDTO> getEventsWithActor(@PathVariable(ID) long id) {
        return eventService.getAllByActorId(id);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/events")
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/actors")
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/erase",
            produces = "application/json")
    public ResponseEntity<String> deleteAllEvents() {
        eventService.deleteAll();
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
