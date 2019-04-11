package team.njupt.machine.pojo.decompress;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class UnZip {

    public static void unZip(String srcFilePath, String destDirPath) {
        File file = new File(srcFilePath);

        if (!file.exists()) {
            return;
        }

        ZipFile zf = null;
        ZipEntry entry;
        try {
            zf = new ZipFile(file, "GB2312");
            Enumeration<ZipEntry> entries = zf.getEntries();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();

                if (entry.isDirectory()) {
                    String dirPath = destDirPath + File.separator + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    File targetFile = new File(destDirPath + File.separator + entry.getName());

                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }

                    targetFile.createNewFile();

                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zf.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int count;
                    byte[] buf = new byte[8192];
                    while ((count = is.read(buf)) != -1) {
                        fos.write(buf, 0, count);
                    }

                    fos.close();
                    is.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zf != null) {
                    zf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
