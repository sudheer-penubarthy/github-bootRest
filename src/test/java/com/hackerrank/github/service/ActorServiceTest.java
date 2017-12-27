package com.hackerrank.github.service;

import com.hackerrank.github.dto.ActorDTO;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.repository.ActorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ActorServiceTest {


    @Configuration
    static class ActorServiceTestConfiguration {
        @Bean
        public ActorService actorService() {
            return new ActorService();
        }
        @Bean
        public ActorRepository actorRepository() {
            return Mockito.mock(ActorRepository.class);
        }
    }
    @Autowired
    private ActorService actorService;
    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void getAllActors() {
        Actor actor1 = new Actor(3661723L, "sw", "https://avatars.githubusercontent.com/u/3661723");
        Actor actor2 = new Actor(2781173L, "rcra", "https://avatars.githubusercontent.com/u/2781173");
        Actor actor3 = new Actor(4603643L, "tammyva", "https://avatars.githubusercontent.com/u/4603643");

        List<Actor> actors = new ArrayList<>();
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);

        Mockito.when(actorRepository.findAll()).thenReturn(actors);
        List<ActorDTO> actorsFound = actorService.getAllActors();
        assertEquals(actorsFound.get(0).getId(), actor1.getId());
        assertEquals(actorsFound.get(1).getId(), actor2.getId());
        assertEquals(actorsFound.get(2).getId(), actor3.getId());

    }
}