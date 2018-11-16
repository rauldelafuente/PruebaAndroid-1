package pt.isec.ans.mysketches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;

public class ChooseColorActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    EditText edTitle;
    SeekBar seekRed,seekGreen,seekBlue;
    FrameLayout frPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        edTitle   = findViewById(R.id.edTitle);
        seekRed   = findViewById(R.id.seekRed);
        seekGreen = findViewById(R.id.seekGreen);
        seekBlue  = findViewById(R.id.seekBlue);
        frPreview = findViewById(R.id.frPreview);

        seekRed.setMax(255); seekGreen.setMax(255); seekBlue.setMax(255);

        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int red = seekRed.getProgress();
        int green = seekGreen.getProgress();
        int blue = seekBlue.getProgress();
        frPreview.setBackgroundColor(Color.argb(255,red,green,blue));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_create,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opCreate) {
            String strTitle = edTitle.getText().toString();
            if (strTitle.length()<1)
                return true;
            int red = seekRed.getProgress();
            int green = seekGreen.getProgress();
            int blue = seekBlue.getProgress();

            Intent intent = new Intent(this,SketchActivity.class);
            intent.putExtra("title",strTitle);
            intent.putExtra("red",red);
            intent.putExtra("green",green);
            intent.putExtra("blue",blue);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
