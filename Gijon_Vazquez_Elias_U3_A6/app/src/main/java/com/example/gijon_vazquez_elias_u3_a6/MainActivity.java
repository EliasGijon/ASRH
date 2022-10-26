package com.example.gijon_vazquez_elias_u3_a6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SurfaceView camaraview;
    CountDownTimer as;
    TextView textview;
    CameraSource camaraSource;
    final int RequestCameraPermissionID = 1001;
    private TextToSpeech Speaker;
    String Text;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        camaraSource.start(camaraview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camaraview = findViewById(R.id.surface_view);
        textview = findViewById(R.id.text_view);

        Speaker = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = Speaker.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("Speaker", "Lenguaje no soportado");
                    } else {
                        /*Speaker.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String utteranceId) {
                                Log.i("TextToSpeech","On Start");
                            }

                            @Override
                            public void onDone(String utteranceId) {
                                try {
                                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(MainActivity.this,
                                                new String[]{Manifest.permission.CAMERA},
                                                RequestCameraPermissionID);
                                        return;
                                    }
                                    camaraSource.start(camaraview.getHolder());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.i("TextToSpeech","On Done");
                            }

                            @Override
                            public void onError(String utteranceId) {
                                Log.i("TextToSpeech","On Error");
                            }
                        });*/
                        //mButtonSpeaker.setEnable(true);
                    }
                } else {
                    Log.e("Speaker", "Fallo en Inicializacion");
                }
            }
        });

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {
            camaraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(200.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            camaraview.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        camaraSource.start(camaraview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                    camaraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        textview.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                //camaraSource.stop();
                                Text = stringBuilder.toString();
                                textview.setText(Text);
                                Speaker.speak(Text, TextToSpeech.QUEUE_FLUSH, null);
                                boolean speakingEnd = Speaker.isSpeaking();
                                do{
                                    speakingEnd = Speaker.isSpeaking();
                                } while (speakingEnd);
                                 /*    try {
                                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MainActivity.this,
                                                    new String[]{Manifest.permission.CAMERA},
                                                    RequestCameraPermissionID);
                                            return;
                                        }
                                        camaraSource.start(camaraview.getHolder());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                               //camaraSource.start(camaraview.getHolder());
                               as= new CountDownTimer(10000, 100) {
                                    int progreso = 100; // Variable que va a ir disminuyendo del progreso
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        progreso -= (1);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Speaker.speak(Text,TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }.start();*/
                            }

                            @Override
                            protected void finalize() throws Throwable {
                                //Speaker.speak(Text,TextToSpeech.QUEUE_FLUSH,null);
                                super.finalize();
                            }
                        });
                    }
                }

            });
        }
    }


    @Override
    protected void onDestroy() {
        if(Speaker!=null){
            Speaker.stop();
            Speaker.shutdown();
        }
        super.onDestroy();
    }
}