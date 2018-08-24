package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.eventorganaizer.entity.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventOrganizationApiTestDouble {
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