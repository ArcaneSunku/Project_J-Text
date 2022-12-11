package git.arcanesunku.utils;

import org.tinylog.Logger;

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
        Logger.info(message);
    }

    /**
     * See {@link Logger#warn(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void warn(String message) {
        Logger.warn(message);
    }

    /**
     * See {@link Logger#error(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void error(String message) {
        Logger.error(message);
    }

    /**
     * See {@link Logger#trace(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void trace(String message) {
        Logger.trace(message);
    }

    /**
     * See {@link Logger#debug(Object)} for more info.
     *
     * @param message the message we want to display
     */
    public static void debug(String message) {
        Logger.debug(message);
    }

    public Level getLevel() {
        return mLoggingLevel;
    }

    public static void setLevel(Level level) {
        Instance().setLogLevel(level);
    }

    public static void setLevel(int level) {
        Instance().setLogLevel(level);
    }

    private Level mLoggingLevel;

    private Log() {
        mLoggingLevel = Level.INFO;
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

    enum Level {
        INFO,
        WARN,
        ERROR,
        TRACE,
        DEBUG,
        NONE
    }

}
