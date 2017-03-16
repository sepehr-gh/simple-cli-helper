package com.sepehrGh.cli.test;

import com.sepehrGh.cli.service.CLI;
import com.sepehrGh.cli.service.SingleToneCliService;
import com.sepehrGh.cli.test.cliServices.SingleToneCLIHelper;

public class SingleToneTest {
    public static void main(String[] args) {
        /*
        reference memory address match check
        CLI cli = SingleToneCliService.getReference();
        CLI cli2 = SingleToneCliService.getReference();

        System.out.println(cli);
        System.out.println(cli2);

        */

        CLI cli = SingleToneCliService.getReference();
        cli.setWelcomeMessage("Welcome to Single Tone CLI service usage test");
        try {
            cli.scan("com.sepehrGh.cli.test.cliServices");
            SingleToneCLIHelper singleToneCLIHelper = new SingleToneCLIHelper();
            cli.register(singleToneCLIHelper);
            cli.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        Unlike what happened in App class , here although we registered a cliHelper, but we didn't need to pass our CLIService reference to it
        That's because we are using singleToneCliService inside cliHelper and we are sure our references match!
         */

    }
}
