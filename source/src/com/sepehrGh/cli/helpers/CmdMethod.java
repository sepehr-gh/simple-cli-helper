package com.sepehrGh.cli.helpers;

import com.sepehrGh.cli.annotations.CMD;

import java.lang.reflect.Method;

public class CmdMethod {
    CMD cmd;
    Method method;

    public CmdMethod(CMD cmd, Method method) {
        this.cmd = cmd;
        this.method = method;
    }

    public CmdMethod() {
    }

    public CMD getCmd() {
        return cmd;
    }

    public void setCmd(CMD cmd) {
        this.cmd = cmd;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
