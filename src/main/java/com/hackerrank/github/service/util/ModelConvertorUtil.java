package com.hackerrank.github.service.util;

import com.hackerrank.github.dto.ActorDTO;
import com.hackerrank.github.dto.EventDTO;
import com.hackerrank.github.dto.RepoDTO;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.model.Repo;

public class ModelConvertorUtil {
    public static ActorDTO convert(Actor actor){
        return new ActorDTO(actor.getId(), actor.getLogin(), actor.getAvatar());
    }

    public static RepoDTO convert(Repo repo){
        return new RepoDTO(repo.getId(), repo.getName(), repo.getUrl());
    }

    public static EventDTO convert(Event event){
        return new EventDTO(event.getId(), event.getType(), convert(event.getActor()), convert(event.getRepo()), event.getCreatedAt());
    }

    public static Event convert(EventDTO dto) {
        return new Event(dto.getId(), dto.getType(), convert(dto.getActor()), convert(dto.getRepo()), dto.getCreated_at());
    }

    public static Actor convert(ActorDTO actor){
        return new Actor(actor.getId(), actor.getLogin(), actor.getAvatar_url());
    }

    public static Repo convert(RepoDTO repo){
        return new Repo(repo.getId(), repo.getName(), repo.getUrl());
    }
}
