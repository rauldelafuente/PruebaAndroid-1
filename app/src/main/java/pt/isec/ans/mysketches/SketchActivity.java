package pt.isec.ans.mysketches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class SketchActivity extends Activity {
    SketchArea sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);

        Intent intent = getIntent();
        String strTitle = intent.getStringExtra("title");
        if (strTitle==null)
            strTitle = "(no name)";

        int red = intent.getIntExtra("red",255);
        int green = intent.getIntExtra("green",255);
        int blue = intent.getIntExtra("blue",255);

        String imgPath = intent.getStringExtra("imgpath");

        int sketchID = intent.getIntExtra("sketchid",-1);

        Sketch sketch;
        if (sketchID>=0)
            sketch=SketchesApp.app.lstSketches.get(sketchID);
        else {
            if (imgPath==null)
                sketch = new Sketch(strTitle,red,green,blue);
            else
                sketch = new Sketch(strTitle,imgPath);
            SketchesApp.app.lstSketches.add(sketch);
        }
        sa = new SketchArea(this,sketch);
        ((FrameLayout)findViewById(R.id.frSketchArea)).addView(sa);

        getActionBar().setTitle(sketch.title);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.sketch_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        } else {
            try {
                sa.lineColor = Color.parseColor(item.toString());
            } catch (Exception e) {
                return super.onOptionsItemSelected(item);
            }
        }

        return true;
    }
}

