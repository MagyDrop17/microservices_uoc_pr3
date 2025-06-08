package edu.uoc.epcsd.user;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.repository.DigitalItemRepository;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DigitalItemRepositoryIntegrationTest {

    // Injectem els repositoris que utiizarem per a les proves d'integració
    // Aquestes intàncies son reals i no Mocks
    @Autowired
    private DigitalItemRepository digitalItemRepository;

    @Autowired
    private DigitalSessionRepository digitalSessionRepository;

    private Long sessionId;

    @BeforeEach
    public void setUp() {

        // Creem una sessió digital per a les proves
        // S'executarà abans de cada test
        DigitalSession session =  DigitalSession.builder()
                .description("Sessio de test")
                .location("Badalona")
                .link("https://pal-robotics.com")
                .userId(1L)
                .build();

        sessionId = digitalSessionRepository.createDigitalSession(session);

    }

    @Test
    public void getItemsLinkedToSession() {

        // Creem dos items digitals i els associem a la sessió creada
        DigitalItem foo = DigitalItem.builder()
                .description("Primera imatge")
                .lat(41L)
                .lon(2L)
                .link("https://foo.com")
                .status(DigitalItemStatus.AVAILABLE)
                .digitalSessionId(sessionId)
                .build();

        DigitalItem bar = DigitalItem.builder()
                .description("Segona imatge")
                .lat(41L)
                .lon(2L)
                .link("https://bar.com")
                .status(DigitalItemStatus.AVAILABLE)
                .digitalSessionId(sessionId)
                .build();

        // Guardem els items digitals
        digitalItemRepository.createDigitalItem(foo);
        digitalItemRepository.createDigitalItem(bar);

        // Obtenim els items digitals associats a la sessió
        List<DigitalItem> digitalItems = digitalItemRepository.findAllDigitalItem();

        // Verifiquem que els items digitals s'han guardat correctament
        assertThat(digitalItems).hasSizeGreaterThanOrEqualTo(2);
        assertThat(digitalItems)
                .extracting(DigitalItem::getDescription)
                .contains("Primera imatge", "Segona imatge");
    }

}
