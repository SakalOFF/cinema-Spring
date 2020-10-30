package com.company.cinema.exceptions;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class MyExceptionHandler {

    static final Logger logger = Logger.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public void databaseError(Exception e){
        logger.warn(e);
    }

}
