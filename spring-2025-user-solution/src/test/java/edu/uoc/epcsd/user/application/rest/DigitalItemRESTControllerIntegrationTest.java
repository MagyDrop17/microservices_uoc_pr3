package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.repository.DigitalItemRepository;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DigitalItemRESTControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DigitalItemRepository digitalItemRepository;

    @Autowired
    private DigitalSessionRepository digitalSessionRepository;

    private Long sessionId;

    @BeforeEach
    public void setUp() {

        // S'executarà abans de cada test
        // Creem una sessió digital per a les proves
        DigitalSession digitalSession = DigitalSession.builder()
                .description("sessio test")
                .location("Badalona")
                .link("https://pal-robotics.com")
                .userId(1L)
                .build();

        // Creem la sessió digital i guardem l'identificador
        sessionId = digitalSessionRepository.createDigitalSession(digitalSession);

        // Eliminem tots els items digitals existents abans de cada test
        digitalItemRepository.findAllDigitalItem().forEach(
                digitalItemRepository::removeDigitalItem
        );

        // Creem dos items digitals i els associem a la sessió creada
        digitalItemRepository.createDigitalItem(DigitalItem.builder()
                .description("Primera imatge")
                .lat(41L)
                .lon(2L)
                .link("https://foo.com")
                .status(DigitalItemStatus.AVAILABLE)
                .digitalSessionId(sessionId)
                .build()
        );

        digitalItemRepository.createDigitalItem(DigitalItem.builder()
                .description("Segona imatge")
                .lat(41L)
                .lon(2L)
                .link("https://bar.com")
                .status(DigitalItemStatus.AVAILABLE)
                .digitalSessionId(sessionId)
                .build()
        );

    }

    // Test per obtenir tots els items digitals associats a una sessió
    @Test
    void recoverDigitalItemsBySession() throws Exception {

        mockMvc.perform(get("/digitalItem/allItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[1].description").exists());
    }

}
