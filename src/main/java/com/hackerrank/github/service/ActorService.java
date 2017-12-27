package com.hackerrank.github.service;

import com.hackerrank.github.dto.ActorDTO;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.service.util.ModelConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService implements IActorService {
    @Autowired
    ActorRepository actorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ActorDTO> getAllActors(){
        List<Actor> actors = actorRepository.findAll();
        return actors.stream().map(ModelConvertorUtil::convert).collect(Collectors.toList());
    }
}
