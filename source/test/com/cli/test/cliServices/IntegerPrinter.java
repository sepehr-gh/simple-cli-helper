package com.cli.test.cliServices;

import com.cli.annotations.CMD;

public class IntegerPrinter {
    public int i;

    @CMD(name = "printint")
    public void printInt(){
        System.out.println(i);
    }
}
