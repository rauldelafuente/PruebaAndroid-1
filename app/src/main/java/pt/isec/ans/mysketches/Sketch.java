package pt.isec.ans.mysketches;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

class SketchPoint implements Serializable {
    float x, y;

    public SketchPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

class SketchLine extends ArrayList<SketchPoint>{
    int lineColor;

    public SketchLine(int lineColor, float xi, float yi) {
        this.lineColor = lineColor;
        add(new SketchPoint(xi, yi));
    }

    public void drawLine(Canvas canvas) {
        Paint paint = new Paint(Paint.DITHER_FLAG);
        paint.setStrokeWidth(5.0f);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.FILL);

        SketchPoint oldPoint = get(0);
        for (int i = 1; i < size(); i++) {
            SketchPoint point = get(i);
            canvas.drawLine(oldPoint.x, oldPoint.y, point.x, point.y, paint);
            oldPoint = point;
        }
    }

    public void addPoint(float x, float y) {
        add(new SketchPoint(x,y));
    }
}

public class Sketch implements Serializable {
    String title;
    int red,green,blue;
    String imgPath;
    ArrayList<SketchLine> lstLines;
    String thumbnail;

    public Sketch(String title,int red, int green, int blue) {
        this.title=title;
        this.red = red;
        this.green = green;
        this.blue = blue;
        imgPath=null;
        lstLines = new ArrayList<>();
        thumbnail = Utils.getNewFilename();
    }

    public Sketch(String title, String imgPath) {
        this.title = title;
        this.red = 255;
        this.green = 255;
        this.blue = 255;
        this.imgPath = imgPath;
        lstLines = new ArrayList<>();
        thumbnail = Utils.getNewFilename();
    }

    void setThumbnail(Bitmap bitmap) {
        Bitmap temp = Bitmap.createBitmap(bitmap, 0,
                bitmap.getHeight()/2-bitmap.getWidth()/2,bitmap.getWidth(),bitmap.getWidth());
        Bitmap temp1 = Bitmap.createScaledBitmap(temp,256,256,false);
        try {
            FileOutputStream fos = new FileOutputStream(thumbnail);
            temp1.compress(Bitmap.CompressFormat.PNG,90,fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {

        }
    }
}
