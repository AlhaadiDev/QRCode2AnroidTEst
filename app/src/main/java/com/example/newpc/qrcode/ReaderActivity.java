package com.example.newpc.qrcode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    private WebView webViewDisplay;
    private TextView scanOutput;
    private ImageView shareBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        scanOutput = (TextView) findViewById(R.id.textView_scan_output);
        shareBut = (ImageView) findViewById(R.id.share_button);
        webViewDisplay = (WebView) findViewById(R.id.webView);
        final Activity activity = this;


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                integrator.setOrientationLocked(true);
            }
        });

//        shareBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBodyText = "Check it out. Your message goes here";
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
//            }
//        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                if (URLUtil.isValidUrl(result.getContents())) {
                    webViewDisplay.setVisibility(View.VISIBLE);
                    scanOutput.setVisibility(View.GONE);
                    webViewDisplay.getSettings().setJavaScriptEnabled(true);
                    webViewDisplay.getSettings().setLoadsImagesAutomatically(true);
                    webViewDisplay.getSettings().setAllowFileAccess(true);
                    webViewDisplay.getSettings().setAllowContentAccess(true);
                    webViewDisplay.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    webViewDisplay.getSettings().setSupportMultipleWindows(true);
                    webViewDisplay.getSettings().setUseWideViewPort(true);
                    webViewDisplay.getSettings().setLoadWithOverviewMode(true);

                    webViewDisplay.loadUrl(result.getContents());
                    Log.d("a1", "onActivityResult: " + result.getContents());
                    Log.d("aa1", "onActivityResult: " + result);
                } else {
                    scanOutput.setVisibility(View.VISIBLE);
                    webViewDisplay.setVisibility(View.GONE);
                    scanOutput.setText(result.getContents());
                    Log.d("a2", "onActivityResult: " + result.getContents());
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
