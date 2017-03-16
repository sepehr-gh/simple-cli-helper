package com.sepehrGh.cli.test;

import com.sepehrGh.cli.service.CommandLineInterfaceService;
import com.sepehrGh.cli.service.SingleToneCliService;
import com.sepehrGh.cli.test.cliServices.IntegerPrinter;

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
