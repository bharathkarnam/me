package com.data.dao.service.mongo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class MongoApp 
{
    public static void main( String[] args )
    {
		new SpringApplicationBuilder(MongoApp.class).registerShutdownHook(true).run(args);
    }
    
}
