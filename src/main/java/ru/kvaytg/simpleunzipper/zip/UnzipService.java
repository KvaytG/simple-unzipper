package ru.kvaytg.simpleunzipper.zip;

import ru.kvaytg.simpleunzipper.SimpleUnzipper;
import ru.kvaytg.simpleunzipper.util.Terminal;
import ru.kvaytg.simpleunzipper.util.RandomUtils;
import ru.kvaytg.simpleunzipper.util.StringUtils;
import ru.kvaytg.simpleunzipper.util.WinTitle;
import ru.kvaytg.wintools.api.Colors;
import ru.kvaytg.wintools.api.WinNotifications;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class UnzipService {

   public void extract(String pathToZipFile) {
      if (!validateInput(pathToZipFile)) {
         return;
      }
      final Path zipFilePath = Paths.get(pathToZipFile).toAbsolutePath();
      final Path folderPath = zipFilePath.getParent().resolve(generateFolderName(zipFilePath));
      Terminal.print("Starting archive extraction...");
      WinTitle.setTitle("Extracting");
      try {
         extractArchive(zipFilePath, folderPath);
         reportSuccess(folderPath);
      } catch (Exception e) {
         handleError(e);
      }
   }

   private boolean validateInput(String path) {
      if (StringUtils.isBlank(path)) {
         Terminal.error("Please specify a valid file path!");
         return false;
      }
      if (!path.toLowerCase().endsWith(".zip")) {
         Terminal.error("File must have a \".zip\" extension!");
         return false;
      }
      if (!Files.isRegularFile(Paths.get(path))) {
         Terminal.error("The specified path is not a file!");
         return false;
      }
      return true;
   }

   private void extractArchive(Path zipPath, Path destination) throws IOException {
      if (!Files.exists(zipPath)) {
         throw new IOException("Specified file not found: " + zipPath);
      }
      createDirectory(destination);
      try (ZipFile zipFile = new ZipFile(zipPath.toFile())) {
         Enumeration<? extends ZipEntry> entries = zipFile.entries();
         byte[] buffer = new byte[8192];
         while (entries.hasMoreElements()) {
            processZipEntry(zipFile, entries.nextElement(), destination, buffer);
         }
      }
   }

   private void processZipEntry(ZipFile zipFile, ZipEntry entry, Path destination, byte[] buffer) throws IOException {
      Path targetPath = destination.resolve(entry.getName()).normalize();
      if (!targetPath.startsWith(destination.normalize())) {
         throw new SecurityException("Detected ZipSlip attack attempt: " + entry.getName());
      }
      if (entry.isDirectory()) {
         Files.createDirectories(targetPath);
      } else {
         extractFile(zipFile, entry, targetPath, buffer);
      }
   }

   private void extractFile(ZipFile zipFile, ZipEntry entry, Path targetPath, byte[] buffer) throws IOException {
      Files.createDirectories(targetPath.getParent());
      try (InputStream is = zipFile.getInputStream(entry);
           OutputStream os = Files.newOutputStream(targetPath)) {
         int bytesRead;
         while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
         }
      }
   }

   private void createDirectory(Path path) throws IOException {
      if (Files.exists(path) && !Files.isDirectory(path)) {
         throw new IOException("Path exists but is not a directory: " + path);
      }
      Files.createDirectories(path);
   }

   private String generateFolderName(Path zipFilePath) {
      String fileName = zipFilePath.getFileName().toString();
      String baseName = fileName.replaceFirst("(?i)\\.zip$", "");
      return baseName + "_" + RandomUtils.generateRandomString(7);
   }

   private void reportSuccess(Path folderPath) {
      String folderPathStr = folderPath.toString().replace("\\", "/");
      Terminal.print("Contents successfully extracted to folder: " + folderPathStr, Colors.BRIGHT_GREEN, false);
      WinNotifications.send("Extraction completed successfully", SimpleUnzipper.APP_NAME);
      WinTitle.setTitle("Extraction successful");
   }

   private void handleError(Exception e) {
      String errorMsg;
      if (e instanceof ZipException) {
         errorMsg = "ZIP format error: " + e.getMessage();
      } else if (e instanceof SecurityException) {
         errorMsg = "Security error: " + e.getMessage();
      } else if (e instanceof IOException) {
         errorMsg = "I/O error: " + e.getMessage() +
                 (e.getCause() != null ? " (" + e.getCause().getMessage() + ")" : "");
      } else {
         errorMsg = "Unknown error: " + e.getMessage();
      }
      Terminal.error(errorMsg);
      WinTitle.setTitle("Extraction error");
   }

}