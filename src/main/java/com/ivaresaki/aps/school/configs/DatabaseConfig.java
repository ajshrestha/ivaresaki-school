/**
 * 
 */
package com.ivaresaki.aps.school.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author IvarEsaki
 *
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(env.getProperty("db.url"));
		ds.setUsername(env.getProperty("db.username"));
		ds.setPassword(env.getProperty("db.password"));
		ds.setDriverClassName(env.getProperty("db.driverClassName"));
		return ds;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		va.setDatabase(Database.MYSQL);
		va.setShowSql(true);
		va.setGenerateDdl(false);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(va);
		factory.setPackagesToScan("com.ivaresaki.apps.domain.models");
		
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory().getObject());
		return tm;
	}
	
}
