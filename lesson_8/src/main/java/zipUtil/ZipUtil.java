package zipUtil;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zip(File file, File destinationZipFile) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destinationZipFile));
        zos.putNextEntry(new ZipEntry(file.getName()));
        zos.write(buffer);
        zos.close();
    }

    public static void unZip(File zipFile, File destinationFile) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        FileOutputStream fos = new FileOutputStream(destinationFile);
        ZipEntry zipEntry = zis.getNextEntry();
        if (zipEntry != null) {
            for (int readByte = zis.read(); readByte != -1; readByte = zis.read()) {
                fos.write(readByte);
            }
        }
    }
}
