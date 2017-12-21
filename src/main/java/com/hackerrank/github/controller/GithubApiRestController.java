package com.hackerrank.github.controller;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.model.Repo;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    private ActorRepository actorRepository;

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(
            method = RequestMethod.POST, 
            value = "/events",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<String> addEvents(@RequestBody String data){
        JSONObject input = new JSONObject(data);
        LOGGER.debug("Got the request as : "+input.toString());
        Long id = input.getLong(ID);
        String type = input.getString(TYPE);
        Timestamp created = Timestamp.valueOf(input.getString(CREATED_AT));
        JSONObject actorJson = input.getJSONObject(ACTOR);
        JSONObject repoJson = input.getJSONObject(REPO);

        Actor actor = new Actor();
        actor.setId(actorJson.getLong(ID));
        actor.setLogin(actorJson.getString(LOGIN));
        actor.setAvatar(actorJson.getString(AVATAR_URL));

        Repo repo = new Repo();
        repo.setId(repoJson.getLong(ID));
        repo.setName(repoJson.getString(NAME));
        repo.setUrl(repoJson.getString(URL));
        try{
            Event e = new Event(id, type, actor, repo, created);
            eventRepository.save(e);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/events/actors/{id}",
            produces = "application/json")
    public ResponseEntity<String> getEventsWithActor(@PathVariable(ID) long id){
        try{
            JSONArray jsonArray = new JSONArray();
            List<Event> events = eventRepository.getAllByActorId(id);
            if (events.size()==0){
                return new ResponseEntity<>("No Actors with given id", HttpStatus.NOT_FOUND);
            }
            for (Event event:events) {
                jsonArray.put(constructEventJson(event));
            }
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private JSONObject constructEventJson(Event event) {
        JSONObject object = new JSONObject();
        object.put(ID, event.getId());
        object.put(TYPE, event.getType());
        object.put(ACTOR, getActorJson(event.getActor()));
        object.put(REPO, getRepoJson(event.getRepo()));
        object.put(CREATED_AT, event.getCreatedAt());
        return object;
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/events",
            produces = "application/json")
    public ResponseEntity<String> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        JSONArray result = new JSONArray();
        for (Event event:events) {
            JSONObject obj = new JSONObject();
            obj.put(ID, event.getId());
            obj.put(TYPE, event.getType());
            obj.put(ACTOR, getActorJson(event.getActor()));
            obj.put(REPO, getRepoJson(event.getRepo()));
            obj.put(CREATED_AT, event.getCreatedAt());
            result.put(obj);
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/actors",
            produces = "application/json")
    public ResponseEntity<String> getAllActors(){
        List<Actor> actors = actorRepository.findAll();
        JSONArray result = new JSONArray();
        for (Actor actor:actors) {
            result.put(getActorJson(actor));
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    private JSONObject getActorJson(Actor actor) {
        JSONObject obj = new JSONObject();
        obj.put(ID, actor.getId());
        obj.put(LOGIN, actor.getLogin());
        obj.put(AVATAR_URL, actor.getAvatar());
        return obj;
    }

    private JSONObject getRepoJson(Repo repo) {
        JSONObject obj = new JSONObject();
        obj.put(ID, repo.getId());
        obj.put(NAME, repo.getName());
        obj.put(URL, repo.getUrl());
        return obj;
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/erase",
            produces = "application/json")
    public ResponseEntity<String> deleteAllEvents(){
        eventRepository.deleteAll();
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
