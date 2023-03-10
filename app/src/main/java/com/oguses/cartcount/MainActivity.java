package com.oguses.cartcount;

import static com.google.android.gms.vision.L.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.oguses.cartcount.Sharepreference.Sharepregmanager;
import com.oguses.cartcount.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    SurfaceView surfaceView;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    TextView barcodedisplay;
    String intentData = "";
    String barcode = "";
    FloatingActionButton add,view;
    androidx.appcompat.widget.Toolbar toolbar;
    String intentdata,storedref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Scan Barcode");
        //setSupportActionBar(toolbar);

        //Objects.requireNonNull(getSupportActionBar()).setTitle("Scan Barcode");

        barcodedisplay = findViewById(R.id.barcodetext);
        surfaceView = findViewById(R.id.surfaceView);
        add = findViewById(R.id.addtocart);
        view = findViewById(R.id.displaycart);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barcode.isEmpty()){
                    Toast.makeText(MainActivity.this, "Barcode Not Found", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, ViewproductActivity.class);
                    intent.putExtra("INTENT",barcode);
                    startActivity(intent);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListitemsActivity.class);
                intent.putExtra("INTENT",storedref);
                startActivity(intent);
            }
        });

    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (barcodes.size() != 0) {
                    barcodedisplay.post(new Runnable() {
                        @Override
                        public void run() {
                            intentData = barcodes.valueAt(0).displayValue;
                            //String[] separated = intentData.split(",");
                            //String fsplit = separated[0];
                            //String[] eip = fsplit.split(":");
                            barcodedisplay.setText("Barcode Found: "+intentData);
                            barcode = intentData;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(400);
                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
        //finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
        Sharepregmanager m = new Sharepregmanager(MainActivity.this);
        if (m.getitemref().isEmpty()){
            m.storerandomnumber();
            Log.e(TAG, "onResume: "+m.getitemref() );
            storedref = m.getitemref();
        }else{
            Log.e(TAG, "onResume: "+m.getitemref() );
            storedref = m.getitemref();
        }

    }


}