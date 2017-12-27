package com.hackerrank.github.dto;

import java.io.Serializable;

public class RepoDTO implements Serializable {

    private Long id;
    private String name;
    private String url;

    public RepoDTO() {
    }

    public RepoDTO(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RepoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
