package com.sepehrGh.cli.test;

import com.sepehrGh.cli.service.CLI;
import com.sepehrGh.cli.service.SingleToneCliService;

public class SingleToneTest {
    public static void main(String[] args) {
        CLI cli = SingleToneCliService.getReference();
        CLI cli2 = SingleToneCliService.getReference();

        System.out.println(cli);
        System.out.println(cli2);
    }
}
