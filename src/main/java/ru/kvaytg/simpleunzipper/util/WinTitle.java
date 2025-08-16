package ru.kvaytg.simpleunzipper.util;

import org.jetbrains.annotations.Nullable;
import ru.kvaytg.simpleunzipper.SimpleUnzipper;
import ru.kvaytg.wintools.api.WinTools;
import java.io.IOException;

public class WinTitle {

   private static final boolean isWindows;

   private WinTitle() {
      throw new AssertionError("No instances allowed");
   }

   static {
      isWindows = WinTools.isWindows();
   }

   public static void setTitle(@Nullable String status) {
      if (!isWindows) return;
      String title = StringUtils.isNullOrBlank(status)
              ? SimpleUnzipper.APP_NAME
              : SimpleUnzipper.APP_NAME + ": " + status;
      try {
         (new ProcessBuilder("cmd", "/c", "title " + title)).inheritIO().start().waitFor();
      } catch (IOException | InterruptedException ignored) {}
   }

}