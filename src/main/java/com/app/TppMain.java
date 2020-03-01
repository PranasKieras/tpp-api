package com.app;

import static spark.Spark.*;

import com.controller.TppController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.injector.ServiceInjector;

import java.util.logging.Logger;

public class TppMain {

    private static Logger logger = Logger.getLogger(TppMain.class.getName());

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ServiceInjector());
        TppController controller = injector.getInstance(TppController.class);

        port(8080);

        before("/*", (req, resp) -> logger.info("api call to " + req.pathInfo()));
        before("/*", (req, resp) -> {

        });
        post("/login", "application/json", controller::handleLogin);

        exception(Exception.class, (exception, req, resp) -> {
            logger.severe("failed due to:" + exception.getMessage());
            resp.type("application/json");
        });

    }

}