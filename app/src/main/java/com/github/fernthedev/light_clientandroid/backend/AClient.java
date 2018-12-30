package com.github.fernthedev.light_clientandroid.backend;

import android.os.Build;

import com.github.fernthedev.client.Client;
import com.github.fernthedev.light_clientandroid.ConsoleIO;
import com.github.fernthedev.light_clientandroid.ServerLogin;

import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.StreamHandler;

public class AClient extends Client {
    private String password = null;

    private Handler handler;

    private ServerLogin serverLogin;

    private StreamHandler streamHandler;
    private ByteArrayOutputStream loggerContent;

    public String getPassword() {
        return password;
    }

    public AClient(String host, int port,ServerLogin serverLogin) {
        super(host, port);
        this.serverLogin = serverLogin;
        name = Build.MODEL;
        WaitForCommand = new AWaitForCommand(this);
        clientThread = new AClientThread(this,serverLogin);
    }

    public AWaitForCommand getWaitForCommand() {
        return (AWaitForCommand) WaitForCommand;
    }

    public AClient(String host, int port,String password,ServerLogin serverLogin) {
        this(host, port,serverLogin);
        this.password = password;

    }

    @Override
    public void initialize() {
        logger.addHandler(new ConsoleIO.LogHandler());
        super.initialize();
        logger.info("MODEL: "+android.os.Build.MODEL
                +"\nDEVICE: "+android.os.Build.DEVICE
                +"\nBRAND: "+android.os.Build.BRAND
                +"\nDISPLAY: "+android.os.Build.DISPLAY
                +"\nBOARD: "+android.os.Build.BOARD
                +"\nHOST: "+android.os.Build.HOST
                +"\nMANUFACTURER: "+android.os.Build.MANUFACTURER
                +"\nPRODUCT: "+android.os.Build.PRODUCT);
        /*loggerContent = new ByteArrayOutputStream();
        PrintStream prStr = new PrintStream(loggerContent);
        streamHandler = new StreamHandler(prStr, new SimpleFormatter());
        logger.addHandler(streamHandler);
        streamHandler.flush();*/

    }

    @Override
    public String getOSName() {
        return "Android-" + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
    }

    public String getLoggerOuput() {
        return loggerContent.toString();
    }

    @Override
    public AClientThread getClientThread() {
        return (AClientThread) clientThread;
    }

}
