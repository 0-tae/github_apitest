package baek2thefuture.config;


import baek2thefuture.domain.webhook.scripts.ScriptExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public ScriptExecutor scriptExecutor(){
        return new ScriptExecutor();
    }
}
