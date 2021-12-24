package com.mycompany.myapp.config;

import com.mycompany.myapp.service.utl.JasperReportsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportsConfig {

    @Bean
    public JasperReportsUtil jasperReportsUtil() {
        return new JasperReportsUtil("reports/");
    }
}
