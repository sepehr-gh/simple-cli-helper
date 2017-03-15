package com.cli.service;

import com.cli.helpers.CmdMethod;
import com.cli.scanner.PackageBasedAnnotationScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterfaceService {
    PackageBasedAnnotationScanner packageBasedAnnotationScanner = new PackageBasedAnnotationScanner();
    String welcomeMessage = "";
    boolean isAlive = true;

    public CommandLineInterfaceService(String packageName) throws Exception {
        packageBasedAnnotationScanner.scan(packageName);
    }

    public void run() throws InvocationTargetException, IllegalAccessException {
        System.out.println(welcomeMessage);

        Scanner scanner = new Scanner(System.in);
        String s = "";
        while (isAlive){
            if(!s.equals("")){
                call(s);
            }
            System.out.print("> ");
            s = scanner.nextLine();
        }

        System.out.println("bye!");
    }

    private void call(String s) throws InvocationTargetException, IllegalAccessException {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(s);
        while (m.find())
            list.add(m.group(1));
        Object[] cmdParts = list.toArray();
        Map<String, CmdMethod> commandsMethodMap = packageBasedAnnotationScanner.getCommandsMethodMap();
        String commandName = (String) cmdParts[0];
        if(commandsMethodMap.containsKey(commandName)){
            Method method = commandsMethodMap.get(commandName).getMethod();
            int methodParametersCount = method.getParameterCount();
            if((cmdParts.length - 1) == methodParametersCount){
                String[] params = new String[methodParametersCount];
                for (int i = 1;i<=methodParametersCount;i++){
                    params[i-1] = (String) cmdParts[i];
                }
                method.invoke(null,params);
            }else if(cmdParts.length -1 > methodParametersCount){
                System.out.println("Wrong parameters count! "+cmdParts.length);
            }
        }else{
            System.out.println("No such command found. "+cmdParts[0]);
        }
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
