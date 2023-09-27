package com.example.dbdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@ConfigurationProperties(prefix = "db.data")
public class DataSourceProperties {
    private List<Properties> domains = new ArrayList<Properties>();

    public List<Properties> getDomains() {
      return domains;
    }

    public void setDomains(List<Properties> domains) {
        this.domains = domains;
    }

}
