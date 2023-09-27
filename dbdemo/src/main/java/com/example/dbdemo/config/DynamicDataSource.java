package com.example.dbdemo.config;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final String DATA_SOURCE_NAME_PREFIX = "JNDI_db_";
    private static final String DOMAIN_JNDI_PREFIX = "java:/comp/env/jdbc/domains/";

    public void init() {
        Map<Object, Object> dataSources = new HashMap<Object, Object>();
        try {
            populateDataSources(dataSources);
        } catch (NamingException e) {
            System.out.println("failed to initialize data source");
        }
        this.setTargetDataSources(dataSources);
        this.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String jndiObjBeanName = DBRoutingContext.getDataSourceJNDIName();
        return jndiObjBeanName;
    }

    @Override
	public Connection getConnection() throws SQLException {
        long start = System.currentTimeMillis();
        try{
            return super.getConnection();
        }finally {
             long delta = System.currentTimeMillis() - start;
             monitorWebDBConnection(delta);
        }
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
        long start = System.currentTimeMillis();
        try{
            return super.getConnection(username, password);
        }finally {
             long delta = System.currentTimeMillis() - start;
             monitorWebDBConnection(delta);
        }
	}

	private void monitorWebDBConnection(long delta) {
        if(delta > 50){
            Object lookupKey = ObjectUtils.defaultIfNull(determineCurrentLookupKey(), DATA_SOURCE_NAME_PREFIX + "default");
            System.out.println("lookupKey: "+ lookupKey +" connection spends: "+ delta);
        }
    }

    private void populateDataSources(Map<Object, Object> dataSources) throws NamingException {
        System.out.println("Loading data sources from JNDI ...");
        boolean foundDefaultDataSource = false;
        Context initCtx = ContextFactory.getContext();
        NamingEnumeration<Binding> namingEnumeration = initCtx.listBindings(DOMAIN_JNDI_PREFIX);
        while (namingEnumeration.hasMore()) {
            Binding binding = namingEnumeration.next();
            String name = binding.getName();

            String key = DATA_SOURCE_NAME_PREFIX + name;
            String jndiName = DOMAIN_JNDI_PREFIX + name;
            dataSources.put(key, jndiName);

            if ("default".equalsIgnoreCase(name)) {
                foundDefaultDataSource = true;
                this.setDefaultTargetDataSource(jndiName);
                this.setLenientFallback(true);
                System.out.println("Set default data source to"+ jndiName);
            }
        }

        if (!foundDefaultDataSource) {
            throw new RuntimeException("No default data source be found from JNDI.");
        }
    }

}
