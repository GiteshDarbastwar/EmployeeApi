package com.GtasteriX.UserAPI.Exception;

import org.aspectj.bridge.Message;

public class NameNotFoundException extends RuntimeException {
   public NameNotFoundException(String message ){
        super(message);
    }
}
