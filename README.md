# simple-cli-helper

A basic commandline interface helper, trying to help creating commandline applications. Easily invoke a method in your application that is mapped to a command!

simply use `@CMD` annotations on methods you want to introduce as a command. Then choose and implementation of `CLI interface` and scan packages for this annotation. you are done!

## examples

introducing new messenger commands as an example. consider `sendmsg` as a message sender function or command. it has two arguments, *to* and *msg_body* .

```
package com.cli.test.cliServices;

import com.cli.annotations.CMD;

public class Messenger {

    @CMD(name = "sendmsg",description = "Sends message to user")
    public static void sendMessage(String to,String body){
        System.out.println("Sending msg to "+to);
        System.out.println("Msg body: "+body);
    }
}
```

Here is main method of your application:

```
public static void main(String[] args) {
        try {
            CommandLineInterfaceService clis = new CommandLineInterfaceService();
            clis.scan("com.cli.test.cliServices"); // you can scan more packages
            clis.setWelcomeMessage("Welcome to this test ...");
            clis.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

so cli service waits for users input, and here is output when user input is ```sendmsg john "hi body, how u doing?"```

```
Sending msg to john
Msg body: hi body, how u doing?
```

so you could replace ```static void sendMessage(String to,String body)``` with any other methods or add more commands to your application just by using ```@CMD``` annotation on static methods.

Note that method inputs type has to be ```java.lang.String``` for now. 
