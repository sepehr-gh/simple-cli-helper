# simple-cli-helper

(2019 update: I was just playing with codes :|)

A basic (AND NOOBISH) commandline interface helper, trying to help creating commandline applications. Easily invoke a method in your application that is mapped to a command!

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

Passing less arguments when calling a command, can still call it without any errors if you set ```nullable=true``` in you ```@CMD()```, LIKE ```CMD(name ="sendmsg",description = "..",nullable = true)```. passed arguments will get their values, others will receive `null`. 

you might also want to map a command to non-static methods. to do that cliService needs an reference of method holder class to invoke the method on it. you can register your references to cliService and it handles the rest!
Consider this IntegerPrinter class:

```
public class IntegerPrinter {
    public int i;

    @CMD(name = "printint")
    public void printInt(){
        System.out.println(i);
    }
}
```
as ```printInt()``` states depeneds on i and its not static, we need to register one reference of this class to our CLIService. here is how:

```
 IntegerPrinter integerPrinter = new IntegerPrinter();
 integerPrinter.i = 10;

 clis.register(integerPrinter);
```
so now, `printint` command has `10` as output. to update state of this reference or any other references of same class type, you have to register them again so it overrides old reference that is saved inside cli service.
 
Also, Note that method inputs type has to be ```java.lang.String``` for now. 

### Single Tone CLI Service

you might need to call CLI public methods from outside, or you might need CLI reference for any reasons. in such case you are usually stuck with passing reference to other classes using setters or constructors.
but if you use SingleToneCliService, you can be sure you are having the same reference all over your application. thats why we *strongly* suggest you to use ```SingleToneCliService``` class over ```CommandLineInterfaceService``` one.

you can check available tests on SingleToneCLIService for examples.

### SCANNER TRICKS

when calling `cli.scan()` it normally uses `PackageBasedAnnotationScanner` classLoader to search for packages and classes inside your application classpath. In case you think `PackageBasedAnnotationScanner` has no access to your main application class paths from its ClassLoader, you might want to give it an access to one of your main application classes classLoader. in that case, you can pass it as second argument of scan method.
be adviced that in case you are using *simple-cli-service.jar* as your application external library, you HAVE to send one class reference of your main application to scan. also set third argument, isJar, `true` to let scanner know it should be looking for classes inside your main application jar file (if you are using cliService inside another jar)

