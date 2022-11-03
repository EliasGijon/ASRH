package com.example.gijon_vazquez_elias_u3_a6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
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
    //Declaramos variables de control de evento camara y view camara, ademas del campo texto de referencia y el speaker para leerle al usuario la informacion
    SurfaceView camaraview;
    CountDownTimer as;
    TextView textview;
    CameraSource camaraSource;
    final int RequestCameraPermissionID = 1001;
    private TextToSpeech Speaker;
    String Text;

    //Creamos el metodo de reconocimiento de poder utilizar la camara del usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        //Iniciamos la camara y proyectamos en el view de la camara
                        camaraSource.start(camaraview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Inicializamos y vinculameos componentes del xml al elemento logico
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camaraview = findViewById(R.id.surface_view);
        textview = findViewById(R.id.text_view);

        //Creamos un Objeto de la clase TextToSpeech que se encargara de interpretar el string detectado por la camara
        Speaker = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            //Al inicializar y acceder
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //Identificamos el lenguaje del texto detectado
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

        //Creamos el  objeto de la clase textRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        //Validamos las librerias del grade
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {
            //configuramos la imagen que presentaremos en el Camaraview, con frecuencia de fotos por segundo, indicando que usaremos la camara trasera y permitiendo el enfoque.
            camaraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(200.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            //Despues de construir el objeto donde pedimos el permiso al usuario de la camara
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

            //Al reconocedor de texto le damos propiedades
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                //Al reconocedor de texto le damos propiedades a cuando identifija algo
                @Override
                public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
                    //le indicamos que queremos bloques de texto
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    //que realizae mientras el tamaña sea difente a 0
                    if (items.size() != 0) {
                        textview.post(new Runnable() {
                            //mientras se ejecute
                            @Override
                            public void run() {
                                //cree y concatene el texto que identifico
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                //detenemos la interpretacion
                                camaraSource.stop();
                                //pasamos el texto al tipo string
                                Text = stringBuilder.toString();
                                //colocamos la guia
                                textview.setText(Text);
                                //empezamos a dar indicaciones al usuario de la identificado de la imagen
                                Speaker.speak(Text, TextToSpeech.QUEUE_FLUSH, null);
                                //esperamos a que termine de hablar
                                boolean speakingEnd = Speaker.isSpeaking();
                                do{
                                    speakingEnd = Speaker.isSpeaking();
                                } while (speakingEnd);
                                     try {
                                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MainActivity.this,
                                                    new String[]{Manifest.permission.CAMERA},
                                                    RequestCameraPermissionID);
                                            return;
                                        }
                                        //Reiniciamos la interpretacion de la camara
                                        camaraSource.start(camaraview.getHolder());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                            }

                            //Detenemos el hilo de camara
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

    //detenemos el speaker
    @Override
    protected void onDestroy() {
        if(Speaker!=null){
            Speaker.stop();
            Speaker.shutdown();
        }
        super.onDestroy();
    }
}