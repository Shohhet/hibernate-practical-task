package com.shoggoth.hibernateapp;

import java.lang.reflect.InvocationTargetException;

public class AppRunner {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        new ApplicationContext().init().start();
    }
}
