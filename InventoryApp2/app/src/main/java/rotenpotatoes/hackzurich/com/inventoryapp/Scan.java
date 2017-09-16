package rotenpotatoes.hackzurich.com.inventoryapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.VideoView;

import java.io.IOException;

public class Scan extends AppCompatActivity implements SurfaceHolder.Callback{

    private Button StartButton = null;
    private VideoView videoView = null;
    private SurfaceHolder holder = null;
    private Camera camera = null;
    private static final String TAG = "Video";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        try {
            camera = Camera.open();
            camera.lock();
        } catch(RuntimeException re) {
            Log.v("Camera", "Could not initialize the Camera");
            re.printStackTrace();
        }

        VideoView videoView = (VideoView) this.findViewById(R.id.videoView);
        holder = videoView.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.v(TAG, "in surfaceCreated");

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch(IOException e) {
            Log.v(TAG, "Could not start the preview");
            e.printStackTrace();
        }
    }

}
