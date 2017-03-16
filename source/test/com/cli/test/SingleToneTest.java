package com.cli.test;

import com.cli.service.CLI;
import com.cli.service.SingleToneCliService;

public class SingleToneTest {
    public static void main(String[] args) {
        CLI cli = SingleToneCliService.getReference();
        CLI cli2 = SingleToneCliService.getReference();

        System.out.println(cli);
        System.out.println(cli2);
    }
}
