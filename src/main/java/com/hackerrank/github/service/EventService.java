package com.hackerrank.github.service;

import com.hackerrank.github.dto.EventDTO;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.service.util.ModelConvertorUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService{

    private static final Logger LOGGER = Logger.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;


    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        return events.stream().map(ModelConvertorUtil::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAll() {
        eventRepository.deleteAll();
    }

    @Override
    public List<EventDTO> getAllByActorId(long id) {
        List<Event> events = eventRepository.getAllByActorId(id);
        return events.stream().map(ModelConvertorUtil::convert).collect(Collectors.toList());
    }

    @Override
    public void save(EventDTO eventDTO) {
        LOGGER.debug("Got the data as : "+eventDTO);
        System.out.println("Got the data as : "+eventDTO);
        eventRepository.save(ModelConvertorUtil.convert(eventDTO));
    }
}
