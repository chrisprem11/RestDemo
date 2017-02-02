package com.digital.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.digital.repository" })
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class DatabaseConfig {

	private static final String DRIVER = "spring.datasource.driverClassName";
	private static final String URL = "spring.datasource.url";
	private static final String USERNAME = "spring.datasource.username";
	private static final String PASSWORD = "spring.datasource.password";

	private static final String JPA_HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
	private static final String JPA_SHOW_SQL = "hibernate.show_sql";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	// Configuring the Datasource Bean
	@Bean
	public DataSource dataSource() throws IOException {
		Properties properties = appProperties();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(properties.getProperty(DRIVER));
		dataSource.setUrl(properties.getProperty(URL));
		dataSource.setUsername(properties.getProperty(USERNAME));
		dataSource.setPassword(properties.getProperty(PASSWORD));
		return dataSource;
	}

	// Configuring the Entity Manager Factory Bean
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws IOException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.digital.model");
		Properties prop = appProperties();
		Properties jpaProperties = new Properties();
		jpaProperties.put(JPA_HIBERNATE_DDL, prop.getProperty(JPA_HIBERNATE_DDL));
		jpaProperties.put(JPA_SHOW_SQL, prop.getProperty(JPA_SHOW_SQL));
		jpaProperties.put(HIBERNATE_DIALECT, prop.getProperty(HIBERNATE_DIALECT));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;

	}

	// Configuring the Transaction Manager Bean
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}

	private Properties appProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("application.properties").getInputStream());
		return properties;

	}
}
