package com.ssafy.backend.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(value = "com.ssafy.backend.security.model.mapper", sqlSessionFactoryRef="securitySqlSessionFactory")
//@EnableTransactionManagement
public class SecurityDatabaseConfig {


    @Bean(name="securityDataSource")
    @ConfigurationProperties(prefix="spring.security.datasource") //appliction.properties 참고.
    public DataSource securityDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "securitySqlSessionFactory")
    public SqlSessionFactory securitySessionFactory(@Qualifier("securityDataSource") DataSource securityDataSource, ApplicationContext applicationContext) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(securityDataSource);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mappers/*.xml")); //쿼리작성용 mapper.xml위치 설정.
        return sessionFactory.getObject();
    }

    @Bean(name="securitySqlSessionTemplate")
    public SqlSessionTemplate securitySqlSessionTemplate(@Qualifier("securitySqlSessionFactory") SqlSessionFactory securitySqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(securitySqlSessionFactory);
    }

    @Bean(name = "securityTransactionManager")
//    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("securityDataSource") DataSource securityDataSource) {
        return new DataSourceTransactionManager(securityDataSource);
    }
}

