package pt.isec.ans.mysketches;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class SketchArea extends View implements GestureDetector.OnGestureListener {

    Sketch sketch;
    SketchLine activeLine;
    int lineColor = Color.WHITE;
    GestureDetector gd;

    public SketchArea(Context context,Sketch sketch) {
        super(context);

        this.sketch = sketch;
        if (sketch.imgPath!=null)
            Utils.loadPicture(this,sketch.imgPath);
        else
            setBackgroundColor(Color.rgb(sketch.red,sketch.green,sketch.blue));
        if (sketch.red > 0xa0 || sketch.green > 0xa0 || sketch.blue > 0xa0)
            lineColor = Color.BLACK;
        gd = new GestureDetector(context,this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (sketch.imgPath!=null)
            Utils.loadPicture(this, sketch.imgPath);
        sketch.setThumbnail(getBitmap());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (sketch.lstLines.size() < 1)
            return;

        for(SketchLine line : sketch.lstLines)
            line.drawLine(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gd.onTouchEvent(event))
            return true;
        if (event.getAction()==MotionEvent.ACTION_UP) {
            sketch.setThumbnail(getBitmap());
            SketchesApp.app.saveSketches();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        activeLine = new SketchLine(lineColor,event.getX(), event.getY());
        sketch.lstLines.add(activeLine);
        invalidate();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float v1, float v2) {
        if (activeLine!=null)
            activeLine.addPoint(event2.getX(), event2.getY());
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float v1, float v2) {
        return false;
    }

    Bitmap getBitmap() {
        Bitmap b = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }
}
