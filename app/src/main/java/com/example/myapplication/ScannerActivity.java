package com.example.myapplication;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScannerActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView decoratedBarcodeView;
    private CaptureManager captureManager;
    private Button torchlightbtn;
    private boolean isFlashlightOn=false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        decoratedBarcodeView=findViewById(R.id.zxing_barcode_scanner);
        decoratedBarcodeView.setTorchListener(this);

        torchlightbtn=(Button)findViewById(R.id.torchlight);

        if(!hashFlash()){
            torchlightbtn.setVisibility(View.GONE);
        }else {
            torchlightbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SwitchFalshOn();
                }
            });
        }

        captureManager=new CaptureManager(this,decoratedBarcodeView);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    private void SwitchFalshOn() {
        if(isFlashlightOn){
            decoratedBarcodeView.setTorchOff();
            isFlashlightOn=false;
        }else{
            decoratedBarcodeView.setTorchOn();
            isFlashlightOn=true;
        }
    }

    private boolean hashFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

    }

    @Override
    public void onTorchOn() {
        torchlightbtn.setText("Turn Off FlashLight");

    }

    @Override
    public void onTorchOff() {
        torchlightbtn.setText("Turn ON Flashlight");

    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return decoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
