package com.cli.service;

import java.lang.reflect.InvocationTargetException;

public interface CLI {
    void printCommands();
    void setIsAlive(boolean isAlive);
    void setWelcomeMessage(String welcomeMessage);
    void call(String s) throws InvocationTargetException, IllegalAccessException;
    void run() throws InvocationTargetException, IllegalAccessException;
}