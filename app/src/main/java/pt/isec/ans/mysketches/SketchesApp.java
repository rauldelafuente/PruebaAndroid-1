package pt.isec.ans.mysketches;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SketchesApp extends Application {

    public static final String TAG = "MySketches";
    public static final String SKETCHES_FILE = "mySketches.dat";
    public static SketchesApp app;
    public ArrayList<Sketch> lstSketches;

    public static Context globalContext;

    public SketchesApp() {
        app = this;
        lstSketches = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = getApplicationContext();
        loadSkecthes();
    }

    public void loadSkecthes() {
        try {
            FileInputStream fis = openFileInput(SKETCHES_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lstSketches = (ArrayList<Sketch>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            Log.i(TAG, "loadSkecthes: Create a new sketches book");
            lstSketches = new ArrayList<>();
        }
    }

    public void saveSketches() {
        try {
            FileOutputStream fos = openFileOutput(SKETCHES_FILE,MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lstSketches);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
