package ru.kvaytg.simpleunzipper;

import ru.kvaytg.simpleunzipper.util.Terminal;
import ru.kvaytg.simpleunzipper.util.StringUtils;
import ru.kvaytg.simpleunzipper.util.WinTitle;
import ru.kvaytg.simpleunzipper.zip.UnzipService;
import java.io.File;
import java.net.URISyntaxException;

public class SimpleUnzipper {

    public static final String APP_NAME = SimpleUnzipper.class.getSimpleName();

    public static void main(String[] args) {
        WinTitle.setTitle(null);
        if (args.length == 0) {
            Terminal.error("Usage: java -jar " + getJarFileName() + " <file.zip>");
            return;
        }
        new UnzipService().extract(
                StringUtils.formatPath(
                        String.join(" ", args), true
                )
        );
    }

    private static String getJarFileName() {
        try {
            return (new File(
                    SimpleUnzipper.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            )).getName();
        } catch (URISyntaxException notUsed) {
            return APP_NAME;
        }
    }

}