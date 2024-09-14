package com.forj.delivery_history.infrastructure.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.forj.delivery_history.infrastructure.repository.hubmovement",
        entityManagerFactoryRef = "hubmovementEntityManagerFactory",
        transactionManagerRef = "hubmovementTransactionManager"
)
public class HubMovementConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource hubmovementDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean hubmovementEntityManagerFactory(){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(hubmovementDataSource());
        emf.setPackagesToScan("com.forj.delivery_history.domain.model.hubmovement");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        emf.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.format_sql", true);
        emf.setJpaPropertyMap(prop);

        return emf;
    }

    @Bean
    public PlatformTransactionManager hubmovementTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(hubmovementEntityManagerFactory().getObject());
        return transactionManager;
    }
}
