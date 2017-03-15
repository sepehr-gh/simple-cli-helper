package com.cli.service;

import com.cli.helpers.CmdMethod;
import com.cli.scanner.PackageBasedAnnotationScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterfaceService implements CLI{
    PackageBasedAnnotationScanner packageBasedAnnotationScanner = new PackageBasedAnnotationScanner();
    Map<String,Object> classNameReferenceMap = new HashMap<String,Object>();
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

    @Override
    public void register(Object o) {
        classNameReferenceMap.put(o.getClass().getName(),o);
    }

    public void call(String s) throws InvocationTargetException, IllegalAccessException {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(s);
        while (m.find()){
            list.add(m.group(1));
        }
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
                if(Modifier.isStatic(method.getModifiers())){
                    method.invoke(null,params);
                }else{
                    String declaringClass = method.getDeclaringClass().getName();
                    System.out.println(declaringClass);
                    if(classNameReferenceMap.containsKey(declaringClass)){
                        method.invoke(classNameReferenceMap.get(declaringClass),params);
                    }
                }
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

    public void printCommands(){
        Map<String,CmdMethod> treeMap = new TreeMap<String,CmdMethod>(packageBasedAnnotationScanner.getCommandsMethodMap());
        printCommandsMap(treeMap);
    }

    private static <K,V> void printCommandsMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            CmdMethod value = (CmdMethod) entry.getValue();
            System.out.println("Command : " + entry.getKey()
                    + " Description : " + value.getCmd().description());
        }
    }


}
