package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.service.DigitalItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(DigitalItemRESTController.class)
public class DigitalItemRESTControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DigitalItemService digitalItemService;

    @Test
    void shouldReturnDigitalItemById() throws Exception {

        Long sessionId = 1L;

        List<DigitalItem>  digitalItems = List.of(
                DigitalItem.builder().id(1L).digitalSessionId(sessionId).description("Camera 1").build(),
                DigitalItem.builder().id(2L).digitalSessionId(sessionId).description("Camera 2").build()
        );

        when(digitalItemService.findDigitalItemBySession(Mockito.eq(sessionId)))
                .thenReturn(digitalItems);


        mockMvc.perform(get("/digitalItem/digitalItemBySession")
                        .param("digitalSessionId", sessionId.toString()))
                .andExpect(status().isOk());

    }

}
