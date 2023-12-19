package com.example.dbdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceConfiguration {

    @Configuration
    @Conditional(LocalTomcatServerCondition.class)
    @EnableConfigurationProperties(DataSourceProperties.class )
    protected static class EmbeddedTomcat {
        private static final Logger logger = LoggerFactory.getLogger(EmbeddedTomcat.class);

        @Bean(name = "dataSource")
        DataSource dataSource(DataSourceProperties dataSourceProperties) {
            logger.info("Found embedded tomcat, so JDBC data sources are being configured.");
            return new JdbcDynamicDataSource(dataSourceProperties);
        }
    }

    @Configuration
    @ConditionalOnProperty( "catalina.home" )
    @ConditionalOnExpression( "'${catalina.home}' != '.'" )
    protected static class Tomcat {
        private static final Logger logger = LoggerFactory.getLogger(EmbeddedTomcat.class);

        @Bean(name = "dataSource")
        DataSource dataSource() {
            logger.info("Found tomcat, so JNDI data sources are being configured.");
            DynamicDataSource result = new DynamicDataSource();
            result.init();
            return result;
        }
    }


    static class LocalTomcatServerCondition extends AnyNestedCondition {

        public LocalTomcatServerCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnExpression( "'${catalina.home}' == '.'" )
        static class OnSpecialTomcatHomeFound {
        }

        @Conditional(NoTomcatHomeFoundCondition.class)
        static class OnNoTomcatHomeFound {
        }
    }

    static class NoTomcatHomeFoundCondition extends NoneNestedConditions {
        public NoTomcatHomeFoundCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty("catalina.home")
        static class OnProperty {
        }
    }
//    @Bean("tradePlatformDataSource")
//    public DataSource dataSource(
//            @Qualifier("druidDataSource") DataSource ds) throws SQLException {
//        return EncryptDataSourceFactory.createDataSource(ds, getEncryptRuleConfiguration(), new Properties());
//    }

//    private EncryptRuleConfiguration getEncryptRuleConfiguration() {
//        Properties props = new Properties();
//
//        //自带aes算法需要
//        props.setProperty("aes.key.value", aeskey);
//        EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("AES", props);
//
//        //自定义算法
//        //props.setProperty("qb.finance.aes.key.value", aeskey);
//        //EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("QB-FINANCE-AES", props);
//
//        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration();
//        encryptRuleConfig.getEncryptors().put("aes", encryptorConfig);
//
//        //START: card_info 表的脱敏配置
//        {
//            EncryptColumnRuleConfiguration columnConfig1 = new EncryptColumnRuleConfiguration("", "name", "", "aes");
//            EncryptColumnRuleConfiguration columnConfig2 = new EncryptColumnRuleConfiguration("", "id_no", "", "aes");
//            EncryptColumnRuleConfiguration columnConfig3 = new EncryptColumnRuleConfiguration("", "finshell_card_no", "", "aes");
//            Map<String, EncryptColumnRuleConfiguration> columnConfigMaps = new HashMap<>();
//            columnConfigMaps.put("name", columnConfig1);
//            columnConfigMaps.put("id_no", columnConfig2);
//            columnConfigMaps.put("finshell_card_no", columnConfig3);
//            EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columnConfigMaps);
//            encryptRuleConfig.getTables().put("card_info", tableConfig);
//        }
//        //END: card_info 表的脱敏配置
//
//        //START: pay_order 表的脱敏配置
//        {
//            EncryptColumnRuleConfiguration columnConfig1 = new EncryptColumnRuleConfiguration("", "card_no", "", "aes");
//            Map<String, EncryptColumnRuleConfiguration> columnConfigMaps = new HashMap<>();
//            columnConfigMaps.put("card_no", columnConfig1);
//            EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columnConfigMaps);
//            encryptRuleConfig.getTables().put("pay_order", tableConfig);
//        }
//
//        log.info("脱敏配置构建完成:{} ", encryptRuleConfig);
//        return encryptRuleConfig;
//
//    }
}
