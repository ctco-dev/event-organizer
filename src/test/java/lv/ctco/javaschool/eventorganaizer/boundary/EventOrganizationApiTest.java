package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventDto;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class EventOrganizationApiTest {
    @Mock
    private EntityManager em;
    @Mock
    private UserStore userStore;
    @Mock
    private Mapper mapper;
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
        MockitoAnnotations.initMocks(this);

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
        assertEquals(eventDto.getId(), eventOrganizationApi.getEventById((long) 1).getId());
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
        assertEquals("qwe", eventDto.getName());
        assertEquals(1, eventDto.getId());
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
        event.setAgenda("test");
        event.setDate("12.09.2018");
        return event;
    }

    private EventDto initEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setName(event.getName());
        eventDto.setDate(event.getDate());
        eventDto.setDescription(event.getDescription());
        eventDto.setId(event.getId());
        eventDto.setAgenda(event.getAgenda());
        eventDto.setDate(event.getDate());
        return eventDto;
    }
}