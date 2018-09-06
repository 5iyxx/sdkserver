package com.sdkserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages= {"com.sdkserver"})
@ComponentScan("com.sdkserver")
public class Messagement_StartSDKApp
{
	public static void main(String[] args)
	{
		SpringApplication.run(Messagement_StartSDKApp.class, args);
	}
}
