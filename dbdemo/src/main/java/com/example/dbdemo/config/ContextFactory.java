package com.example.dbdemo.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextFactory {
    public static Context getContext() throws NamingException {
        return new InitialContext();
    }
}
