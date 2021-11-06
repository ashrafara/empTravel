package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DecreeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Decree.class);
        Decree decree1 = new Decree();
        decree1.setId(1L);
        Decree decree2 = new Decree();
        decree2.setId(decree1.getId());
        assertThat(decree1).isEqualTo(decree2);
        decree2.setId(2L);
        assertThat(decree1).isNotEqualTo(decree2);
        decree1.setId(null);
        assertThat(decree1).isNotEqualTo(decree2);
    }
}
