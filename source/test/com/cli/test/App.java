package com.cli.test;

import com.cli.service.CommandLineInterfaceService;

public class App {
    public static void main(String[] args) {
        try {
            CommandLineInterfaceService clis = new CommandLineInterfaceService("com.cli.test.cliServices");
            clis.setWelcomeMessage("Welcome to this test ...");
            clis.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
