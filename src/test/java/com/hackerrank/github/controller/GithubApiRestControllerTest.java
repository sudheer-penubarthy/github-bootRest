package com.hackerrank.github.controller;

import com.hackerrank.github.dto.ActorDTO;
import com.hackerrank.github.dto.EventDTO;
import com.hackerrank.github.dto.RepoDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GithubApiRestController.class, secure = false)
public class GithubApiRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GithubApiRestController githubApiRestController;

    private ActorDTO actor1, actor2, actor3;
    private RepoDTO repo1, repo2, repo3, repo4, repo5;
    private EventDTO event1, event2, event3, event4, event5;
    private Timestamp timestamp1, timestamp2;
    private JSONObject body = new JSONObject();

    @Before
    public void init() throws ParseException, JSONException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = dateFormat.parse("2013-01-01 01:13:31");
        timestamp1 = new Timestamp(parsedDate.getTime());
        parsedDate = dateFormat.parse("2013-01-02 17:13:31");
        timestamp2 = new Timestamp(parsedDate.getTime());

        actor1 = new ActorDTO(3661723L, "sw", "https://avatars.githubusercontent.com/u/3661723");
        actor2 = new ActorDTO(2781173L, "rcra", "https://avatars.githubusercontent.com/u/2781173");
        actor3 = new ActorDTO(4603643L, "tammyva", "https://avatars.githubusercontent.com/u/4603643");

        repo1 = new RepoDTO(418002L, "sw/quia-ex", "https://api.github.com/repos/sw/quia-ex");
        repo2 = new RepoDTO(373905L, "rcra/culpa-vel", "https://api.github.com/repos/rcra/culpa-vel");
        repo3 = new RepoDTO(254722L, "rcra/laborum-r", "https://api.github.com/repos/rcra/laborum-r");
        repo4 = new RepoDTO(224901L, "ayers/lauda", "https://api.github.com/repos/ayers/lauda");
        repo5 = new RepoDTO(346385L, "tammyva/similique", "https://api.github.com/repos/tammyva/similique");

        event1 = new EventDTO(1000547873L, "PushEvent", actor1, repo1, timestamp1);
        event2 = new EventDTO(1001192108L, "PushEvent", actor2, repo2, timestamp1);
        event3 = new EventDTO(1001582846L, "PushEvent", actor2, repo3, timestamp1);
        event4 = new EventDTO(1005058095L, "PushEvent", actor1, repo4, timestamp2);
        event5 = new EventDTO(1005073723L, "PushEvent", actor3, repo5, timestamp2);

        body = new JSONObject("{\n" +
                "'id':1000547873,\n" +
                "'type':'PushEvent',\n" +
                "'actor':{\n" +
                "    'id':3661723,\n" +
                "    'login':'sw',\n" +
                "    'avatar_url':'https://avatars.githubusercontent.com/u/3661723'\n" +
                "  },\n" +
                "'repo':{\n" +
                "    'id':418002,\n" +
                "    'name':'sw/quia-ex',\n" +
                "    'url':'https://api.github.com/repos/sw/quia-ex'\n" +
                "  },\n" +
                "'created_at':'2013-01-01 01:13:31'\n" +
                "}");
    }

    @Test
    public void addEvents() throws Exception {
        mvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getEventsWithActor() throws Exception {
        List<EventDTO> eventList = new ArrayList<>(Arrays.asList(event2, event3));
        given(githubApiRestController.getEventsWithActor(2781173L)).willReturn(eventList);

        mvc.perform(get("/events/actors/2781173")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(event2.getId().intValue())))
                .andExpect(jsonPath("$[0].actor.login", is(event2.getActor().getLogin())))
                .andExpect(jsonPath("$[0].actor.avatar_url", is(event2.getActor().getAvatar_url())))
                .andExpect(jsonPath("$[0].actor.id", is(event2.getActor().getId().intValue())))
                .andExpect(jsonPath("$[0].repo.id", is(event2.getRepo().getId().intValue())))
                .andExpect(jsonPath("$[0].repo.name", is(event2.getRepo().getName())))
                .andExpect(jsonPath("$[0].repo.url", is(event2.getRepo().getUrl())))
                //.andExpect(jsonPath("$[0].created_at", is(event1.getCreated_at())))
                .andExpect(jsonPath("$[1].type", is(event3.getType())))
                .andExpect(jsonPath("$[1].id", is(event3.getId().intValue())))
                .andExpect(jsonPath("$[1].actor.login", is(event3.getActor().getLogin())))
                .andExpect(jsonPath("$[1].actor.avatar_url", is(event3.getActor().getAvatar_url())))
                .andExpect(jsonPath("$[1].actor.id", is(event3.getActor().getId().intValue())))
                .andExpect(jsonPath("$[1].repo.id", is(event3.getRepo().getId().intValue())))
                .andExpect(jsonPath("$[1].repo.name", is(event3.getRepo().getName())))
                .andExpect(jsonPath("$[1].repo.url", is(event3.getRepo().getUrl())))
                //.andExpect(jsonPath("$[0].created_at", is(event1.getCreated_at())))
                .andExpect(jsonPath("$[1].type", is(event3.getType())));
    }

    @Test
    public void getEventsWithMissingActor() throws Exception {
        List<EventDTO> eventList = new ArrayList<>();
        given(githubApiRestController.getEventsWithActor(4019960)).willReturn(eventList);

        mvc.perform(get("/events/actors/4019960")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllEvents() throws Exception {
        //List<EventDTO> eventList = singletonList(event1);
        List<EventDTO> eventList = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5));
        given(githubApiRestController.getAllEvents()).willReturn(eventList);

        mvc.perform(get("/events")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(event1.getId().intValue())))
                .andExpect(jsonPath("$[0].actor.login", is(event1.getActor().getLogin())))
                .andExpect(jsonPath("$[0].actor.avatar_url", is(event1.getActor().getAvatar_url())))
                .andExpect(jsonPath("$[0].actor.id", is(event1.getActor().getId().intValue())))
                .andExpect(jsonPath("$[0].repo.id", is(event1.getRepo().getId().intValue())))
                .andExpect(jsonPath("$[0].repo.name", is(event1.getRepo().getName())))
                .andExpect(jsonPath("$[0].repo.url", is(event1.getRepo().getUrl())))
                //.andExpect(jsonPath("$[0].created_at", is(event1.getCreated_at())))
                .andExpect(jsonPath("$[0].type", is(event1.getType())));

    }

    @Test
    public void getAllActors() throws Exception {
        List<ActorDTO> actorList = new ArrayList<>(Arrays.asList(actor1, actor2, actor3));
        given(githubApiRestController.getAllActors()).willReturn(actorList);

        mvc.perform(get("/actors")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(actor1.getId().intValue())))
                .andExpect(jsonPath("$[0].login", is(actor1.getLogin())))
                .andExpect(jsonPath("$[0].avatar_url", is(actor1.getAvatar_url())))
                .andExpect(jsonPath("$[1].id", is(actor2.getId().intValue())))
                .andExpect(jsonPath("$[1].login", is(actor2.getLogin())))
                .andExpect(jsonPath("$[1].avatar_url", is(actor2.getAvatar_url())))
                .andExpect(jsonPath("$[2].id", is(actor3.getId().intValue())))
                .andExpect(jsonPath("$[2].login", is(actor3.getLogin())))
                .andExpect(jsonPath("$[2].avatar_url", is(actor3.getAvatar_url())));
    }

    /*@Test
    public void deleteAllEvents() {
    }*/
}