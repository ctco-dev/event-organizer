package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    User u1 = new User();
    List<Event> events = new ArrayList<>();
    Event event = new Event();
    TopicDto topicDto;
    List<TopicDto> td = new ArrayList<>();

    @BeforeEach
    void init() {
        u1.setUsername("admin");
        event.setId((long) 1);
        event.setName("qwe");
        event.setDescription("asdf");
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(u1);
        topicDto = new TopicDto(event);
        td.add(topicDto);
        events.add(event);
    }

    @Test
    void someTest() {
        when(eventStore.getAllEvents())
                .thenReturn(events);
        TopicListDto td2 = new TopicListDto(eventOrganizationApi.getAllOpenEvents().getTopicList());
        td.add(topicDto);
        assertEquals(td.get(0).getId(), td2.getTopicList().get(0).getId());
        assertEquals(td.get(0).getTopicName(), td2.getTopicList().get(0).getTopicName());
        assertEquals(td.get(0).getTopicAuthor(), td2.getTopicList().get(0).getTopicAuthor());
        assertEquals(td.get(0).getDate(), td2.getTopicList().get(0).getDate());
    }


    //NON MOCKITO TESTS

    @Test
    void setFieldsToEventSetsUpName() {
        EventOrganizationApi eventOrganizationApi = new EventOrganizationApi();
        String adrr = "name";
        String value = "name";
        Event e = new Event();

        assertEquals(value, eventOrganizationApi.setFieldsToEvent(e, adrr, value).getName());
    }

    @Test
    void setFieldsToEventWrongAddressReturnsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventOrganizationApi eventOrganizationApi = new EventOrganizationApi();
            String adrr = "date";
            String value = "datepicker";
            Event e = new Event();
            eventOrganizationApi.setFieldsToEvent(e, adrr, value);
        });
    }

    @Test
    void setFieldsToEventNullAddressThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            EventOrganizationApi eventOrganizationApi = new EventOrganizationApi();
            String adrr = null;
            String value = "datepicker";
            Event e = new Event();
            eventOrganizationApi.setFieldsToEvent(e, adrr, value);
        });
    }

}