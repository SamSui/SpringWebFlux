package com.example.dbdemo.config;

import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MultiDataSource extends DelegatingDataSource {
    
    private List<DataSource> targetDataSources;
    private Random random;

    public MultiDataSource(List<DataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
        this.random = new Random(); 
    }

    public void switchToNextTargetDataSource() {

    	int offset = (int)(random.nextInt(targetDataSources.size()));
        int size = targetDataSources.size();
        DataSource nextTarget = null;
        for (int i = 0; i < size; i++) {
            if (i + offset < size) {
                nextTarget = targetDataSources.get(i + offset);
            } else {
                nextTarget = targetDataSources.get(Math.abs(i + offset - size));
            }
            if (nextTarget.equals(getTargetDataSource())) {
                continue;
            }
            if (nextTarget != null && DataSourceUtils.isHealthy(nextTarget)) {
                break;
            }
        }
        if (nextTarget != null) {
            setTargetDataSource(nextTarget);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        switchToNextTargetDataSource();
        return super.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        switchToNextTargetDataSource();
        return super.getConnection(username, password);
    }


    @Override
    public void afterPropertiesSet() {
        if (getTargetDataSources() == null || getTargetDataSources().size() == 0) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        }
    }


    public List<DataSource> getTargetDataSources() {
        return targetDataSources;
    }

    public void setTargetDataSources(List<DataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }
}
