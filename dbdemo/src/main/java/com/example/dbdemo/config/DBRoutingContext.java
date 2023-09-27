package com.example.dbdemo.config;

public class DBRoutingContext {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void selectDefaultDataSource() {
        contextHolder.remove();
    }

    public static void setDataSourceJNDIName(String webDomainName) {
        String jndiName = "JNDI_db_" + webDomainName.toLowerCase();
        contextHolder.set(jndiName);
    }

    public static String getDataSourceJNDIName() {
        return (String) contextHolder.get();
    }

    public static void clearDataSourceJNDIName() {
        contextHolder.remove();
    }
}
