package com.sepehrGh.cli.test;

import com.sepehrGh.cli.service.CommandLineInterfaceService;
import com.sepehrGh.cli.test.cliServices.CLIHelper;

public class App {
    public static void main(String[] args) {
        try {
            CommandLineInterfaceService clis = new CommandLineInterfaceService();
            clis.scan("com.sepehrGh.cli.test.cliServices");
            clis.setWelcomeMessage("Welcome to this test ...");

            /*
            registering new CliHelper to clis.
            this is example of calling methods on referenced objects
             */
            CLIHelper cliHelper = new CLIHelper(clis); // this could be anything, but as we want to call commandLineInterfaceService.printCommands() we had to have cli reference

            clis.register(cliHelper); // registering: now when `help` command is called, cliService uses this cliHelper reference to call it


            clis.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
