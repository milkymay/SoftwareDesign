package ru.akirakozov.config;

import ru.akirakozov.dao.TaskInMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskInMemoryDao taskInMemoryDao() {
        return new TaskInMemoryDao();
    }
}
