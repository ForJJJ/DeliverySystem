package com.forj.delivery.infrastructure.config;

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
        basePackages = "com.forj.delivery.infrastructure.repository.delivery",
        entityManagerFactoryRef = "deliveryEntityManagerFactory",
        transactionManagerRef = "deliveryTransactionManager"
)
public class DeliveryConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource deliveryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean deliveryEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(deliveryDataSource());
        emf.setPackagesToScan("com.forj.delivery.domain.model.delivery");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        emf.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "create");
        prop.put("hibernate.format_sql", true);
        emf.setJpaPropertyMap(prop);

        return emf;
    }

    @Bean
    public PlatformTransactionManager deliveryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(deliveryEntityManagerFactory().getObject());
        return transactionManager;
    }

}