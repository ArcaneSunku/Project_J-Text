package git.arcanesunku.utils;

import org.tinylog.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Log is a simple wrapper around TinyLog's {@link Logger} class. This lets us have control of the logging level and how
 * our messages are handled.
 */
public class Log {

    private static Log _Instance = null;
    private static Log Instance() {
        if(_Instance == null)
            _Instance = new Log();

        return _Instance;
    }

    /**
     * Prints a message based on the actual set level. This assures whatever you print is shown.
     * If the Log Level is {@link Level#NONE} then we result to {@link System#out} to print our message.
     *
     * @param message the message you want displayed
     */
    public static void print(String message) {
        switch (Instance().mLoggingLevel) {
            case INFO -> info(message);
            case WARN -> warn(message);
            case ERROR -> error(message);
            case TRACE -> trace(message);
            case DEBUG -> debug(message);

            case NONE -> System.out.println(message);
        }
    }

    /**
     * See {@link Logger#info(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void info(String message) {
        if(Instance().getLogToFile())
            Instance().appendToFile(message);

        Logger.info(message);
    }

    /**
     * See {@link Logger#warn(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void warn(String message) {
        if(Instance().getLogToFile())
            Instance().appendToFile(message);

        Logger.warn(message);
    }

    /**
     * See {@link Logger#error(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void error(String message) {
        if(Instance().getLogToFile())
            Instance().appendToFile(message);

        Logger.error(message);
    }

    /**
     * See {@link Logger#trace(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void trace(String message) {
        if(Instance().getLogToFile())
            Instance().appendToFile(message);

        Logger.trace(message);
    }

    /**
     * See {@link Logger#debug(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void debug(String message) {
        if(Instance().getLogToFile())
            Instance().appendToFile(message);

        Logger.debug(message);
    }

    public static void logToFile(boolean logToFile)
    {
        Instance().setLogToFile(logToFile);
    }

    public static boolean loggingToFile() {
        return Instance().getLogToFile();
    }

    public static Level getLevel() {
        return Instance().mLoggingLevel;
    }

    public static void setLevel(Level level) {
        Instance().setLogLevel(level);
    }

    public static void setLevel(int level) {
        Instance().setLogLevel(level);
    }

    public static String getFileName() {
        return Instance().mFileName;
    }

    public static String getLogsDir() {
        return Instance().mLogFileDir;
    }

    private Level mLoggingLevel;

    private File mLoggingFile;
    private String mFileName, mLogFileDir;
    private boolean mLogToFile;

    private Log() {
        mLoggingLevel = Level.INFO;
        mLoggingFile = null;
    }

    private void appendToFile(String text) {
        if(mLoggingFile == null)
            mLoggingFile = createLogFile();

        try(FileWriter writer = new FileWriter(mLoggingFile, true)) {
            String level = "";
            switch (mLoggingLevel) {
                case INFO  -> level = "[INFO]";
                case WARN  -> level = "[WARNING]";
                case ERROR -> level = "[ERROR]";
                case TRACE -> level = "[TRACE]";
                case DEBUG -> level = "[DEBUG]";

                case NONE -> level = "[RAW]";
            }

            level += ": ";
            writer.append(level).append(text).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createLogFile() {
        mLogFileDir = String.format("%s\\.jtext\\logs", SystemData.getLocalUserDir());
        File logFile = new File(mLogFileDir);
        if(!logFile.isDirectory()) {
            boolean dirMade = logFile.mkdir();
            if(dirMade)
                System.out.println("Directory Created!");
        }

        mFileName = generateName(mLogFileDir);
        logFile = new File(mFileName);

        return logFile;
    }

    private String generateName(String logsDir) {
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String result = "";
        if(mLogToFile) {
            try {
                do
                {
                    int id = generateID(day, month, year);
                    result = String.format("log_%d_%d_%d_%d.log", Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year), id);
                    mLoggingFile = new File(logsDir, result);
                } while(mLoggingFile.exists());

                result = mLoggingFile.getAbsolutePath();
                if(result.isEmpty())
                    throw new IOException("Failed to create a file somehow!");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            int id = generateID(day, month, year);
            result = String.format("%s\\log_%d_%d_%d_%d.log", logsDir, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year), id);
        }

        return result;
    }

    private int generateID(String day, String month, String year) {
        int d = Integer.parseInt(day), m = Integer.parseInt(month), y = Integer.parseInt(year);
        return ThreadLocalRandom.current().nextInt(d + m + y, 999999);
    }

    private boolean getLogToFile() {
        return mLogToFile;
    }

    private void setLogToFile(boolean logToFile) {
        mLogToFile = logToFile;
    }

    private void setLogLevel(Level level) {
        mLoggingLevel = level;
    }

    private void setLogLevel(int level) {
        switch (level) {
            case  0 -> setLogLevel(Level.INFO);
            case  1 -> setLogLevel(Level.WARN);
            case  2 -> setLogLevel(Level.ERROR);
            case  3 -> setLogLevel(Level.TRACE);
            case  4 -> setLogLevel(Level.DEBUG);
            default -> setLogLevel(Level.NONE);
        }
    }

    public enum Level {
        INFO,
        WARN,
        ERROR,
        TRACE,
        DEBUG,
        NONE
    }

}
