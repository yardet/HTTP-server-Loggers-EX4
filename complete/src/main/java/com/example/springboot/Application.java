package com.example.springboot;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Application  {
	//private static final Logger logger = (org.apache.logging.log4j.Logger) LogManager.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(Application.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "9583"));
		app.run(args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	/*@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.trace("ApplicationRunner: This is a trace message");
		logger.debug("ApplicationRunner: This is a debug message");
		logger.info("ApplicationRunner: This is a info message");
		logger.warn("ApplicationRunner: This is a warn message");
		logger.error("ApplicationRunner: This is a error message");
		logger.fatal("ApplicationRunner: This is a fatal message");


		<root level="info">
        <!-- appender elements go here -->
    </root>
    <root level="debug">
        <!-- appender elements go here -->
    </root>
    <appender name="RequestLogFile" class="ch.qos.logback.core.FileAppender">
        <file>logs/requests.log</file>
        <encoder>
            <pattern>%d{dd-MM-yyyy HH:mm:ss.SSS} %-5level: %m%n</pattern>
        </encoder>
    </appender>
    <appender name="RequestLogStdout" class="ch.qos.logback.core.Console.ConsoleAppender">
        <encoder>
            <pattern>%d{dd-MM-yyyy HH:mm:ss.SSS} %-5level: %m%n</pattern>
        </encoder>
    </appender>
    <logger name="request-logger" level="INFO">
        <appender-ref ref="RequestLogFile"/>
        <appender-ref ref="RequestLogStdout"/>
    </logger>
    <logger name="request-logger" level="DEBUG">
        <appender-ref ref="RequestLogFile"/>
        <appender-ref ref="RequestLogStdout"/>
    </logger>

	}*/
}
