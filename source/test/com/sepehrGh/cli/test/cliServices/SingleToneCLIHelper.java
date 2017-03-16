package com.sepehrGh.cli.test.cliServices;

import com.sepehrGh.cli.annotations.CMD;
import com.sepehrGh.cli.service.CLI;
import com.sepehrGh.cli.service.CommandLineInterfaceService;
import com.sepehrGh.cli.service.SingleToneCliService;

public class SingleToneCLIHelper {
    CLI cli = SingleToneCliService.getReference();

    public SingleToneCLIHelper() {
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
