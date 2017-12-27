package com.hackerrank.github.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class EventDTO {
    private Long id;
    private String type;
    private ActorDTO actor;
    private RepoDTO repo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp created_at;

    public EventDTO() {
    }

    public EventDTO(Long id, String type, ActorDTO actor, RepoDTO repo, Timestamp createdAt) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repo = repo;
        this.created_at = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ActorDTO getActor() {
        return actor;
    }

    public RepoDTO getRepo() {
        return repo;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", actor=" + actor +
                ", repo=" + repo +
                ", createdAt=" + created_at +
                '}';
    }
}
