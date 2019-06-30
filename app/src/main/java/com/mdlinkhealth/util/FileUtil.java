package com.mdlinkhealth.util;

import android.content.Context;
import android.net.Uri;
import android.util.Patterns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(Context context) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(context.getFilesDir()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MedLink_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static Uri writeInputSteamToCache(Context context, InputStream inputStream) throws Exception {
        if (null == inputStream) {
            return null;
        }

        File file = getTemporaryFile(context);

        OutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[4 * 1024]; // or other buffer size
        int read;

        while ((read = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }

        output.flush();
        output.close();
        inputStream.close();

        return Uri.fromFile(file);
    }

    private static File getTemporaryFile(Context context) {
        return new File(context.getCacheDir(), System.currentTimeMillis() + ".jpg");
    }

    public static boolean isURL(String urlVal) {
        //return Patterns.WEB_URL.matcher(urlVal).matches();
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(urlVal);//replace with string to compare
        if (m.find()) {
            return true;
        }
        return false;
    }


}
