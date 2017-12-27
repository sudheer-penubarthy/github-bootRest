package com.hackerrank.github.service;

import com.hackerrank.github.dto.ActorDTO;

import java.util.List;

public interface IActorService {
    public List<ActorDTO> getAllActors();
}
