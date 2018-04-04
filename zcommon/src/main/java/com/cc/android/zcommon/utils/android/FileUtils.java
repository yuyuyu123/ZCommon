package com.cc.android.zcommon.utils.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File Utils.
 *
 * Created by LiuLiWei on 2016/3/24.
 */
public class FileUtils {

    public FileUtils() {
         /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * Convert the input stream to a file.
     * @param in
     * @param file
     * @return
     */
    public static boolean convertInputStreamToFile(InputStream in, File file) {
        try {
            InputStream inputStream = in;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[1024];
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
