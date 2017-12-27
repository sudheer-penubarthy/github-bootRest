package com.hackerrank.github.service;

import com.hackerrank.github.dto.EventDTO;

import java.util.List;

public interface IEventService {
    List<EventDTO> getAllEvents();

    void deleteAll();

    List<EventDTO> getAllByActorId(long id);

    void save(EventDTO eventDTO);
}
