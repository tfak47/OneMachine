package team.njupt.machine.pojo.compress;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class ZipCompress {

    private static final int BUFSIZE = 1024;

    public static void compress(String srcFilePath, String destFilePath) {
        File src = new File(srcFilePath);

        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }

        File zipFile = new File(destFilePath);
        try {
            ZipOutputStream zos = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32()));
            String baseDir = "";
            compressByType(src, zos, baseDir);

            zos.setEncoding("GB2312");
            zos.closeEntry();
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compressByType(File src, ZipOutputStream zos, String baseDir) {
        if (!src.exists())  {
            return;
        }

        if (src.isFile()) {
            compressFile(src, zos, baseDir);
        } else if (src.isDirectory()) {
            compressDir(src, zos, baseDir);
        }
    }

    private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists()) {
            return;
        }

        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            zos.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int count;
            byte[] buf = new byte[BUFSIZE];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }

            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if(files.length == 0){
            try {
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (File file : files) {
            compressByType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }
}
