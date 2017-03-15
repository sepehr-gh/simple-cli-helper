package com.cli.scanner;

import com.cli.annotations.CMD;
import com.cli.helpers.CmdMethod;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class PackageBasedAnnotationScanner {
    private Map<String,CmdMethod> commandsMethodMap = new HashMap<>();

    public void scan(String packagename) throws Exception {
        scanForCommands(packagename);
    }

    private void scanForCommands(String packagename) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        packagename = packagename.replaceAll("\\.","/");
        InputStream inputStream = (InputStream) classLoader.getResource(packagename).getContent();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String line;
        while ((line = dataInputStream.readLine()) != null){
            if(line.contains(".class")){
                String className = (packagename+"/"+line).replaceAll("/",".").replaceAll(".class","");
                Class<?> aClass = Class.forName(className);
                Method[] declaredMethods = aClass.getDeclaredMethods();
                for(Method method:declaredMethods){
                    CMD[] CMDs = method.getAnnotationsByType(CMD.class);
                    if(CMDs.length > 0){
                        CmdMethod cmdMethod = new CmdMethod(CMDs[0], method);
                        commandsMethodMap.put(CMDs[0].name(), cmdMethod);
                    }
                }
            }else{
                scanForCommands(packagename + "/" + line);
            }
        }
    }


    private boolean isPriorityValid(int i){
        if( i >= 0 && i <= 2)
            return true;
        return false;
    }

    public Map<String, CmdMethod> getCommandsMethodMap() {
        return commandsMethodMap;
    }

    public void setCommandsMethodMap(Map<String, CmdMethod> commandsMethodMap) {
        this.commandsMethodMap = commandsMethodMap;
    }

}
