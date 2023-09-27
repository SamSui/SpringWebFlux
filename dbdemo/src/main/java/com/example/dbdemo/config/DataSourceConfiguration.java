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
}
