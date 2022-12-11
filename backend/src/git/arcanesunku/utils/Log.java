package git.arcanesunku.utils;

public class Log {

    private static Log _Instance = null;
    private static Log Instance() {
        if(_Instance == null)
            _Instance = new Log();

        return _Instance;
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
