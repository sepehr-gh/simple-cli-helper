package com.sepehrGh.cli.test.cliServices;

import com.sepehrGh.cli.annotations.CMD;

public class Messenger {

    @CMD(name = "sendmsg",description = "Sends message to user")
    public static void sendMessage(String to,String body){
        System.out.println("Sending msg to "+to);
        System.out.println("Msg body: "+body);
    }
}
