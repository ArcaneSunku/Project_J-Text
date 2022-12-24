package git.arcanesunku.utils;

import java.util.Locale;

/**
 * Provides us paths relevant to OS (ie user.home, appdata, etc).
 */
public class SystemData {

    public static String getLocalUserDir() {
        if(getSimplifiedOS().equalsIgnoreCase("win"))
            return System.getenv("APPDATA");

        if(getSimplifiedOS().equalsIgnoreCase("linux"))
            return System.getProperty("user.home");

        return "unknown";
    }

    public static String getArch() {
        return System.getProperty("os.arch").toLowerCase(Locale.ROOT);
    }

    public static String getSimplifiedArch() {
        String arch = getArch();
        if(arch.contains("64")) return "x64";

        return arch;
    }

    public static String getOS() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT);
    }

    public static String getSimplifiedOS() {
        String os = getOS();

        if(os.contains("windows")) return "win";
        if(os.contains("linux")) return "linux";

        return "unknown";
    }

}
