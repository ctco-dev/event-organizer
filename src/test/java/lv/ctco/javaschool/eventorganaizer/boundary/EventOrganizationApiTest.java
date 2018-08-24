package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventOrganizationApiTest {


    @Mock
    EntityManager em;
    @Mock
    UserStore userStore;
    @Mock
    EventStore eventStore;
    @InjectMocks
    EventOrganizationApi eventOrganizationApi;

    List<Event> events = new ArrayList<>();
    Event event = new Event();
    TopicDto topicDto;
    TopicListDto topicListDto;
    List<TopicDto> td = new ArrayList<>();


    @Test
    void someTest() {
        when(eventStore.getAllEvents())
                .thenReturn(events);

        assertIterableEquals(td, eventOrganizationApi.getAllOpenEvents().getTopicList());
    }

}