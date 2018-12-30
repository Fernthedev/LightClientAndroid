package com.github.fernthedev.light_clientandroid.backend;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.github.fernthedev.client.ClientThread;
import com.github.fernthedev.exceptions.DebugException;
import com.github.fernthedev.light_clientandroid.ServerLogin;
import com.github.fernthedev.light_clientandroid.backend.netty.AClientHandler;

import java.util.List;

public class AClientThread extends ClientThread {
    public boolean registering = false;
    private ServerLogin serverLogin;

    public AClientThread(AClient client, ServerLogin serverLogin) {
        super(client);
        listener = new AEventListener(client);
        clientHandler = new AClientHandler(client,listener,serverLogin);
        this.serverLogin = serverLogin;
    }

    @Override
    public synchronized void connect() {
        registering = true;
        super.connect();
    }

    @Override
    public void sendObject(Object packet) {
        super.sendObject(packet);
    }

    @Override
    public void close() {
        new DebugException().printStackTrace();
        super.close();
        if(isAppIsInBackground(serverLogin)) {
            serverLogin.startActivity(new Intent(serverLogin, ServerLogin.class));
        }

    }

    public boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


}
