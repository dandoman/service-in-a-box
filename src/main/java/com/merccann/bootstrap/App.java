package com.merccann.bootstrap;

import java.beans.PropertyVetoException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.merccann.dao.AppDao;
import com.merccann.logic.AppLogic;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.merccann" })
@MapperScan("com.merccann.dao.mapper")
@EnableSwagger2
public class App {
	
	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {

			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {

			}

		};
	}

	@Bean
	public AppDao createDao() {
		return new AppDao();
	}

	@Bean
	public AppLogic createLogic() {
		return new AppLogic();
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory()
			throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource());
		return (SqlSessionFactory) factory.getObject();
	}
	
	@Bean
	@Primary
	public DataSource dataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass("org.postgresql.Driver"); //How do I read this shit from application.properties?
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/dev");
		ds.setUser("dev");
		ds.setPassword("dev");
		
		return ds;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
