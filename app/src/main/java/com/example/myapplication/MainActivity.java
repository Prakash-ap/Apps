package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private TextView scanresult;
    private Button scanbutton1, scanbutton2;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button scanbutton=(Button)findViewById(R.id.scanbuton);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();


            }else{
                showResultDialogue(result.getContents());
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void showResultDialogue(final String contents) {

        AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + contents)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", contents);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }

    }


//        scanresult=(TextView)findViewById(R.id.text_scanresults);
//        scanbutton1=(Button)findViewById(R.id.barsacn_button);
//        scanbutton2=(Button)findViewById(R.id.qrsacn_button);
////        scanbutton1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                IntentIntegrator scanintent=new IntentIntegrator(MainActivity.this );
////            scanintent.initiateScan();
////            }
////        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==0) {
//            if (resultCode == RESULT_OK) {
//                String contents = data.getStringExtra("SCAN RESULT");
//                String format = data.getStringExtra("SCAN RESULT FORMAT");
//                Toast.makeText(this, "Contents:" + contents + "Format" + format, Toast.LENGTH_SHORT).show();
//                scanresult.setText("Contents" + contents + "FORMAT" + format);
//            }
//        }
//    }
//
//    public void scanQR(View view) {
//
//        Intent qrintent=new Intent(ACTION_SCAN);
//        qrintent.putExtra("SCAN MODE","QR MODE");
//        startActivityForResult(qrintent,0);
//    }
//
//    public void scanBar(View view) {
//
//        Intent barintent=new Intent(ACTION_SCAN);
//        barintent.putExtra("SCAN MODE","PRODUCT MODE");
//        startActivityForResult(barintent,0);
//    }

