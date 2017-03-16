package com.cli.test;

import com.cli.service.CommandLineInterfaceService;
import com.cli.service.SingleToneCliService;
import com.cli.test.cliServices.IntegerPrinter;

public class NonStaticMethodsCallTest {
    public static void main(String[] args) {
        try {
            CommandLineInterfaceService clis = SingleToneCliService.getReference();
            clis.scan("com.cli.test.cliServices");
            clis.setWelcomeMessage("Welcome to this test ...");

            IntegerPrinter integerPrinter = new IntegerPrinter();
            integerPrinter.i = 10;

            clis.register(integerPrinter);

            clis.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
