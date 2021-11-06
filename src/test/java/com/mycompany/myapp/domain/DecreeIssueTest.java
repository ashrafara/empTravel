package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DecreeIssueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DecreeIssue.class);
        DecreeIssue decreeIssue1 = new DecreeIssue();
        decreeIssue1.setId(1L);
        DecreeIssue decreeIssue2 = new DecreeIssue();
        decreeIssue2.setId(decreeIssue1.getId());
        assertThat(decreeIssue1).isEqualTo(decreeIssue2);
        decreeIssue2.setId(2L);
        assertThat(decreeIssue1).isNotEqualTo(decreeIssue2);
        decreeIssue1.setId(null);
        assertThat(decreeIssue1).isNotEqualTo(decreeIssue2);
    }
}
