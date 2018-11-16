package pt.isec.ans.mysketches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SketchesMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketches_main);
    }

    public void onSolidColor(View v) {
        Intent intent = new Intent(this,ChooseColorActivity.class);
        startActivity(intent);
    }

    public void onFromGallery(View v) {
        Intent intent = new Intent(this,ChooseFromGalleryActivity.class);
        startActivity(intent);
    }

    public void onTakePhoto(View v) {
        Intent intent = new Intent(this,TakePhotoActivity.class);
        startActivity(intent);
    }

    public void onList(View v) {
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnList = findViewById(R.id.btnList);
        btnList.setEnabled(!SketchesApp.app.lstSketches.isEmpty());
    }
}
