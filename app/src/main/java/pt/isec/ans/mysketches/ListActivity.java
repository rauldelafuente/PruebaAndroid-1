package pt.isec.ans.mysketches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv = findViewById(R.id.lvSketches);
        lv.setAdapter(new SketchesAdapter());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this,SketchActivity.class);
                intent.putExtra("sketchid",i);
                startActivity(intent);
                finish();
            }
        });
    }

    public class SketchesAdapter extends BaseAdapter {

        int iconW,iconH;

        public SketchesAdapter() {
            Bitmap temp = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            iconW = temp.getWidth();
            iconH = temp.getHeight();
        }

        @Override
        public int getCount() {
            return SketchesApp.app.lstSketches.size();
        }

        @Override
        public Object getItem(int i) {
            return SketchesApp.app.lstSketches.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Sketch sketch = SketchesApp.app.lstSketches.get(i);
            View v = getLayoutInflater().inflate(R.layout.sketch_item, null);
            TextView tvTitle = v.findViewById(R.id.tvTitle);
            ImageView ivIcon = v.findViewById(R.id.ivIcon);

            tvTitle.setText(sketch.title);

            Bitmap bitmap;
            if (sketch.thumbnail != null) {
                bitmap = Utils.loadPicture(sketch.thumbnail, iconW, iconH);
            } else {
                bitmap = Bitmap.createBitmap(iconW, iconH, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                paint.setColor(Color.rgb(sketch.red, sketch.green, sketch.blue));
                canvas.drawRect(0F, 0F, (float) iconW, (float) iconH, paint);
            }
            ivIcon.setImageBitmap(bitmap);
            return v;
        }
    }
}
