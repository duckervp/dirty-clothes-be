package com.dirty.shop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocProperty {
    private String defaultMerchant;
    private String defaultBrand;
    private String serverUrl;
    private String version;
}
