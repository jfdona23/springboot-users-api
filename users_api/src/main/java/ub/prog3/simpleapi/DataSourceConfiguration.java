package ub.prog3.simpleapi;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {
    
    @Bean
    public DataSource buildDataSource() {

        @SuppressWarnings("rawtypes")
        DataSourceBuilder ds = DataSourceBuilder.create();
        ds.driverClassName("org.mariadb.jdbc.Driver");
        ds.url("jdbc:mariadb://localhost:3306/springbootdb");
        ds.username("root");
        ds.password("123");

        return ds.build();
    }
}
