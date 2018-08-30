package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventOrganizationApiTest {
    @Mock
    private EntityManager em;
    @Mock
    private UserStore userStore;
    @Mock
    private EventStore eventStore;
    @InjectMocks
    private EventOrganizationApi eventOrganizationApi;

    private User testUser;
    private List<Event> userEvents;
    private Event event;
    private TopicDto expectedTopicDto;
    private List<TopicDto> topicDtoList;
    private EventDto eventDto;

    @BeforeEach
    void init() {
        userEvents = new ArrayList<>();
        testUser = new User();
        testUser.setUsername("admin");
        event = initEvent();
        eventDto = initEventDto(event);
        topicDtoList = new ArrayList<>();
        expectedTopicDto = new TopicDto(event);
    }

    @Test
    void testReturnsCorrectDTO() {
        userEvents.add(event);
        when(eventStore.getAllEvents())
                .thenReturn(userEvents);

        TopicListDto result = eventOrganizationApi.getAllOpenEvents();
        List<TopicDto> topicList = result.getTopicList();
        assertEquals(1, topicList.size());

        TopicDto resultTopicDto = topicList.get(0);
        assertEquals(expectedTopicDto.getId(), resultTopicDto.getId());
        assertEquals(expectedTopicDto.getTopicName(), resultTopicDto.getTopicName());
        assertEquals(expectedTopicDto.getTopicAuthor(), resultTopicDto.getTopicAuthor());
        assertEquals(expectedTopicDto.getDate(), resultTopicDto.getDate());
    }

    @Test
    void testReturnEmptyDto() {
        when(eventStore.getAllEvents())
                .thenReturn(userEvents);
        TopicListDto td2 = new TopicListDto(eventOrganizationApi.getAllOpenEvents().getTopicList());
        assertEquals(topicDtoList.size(), td2.getTopicList().size());
    }

    @Test
    void testFindById() {
        when(eventStore.getEventById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(event));
        assertEquals(eventDto.getEventID(), eventOrganizationApi.getEventById((long) 1).getEventID());
    }

    @Test
    void testValidationThrowsException() {
        when(eventStore.getEventById((long) 1))
                .thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> eventOrganizationApi.getEventById((long) 1));
    }

    @Test
    void testNullThrowsException() {
        assertThrows(EntityNotFoundException.class, () -> eventOrganizationApi.getEventById(null));
    }

    @Test
    void testGetAllAuthorsEvents() {
        userEvents.add(event);
        when(userStore.getCurrentUser())
                .thenReturn(testUser);
        when(eventStore.getAuthorEvents(testUser))
                .thenReturn(userEvents);
        List<EventDto> result = eventOrganizationApi.getAllAuthorEvents();
        assertEquals(1, result.size());

        EventDto eventDto = result.get(0);
        assertEquals("qwe", eventDto.getEventName());
        assertEquals(1, eventDto.getEventID());
    }

    @Test
    void testReturnEmtyList() {
        when(userStore.getCurrentUser())
                .thenReturn(testUser);
        when(eventStore.getAuthorEvents(testUser))
                .thenReturn(userEvents);

        List<EventDto> result = eventOrganizationApi.getAllAuthorEvents();
        assertTrue(result.isEmpty());
    }

    private Event initEvent() {
        Event event = new Event();
        event.setId((long) 1);
        event.setName("qwe");
        event.setDescription("asdf");
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(testUser);
        return event;
    }

    private EventDto initEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setEventName(event.getName());
        eventDto.setEventDate(event.getDate());
        eventDto.setEventDescription(event.getDescription());
        eventDto.setEventID(event.getId());
        return eventDto;
    }
}