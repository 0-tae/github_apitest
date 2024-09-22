package com.example.githubapiserver.config;


import com.example.githubapiserver.scripts.ScriptExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public ScriptExecutor scriptExecutor(){
        return new ScriptExecutor();
    }
}
