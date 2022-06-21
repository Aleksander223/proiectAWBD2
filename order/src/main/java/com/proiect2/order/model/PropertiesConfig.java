package com.proiect2.order.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("cost")
@Getter
@Setter
public class PropertiesConfig {
    private float tva;
}
