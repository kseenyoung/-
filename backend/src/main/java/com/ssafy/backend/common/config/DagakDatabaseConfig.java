package com.ssafy.backend.common.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DagakDatabaseConfig {

    public DataSource dagakDataSource(){
        return DataSourceBuilder.create().build();
    }
}
