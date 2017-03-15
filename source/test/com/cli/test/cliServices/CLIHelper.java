package com.cli.test.cliServices;

import com.cli.annotations.CMD;
import com.cli.service.CLI;
import com.cli.service.CommandLineInterfaceService;

public class CLIHelper {
    CLI cli;

    public CLIHelper(CommandLineInterfaceService commandLineInterfaceService) {
        this.cli = commandLineInterfaceService;
    }

    @CMD(name = "help",description = "prints available commands")
    public void printHelp(){
        cli.printCommands();
    }
}
