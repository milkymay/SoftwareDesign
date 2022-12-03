package ru.akirakozov.config;

import org.springframework.context.annotation.Configuration;
import ru.akirakozov.dao.TaskDao;
import ru.akirakozov.dao.TaskJdbcDao;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskDao taskDao(DataSource dataSource) {
        return new TaskJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:product.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
