package com.example.dbdemo.config;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JdbcDynamicDataSource extends AbstractRoutingDataSource {
    public static final String DATA_SOURCE_NAME_PREFIX = "JNDI_db_";
    public static final String DATA_SOURCE_PROPERTY_PREFIX = "db.data.domains";


    public JdbcDynamicDataSource(DataSourceProperties dataSourceProperties) {
        System.out.println("Loading data sources from configurations ...");
        List<DataSource> webDomainDataSources = new ArrayList<DataSource>();

        List<Properties> domainDefinitions = dataSourceProperties.getDomains();
        System.out.println("Found data sources configured size"+ domainDefinitions.size() );
        if (domainDefinitions.size() == 0) {
            String msg = "No data source has been configured, please configure them under key '" + DATA_SOURCE_PROPERTY_PREFIX + "'";
            System.out.println(msg);
            throw new RuntimeException(msg);
        }

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        for (Properties properties : domainDefinitions) {
            String domainName = properties.getProperty("name");
            if (org.apache.commons.lang3.StringUtils.isBlank(domainName)) {
                String msg = "Missing domain name when specify a database under key '" + DATA_SOURCE_PROPERTY_PREFIX + "'";
                System.out.println(msg);
                throw new RuntimeException(msg);
            } else {
                domainName = domainName.trim().toLowerCase();
            }
            String key = DATA_SOURCE_NAME_PREFIX + domainName;
            PoolConfiguration conf = DataSourceFactory.parsePoolProperties(properties);

            // Load data source
            DataSource ds = loadDataSource(key, domainName, conf);
            targetDataSources.put(key, ds);

            // Add Web DB as one of the backing data sources of the default data source
            String isWebDomain = properties.getProperty("provideDefaultConnection");
            if (isWebDomain != null && "enabled".equalsIgnoreCase(isWebDomain.trim())) {
                webDomainDataSources.add(ds);
                System.out.println("Add into the default data source: "+ domainName);
            }

            // Load data sources again for each supported primary domain
            String str = properties.getProperty("delegatedDomains");
            Set<String> names = StringUtils.commaDelimitedListToSet(str);
            for (String name : names) {
                if (org.apache.commons.lang3.StringUtils.isBlank(name)) {
                    continue;
                }
                name = name.trim().toLowerCase();
                String delegatedKey = DATA_SOURCE_NAME_PREFIX + name;
                DataSource delegatedDataSource = loadDataSource(delegatedKey, name, conf);
                targetDataSources.put(delegatedKey, delegatedDataSource);
            }
        }
        this.setTargetDataSources(targetDataSources);
        
        if (webDomainDataSources.size() == 0) {
            String msg = "No Web DB data source has been configured by properties under '" + DATA_SOURCE_PROPERTY_PREFIX + " with 'provideDefaultConnection' enabled";
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
        MultiDataSource defaultDataSource = new MultiDataSource(webDomainDataSources);
        this.setDefaultTargetDataSource(defaultDataSource);
        
    }
    
    private DataSource loadDataSource(String key, String domainName, PoolConfiguration conf) {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource(conf);

        try {
            ds.createPool();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String jndiObjBeanName = DBRoutingContext.getDataSourceJNDIName();
        return jndiObjBeanName;
    }
}
