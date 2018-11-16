package pt.isec.ans.mysketches;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class Utils {
    public static String getNewFilename() {
        File file = SketchesApp.globalContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String folder = file.getAbsolutePath();
        String filename = String.format("%s/Sketch.%X.pic",folder,System.currentTimeMillis());
        return filename;
    }
    public static boolean copy(String src, String dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String cloneFile(String src) {
        String filename = getNewFilename();
        boolean result = copy(src,filename);
        if (result)
            return null;
        return filename;
    }

    public static Bitmap loadPicture(View v, String photoPath) {
        // Get the dimensions of the View/Screen
        int targetW,targetH;
        if (v != null) {
            targetW = v.getWidth();
            targetH = v.getHeight();
        } else {
            WindowManager wm = (WindowManager) SketchesApp.globalContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics=new DisplayMetrics();
            display.getMetrics(metrics);
            targetW = metrics.widthPixels;
            targetH = metrics.heightPixels;
        }
        if (targetH==0 || targetW==0)
            return null;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        if (v!=null) {
            if (v instanceof ImageView) {
                ImageView iv = (ImageView) v;
                iv.setImageBitmap(bitmap);
            } else {
                BitmapDrawable bd = new BitmapDrawable(v.getResources(), bitmap);
                v.setBackground(bd);
            }
        }
        return bitmap;
    }

    public static Bitmap loadPicture( String photoPath,int targetW,int targetH) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
       // bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);

        return bitmap;
    }

}
