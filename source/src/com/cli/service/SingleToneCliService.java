package com.cli.service;

public class SingleToneCliService extends CommandLineInterfaceService implements CLI{
    static SingleToneCliService singleToneCliService = new SingleToneCliService();
    private SingleToneCliService(){}
    public static SingleToneCliService getReference(){
        return singleToneCliService;
    }
}
