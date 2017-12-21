package com.hackerrank.github.repository;

import com.hackerrank.github.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRepository extends JpaRepository<Repo, Long> {
}
