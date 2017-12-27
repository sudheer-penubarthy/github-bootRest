package com.hackerrank.github.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "t_event")
public class Event implements Serializable {
    @Id
    private Long id;
    private String type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACTOR_ID")
    private Actor actor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REPO_ID")
    private Repo repo;
    private Timestamp createdAt;

    public Event() {
    }

    public Event(Long id, String type, Actor actor, Repo repo, Timestamp createdAt) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repo = repo;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Actor getActor() {
        return actor;
    }

    public Repo getRepo() {
        return repo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
