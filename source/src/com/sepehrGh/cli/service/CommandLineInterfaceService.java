package com.sepehrGh.cli.service;

import com.sepehrGh.cli.annotations.CMD;
import com.sepehrGh.cli.helpers.CmdMethod;
import com.sepehrGh.cli.scanner.PackageBasedAnnotationScanner;

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

    public CommandLineInterfaceService() {
    }



    public void run() throws InvocationTargetException, IllegalAccessException {
        System.out.println(welcomeMessage);

        Scanner scanner = new Scanner(System.in);
        String s = "";
        while (isAlive){
            System.out.print("> ");
            s = scanner.nextLine();
            if(!s.equals("")){
                call(s);
            }
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
            CMD cmd = commandsMethodMap.get(commandName).getCmd();
            int methodParametersCount = method.getParameterCount();
            if((cmd.nullable() && (cmdParts.length - 1) <= methodParametersCount) || (cmdParts.length - 1) == methodParametersCount){
                String[] params = new String[methodParametersCount];
                for (int i = 1;i <= methodParametersCount;i++){
                    if(i <= (cmdParts.length - 1))
                        params[i-1] = (String) cmdParts[i];
                    else
                        params[i-1] = null;

                }
                if(Modifier.isStatic(method.getModifiers())){
                    method.invoke(null,params);
                }else{
                    String declaringClass = method.getDeclaringClass().getName();
                    if(classNameReferenceMap.containsKey(declaringClass)){
                        method.invoke(classNameReferenceMap.get(declaringClass),params);
                    }
                }
            }else {
                System.out.println("Wrong parameters count!");
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

    @Override
    public void scan(String packageName) throws Exception {
        packageBasedAnnotationScanner.scan(packageName);
    }

    public void printCommands(){
        Map<String,CmdMethod> treeMap = new TreeMap<String,CmdMethod>(packageBasedAnnotationScanner.getCommandsMethodMap());
        printCommandsMap(treeMap);
    }

    private static <K,V> void printCommandsMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            String commandName = (String) entry.getKey();
            String desc = ((CmdMethod) entry.getValue()).getCmd().description();

//            test
            int commandPartSize = 40;
            System.out.print(commandName);
            for(int i = 0;i<commandPartSize-commandName.length();i++){
                System.out.print(" ");
            }
            System.out.print(desc);
            System.out.println();

        }
    }


}
