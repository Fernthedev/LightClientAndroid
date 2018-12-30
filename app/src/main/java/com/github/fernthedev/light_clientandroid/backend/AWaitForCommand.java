package com.github.fernthedev.light_clientandroid.backend;

import com.github.fernthedev.client.WaitForCommand;

public class AWaitForCommand extends WaitForCommand {

    static boolean running;

    private AClient client;
    private boolean checked;

    public AWaitForCommand(AClient client) {
        super(client);
    }


    @Override
    public void run() {
        running = true;

    }



}
