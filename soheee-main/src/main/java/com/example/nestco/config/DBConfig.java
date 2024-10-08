//package com.example.nestco.config.local;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DBConfig {
//
//    @Bean   // Spring에서 관리하는 저장소인 Bean에 등록(jpa)
//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://1.220.247.78:3307/final_2405_team3?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("1234");
//
//        hikariConfig.setPoolName("article-pool");
//        hikariConfig.setMaximumPoolSize(3);
//
//        return new HikariDataSource(hikariConfig);
//    }
//}
