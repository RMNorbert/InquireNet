package com.rmnnorbert.InquireNet.customendpoints.custom;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component//To get the endpoint, a bean (@Component) and the @Endpoint annotation needed
@Endpoint(id = "features")//The path of the endpoint is determined by the id parameter
public class FeaturesEndpoint {
    private final Map<String, Feature> features = new ConcurrentHashMap<>();
    private final Environment environment;
    @Autowired
    public FeaturesEndpoint(Environment environment) {
        this.environment = environment;
    }
    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/features.properties"));
        } catch (IOException e) {
            // Handle the exception if the properties file cannot be loaded
            e.printStackTrace();
            return;
        }

        // Iterate over the properties and populate the features map
        for (String propertyName : properties.stringPropertyNames()) {
            String propertyValue = properties.getProperty(propertyName);
            String[] featureValues = propertyValue.split("\\|");
            if (featureValues.length >= 2) {
                boolean featureEnabled = Boolean.parseBoolean(featureValues[0]);
                String featureDescription = featureValues[1];
                features.put(propertyName, new Feature(featureEnabled, featureDescription));
            }
        }
    }
    @ReadOperation
    public Map<String, Feature> features() {
        return features;
    }

    @ReadOperation
    public Feature feature(@Selector String name) {
        return features.get(name);
    }

    @WriteOperation
    public void configureFeature(@Selector String name, Feature feature) {
        features.put(name, feature);
    }

    @DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }
        @Getter
        @Setter
        public static class Feature {
            private Boolean enabled;
            private String description;
            public Feature(Boolean enabled, String description) {
                this.enabled = enabled;
                this.description = description;
            }

        }
}
