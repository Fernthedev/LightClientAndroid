package com.github.fernthedev.light_clientandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.github.fernthedev.light_clientandroid.backend.AWaitForCommand;

import java.util.logging.LogRecord;

public class ConsoleIO extends AppCompatActivity {

    private static final String newline = System.getProperty("line.separator");

    private TextView mMessage;
    private Button mSendButton;
    private static TextView mConsoleOutput;

    private static AWaitForCommand aWaitForCommand;

    public static void setaWaitForCommand(AWaitForCommand aWaitForCommand) {
        ConsoleIO.aWaitForCommand = aWaitForCommand;
    }

    private static ConsoleIO consoleIO;

    private static String oldLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console_io);

        mMessage = findViewById(R.id.messageBox);

        consoleIO = this;

        mConsoleOutput = findViewById(R.id.consoleOutputText);

        mConsoleOutput.setFocusable(false);
        mConsoleOutput.setCursorVisible(false);
        mConsoleOutput.setText(oldLog);
        mConsoleOutput.setMovementMethod(new ScrollingMovementMethod());

        mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(v -> {

            aWaitForCommand.sendMessage(mMessage.getText().toString());
            mMessage.setText("");
        });


    }

    public static ConsoleIO getConsoleIO() {
        return consoleIO;
    }

    public static class LogHandler extends java.util.logging.Handler {

        @Override
        public void publish(LogRecord record) {
            if(mConsoleOutput != null) {
                mConsoleOutput.append(record.getMessage() + newline);
                int scroll_amount = mConsoleOutput.getBottom();
                mConsoleOutput.scrollTo(0, scroll_amount);
            } else {
                addLog(record.getMessage());
            }
        }

        private static void addLog(String thing) {
            oldLog += thing + newline;
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }
    }
}
