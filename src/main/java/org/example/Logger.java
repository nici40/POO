package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Logger {

    // order here = how serious it is, TRACE is the least, ERROR the most
    public enum LogLevel {
        TRACE("Looking at the details", "Step by step", "Following the program"),
        INFO("Operation completed", "Everything looks good", "Here is your result"),
        WARN("Something looks suspicious", "Proceed with caution", "This might be a problem"),
        ERROR("Operation failed", "Something went wrong", "Cannot accept this input");

        private final String[] randomMessages; // these go into %r

        LogLevel(String... randomMessages) {
            this.randomMessages = randomMessages;
        }

        // just picks one of the messages above at random
        public String randomMessage() {
            return randomMessages[RANDOM.nextInt(randomMessages.length)];
        }
    }

    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private String name; // every logger has its own name

    // these two are the same for all loggers, change once and it changes everywhere
    public static LogLevel level = LogLevel.INFO;
    public static String format = "[%t] [%l] %n: %m";

    public Logger(String name) {
        this.name = name;
    }

    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public static void setFormat(String newFormat) {
        format = newFormat;
    }

    public void log(LogLevel msgLevel, String message) {
        // message is less important than the level we set, so just skip it
        if (msgLevel.ordinal() < level.ordinal()) {
            return;
        }

        String time = LocalTime.now().format(TIME_FORMAT);

        // copy the format, if i change format itself every next log breaks
        String output = format;
        output = output.replace("%t", time);
        output = output.replace("%l", msgLevel.toString());
        output = output.replace("%n", name);
        output = output.replace("%r", msgLevel.randomMessage());
        output = output.replace("%m", message); // message goes last so nothing inside it gets replaced

        System.out.println(output);
    }

    // shortcuts so i dont write log(LogLevel.INFO, ...) every time
    public void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
}