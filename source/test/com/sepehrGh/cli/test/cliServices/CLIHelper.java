package com.sepehrGh.cli.test.cliServices;

import com.sepehrGh.cli.annotations.CMD;
import com.sepehrGh.cli.service.CLI;
import com.sepehrGh.cli.service.CommandLineInterfaceService;

public class CLIHelper {
    CLI cli;

    public CLIHelper(CommandLineInterfaceService commandLineInterfaceService) {
        this.cli = commandLineInterfaceService;
    }

    @CMD(name = "help",description = "prints available commands")
    public void printHelp(){
        cli.printCommands();
    }

    @CMD(name = "quit")
    public void quit(){
        cli.setIsAlive(false);
    }
}
