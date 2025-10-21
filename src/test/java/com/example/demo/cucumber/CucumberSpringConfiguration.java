package com.example.demo.cucumber;


import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.PlayerTeamCucumberApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = PlayerTeamCucumberApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration 
{
}