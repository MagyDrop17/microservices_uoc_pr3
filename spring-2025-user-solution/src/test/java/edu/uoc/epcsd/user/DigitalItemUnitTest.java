package edu.uoc.epcsd.user;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalItemUnitTest {

    // Test per comprovar que un nou item digital té l'estat per defecte com a DISPONIBLE
    @Test
    void newDigitalItemShouldBeAvailableByDefault() {

        DigitalItem digitalItem = DigitalItem.builder()
                .id(1L)
                .description("test item")
                .link("http://example.com/item")
                .digitalSessionId(10L)
                .build();

        assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE);

    }

}
