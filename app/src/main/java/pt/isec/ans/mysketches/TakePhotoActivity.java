package pt.isec.ans.mysketches;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class TakePhotoActivity extends Activity {

    EditText edTitle;
    ImageView imgPreview;
    String imgPath;
    boolean permGallery = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        imgPath = null;

        edTitle = findViewById(R.id.edTitle);
        imgPreview = findViewById(R.id.imgPreview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permGallery = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            if (!permGallery)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1111) {
            permGallery = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opCreate) {
            String strTitle = edTitle.getText().toString();
            if (strTitle.length() < 1 || imgPath == null || imgPath.length() < 1)
                return true;

            Intent intent = new Intent(this, SketchActivity.class);
            intent.putExtra("title", strTitle);
            intent.putExtra("imgpath", imgPath);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onTakePhoto(View v) {
        if (!permGallery) {
            Toast.makeText(this, R.string.take_photo, Toast.LENGTH_LONG).show();
            return;
        }
        //imgPath = "/sdcard/tmp_photo";
        //Uri fileUri;
        //fileUri = Uri.fromFile(new File(imgPath));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data"); // Low density bitmap. Use FileUri (above) to get a full-sized image
            imgPreview.setImageBitmap(photo);
            imgPath = Utils.getNewFilename();
            try {
                FileOutputStream fos = new FileOutputStream(imgPath);
                photo.compress(Bitmap.CompressFormat.PNG,90,fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                imgPath=null;
            }

            return;
        }
        imgPath = null;
        super.onActivityResult(requestCode, resultCode, data);
    }

}
