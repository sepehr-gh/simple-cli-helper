package com.sepehrGh.cli.scanner;

import com.sepehrGh.cli.annotations.CMD;
import com.sepehrGh.cli.helpers.CmdMethod;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageBasedAnnotationScanner {
    private Map<String,CmdMethod> commandsMethodMap = new HashMap<>();

    public void scan(String packagename,Class c,boolean isJar) throws Exception {
        scanForCommands(packagename,c,isJar);
    }


    private void scanForCommands(String packagename, Class c, boolean isJar) throws IOException, ClassNotFoundException {
        Class aClassToGetClassLoaderFrom;
        packagename = packagename.replaceAll("\\.","/");
        if(c != null){
            aClassToGetClassLoaderFrom = c;
        }else{
            aClassToGetClassLoaderFrom = this.getClass();
        }

        if(!isJar){
            InputStream inputStream = (InputStream) aClassToGetClassLoaderFrom.getClassLoader().getResource(packagename).getContent();
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
                    scanForCommands(packagename + "/" + line, c, isJar);
                }
            }
        }else{
            CodeSource src = aClassToGetClassLoaderFrom.getProtectionDomain().getCodeSource();
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            while(true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null)
                    break;
                String name = e.getName();
                if (name.startsWith(packagename) && name.endsWith(".class")) {
                    String className = name.replaceAll("/",".").replaceAll(".class","");
                    Class<?> aClass = Class.forName(className);
                    Method[] declaredMethods = aClass.getDeclaredMethods();
                    for(Method method:declaredMethods){
                        CMD[] CMDs = method.getAnnotationsByType(CMD.class);
                        if(CMDs.length > 0){
                            CmdMethod cmdMethod = new CmdMethod(CMDs[0], method);
                            commandsMethodMap.put(CMDs[0].name(), cmdMethod);
                        }
                    }
                }
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
