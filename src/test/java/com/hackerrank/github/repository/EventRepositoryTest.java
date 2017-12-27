package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.model.Repo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    private Actor actor;
    private Repo repo1, repo2;

    @Before
    public void init(){
        actor = new Actor(100l, "rcra", "https://avatars.githubusercontent.com/u/100");
        repo1 = new Repo(100l, "rcra/sample-r", "https://api.github.com/repos/rcra/sample");
        repo2 = new Repo(101l, "rcra/sample2-r", "https://api.github.com/repos/rcra/sample2");
    }

    @Test
    public void getAllEventsByActorId() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        Event e = new Event(100l, "PushEvent", actor, repo1, t);
        entityManager.persist(e);
        entityManager.flush();

        List<Event> expected = new ArrayList<>();
        expected.add(e);

        List<Event> eventList = eventRepository.getAllByActorId(e.getId());

        assertArrayEquals(eventList.toArray(), expected.toArray());
    }


}