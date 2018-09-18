package io.ee.hotelbooking.utils;

import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.util.Map;

import static io.ee.hotelbooking.utils.WebDriverBuilder.initialiseDriver;
import static io.ee.hotelbooking.utils.WebDriverBuilder.tearDownDriver;

public class Hooks {

    private static Map<String,String> config =  ConfigurationLoader.config();

    @Before
    public void setUp() {
        String driverType = config.get("browserType");
        initialiseDriver(driverType);
    }

    @After
    public void tearDown() {
        tearDownDriver();
    }
}

