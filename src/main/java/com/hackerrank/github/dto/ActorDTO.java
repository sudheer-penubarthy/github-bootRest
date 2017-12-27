package com.hackerrank.github.dto;

import java.io.Serializable;

public class ActorDTO implements Serializable{
    private Long id;
    private String login;
    private String avatar_url;

    public ActorDTO() {
    }

    public ActorDTO(Long id, String login, String avatar) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public String toString() {
        return "ActorDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
