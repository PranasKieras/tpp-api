package com.app;

import com.rest.TppController;
import com.exception.TppException;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.logging.Logger;

import static spark.Spark.*;
import static spark.Spark.exception;

class TppAppRunner {

    private static Logger logger = Logger.getLogger(TppAppRunner.class.getName());

    private static final String CONTENT_TYPE_JSON = "application/json";

    private TppAppRunner(){
    }

    static void runApp(){
        Injector injector = Guice.createInjector(new ServiceInjector());
        TppController controller = injector.getInstance(TppController.class);

        port(8080);

        before("/*", (req, resp) -> logger.info("api call to " + req.pathInfo()));

        post("/login", CONTENT_TYPE_JSON, controller::handleLogin);

        exception(TppException.class, (exception, req, resp) -> {
            logger.severe("failed due to:" + exception.getMessage());
            resp.type(CONTENT_TYPE_JSON);
            resp.body(((TppException)exception).getFormattedMessage());
            resp.status(((TppException)exception).getCode());
        });
        exception(Exception.class, (exception, req, resp) -> {
            logger.severe("failed due to:" + exception.getMessage());
            resp.type(CONTENT_TYPE_JSON);
            resp.status(500);
            resp.body(exception.getMessage());
        });
    }
}
