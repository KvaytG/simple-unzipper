package ru.kvaytg.simpleunzipper.util;

import org.jetbrains.annotations.NotNull;

public class StringUtils {

   private StringUtils() {
      throw new AssertionError("No instances allowed");
   }

   public static int length(CharSequence cs) {
      return cs == null ? 0 : cs.length();
   }

   public static boolean isBlank(CharSequence cs) {
      int strLen = length(cs);
      if (strLen != 0) {
         for (int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
               return false;
            }
         }
      }
      return true;
   }

   public static boolean isNullOrBlank(CharSequence cs) {
      return cs == null || isBlank(cs);
   }

   public static String formatPath(@NotNull String path, boolean removeLastSlash) {
      path = path.replaceAll("[\\\\/]+", "/");
      if (removeLastSlash && path.endsWith("/")) {
         path = path.substring(0, path.length() - 1);
      }
      return path;
   }

}