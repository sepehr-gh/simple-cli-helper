package com.sepehrGh.cli.test.cliServices;

import com.sepehrGh.cli.annotations.CMD;

public class Messenger {

    @CMD(name = "sendmsg",description = "Sends message to user")
    public static void sendMessage(String to,String body){
        System.out.println("Sending msg to "+to);
        System.out.println("Msg body: "+body);
    }

    @CMD(name = "nullTest",description = "tests null method input",nullable = true)
    public static void nullableTest(String a,String b){
        System.out.println("A is "+a);
        System.out.println("B is "+b);
    }
}
