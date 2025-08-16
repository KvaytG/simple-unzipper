package ru.kvaytg.simpleunzipper.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.kvaytg.wintools.api.Colors;
import ru.kvaytg.wintools.api.WinTerminal;
import ru.kvaytg.wintools.api.WinTools;

public class Terminal {

    private static final boolean isWindows;

    private Terminal() {
        throw new AssertionError("No instances allowed");
    }

    static {
        isWindows = WinTools.isWindows();
    }

    public static void print(@NotNull String message, @Nullable Colors color, boolean isError) {
        if (isWindows) {
            if (color == null) {
                WinTerminal.writeLine(message);
            } else {
                WinTerminal.writeLine(message, color);
            }
        } else {
            if (isError) {
                System.err.println(message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void print(@NotNull String message) {
        print(message, null, false);
    }

    public static void error(@NotNull String message) {
        print(message, Colors.BRIGHT_RED, true);
    }

}