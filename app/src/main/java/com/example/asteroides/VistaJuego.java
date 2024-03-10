package com.example.asteroides;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class VistaJuego extends View implements SensorEventListener {
    // //// ASTEROIDES //////
    private List<Grafico> asteroides; // Vector con los Asteroides
    private int numAsteroides = 5; // Número inicial de asteroides
    private int numFragmentos = 3; // Fragmentos en que se divide
    private boolean usarSensores;


    public VistaJuego(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable drawableNave, drawableAsteroide, drawableMisil;
        SharedPreferences pref = PreferenceManager.
                getDefaultSharedPreferences(getContext());
        // Activar/desactivar sensores
       usarSensores = pref.getBoolean("pref_key_usar_sensores", true);

        //ASTEROIDES
        if (pref.getString("graficos", "1").equals("3")) {
            // Utilizar VectorDrawable
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            /// Obtener el nombre del recurso vectorial según el tamaño
            String tamanoAsteroides = "a"; // Puedes ajustar este valor según tus necesidades
            String nombreRecurso = tamanoAsteroides;
            int vectorDrawableResourceId = getResources().getIdentifier(nombreRecurso, "drawable", context.getPackageName());
            // Verificar si el recurso vectorial existe antes de intentar cargarlo
            if (vectorDrawableResourceId != 0) {
                // Utilizar un recurso vectorial existente
                drawableAsteroide = AppCompatResources.getDrawable(context, vectorDrawableResourceId);
                setBackgroundColor(Color.BLACK);
            } else {
                Log.e("VistaJuego", "Recurso vectorial no encontrado para " + nombreRecurso);
                drawableAsteroide = AppCompatResources.getDrawable(context, R.drawable.asteroide1);
            }
        } else {
            // Utilizar Bitmap (o ShapeDrawable, según tu necesidad)
            setLayerType(View.LAYER_TYPE_HARDWARE, null);

            if (pref.getString("graficos", "1").equals("0")) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                Path pathAsteroide = new Path();
                pathAsteroide.moveTo((float) 0.3, (float) 0.0);
                pathAsteroide.lineTo((float) 0.6, (float) 0.0);
                pathAsteroide.lineTo((float) 0.6, (float) 0.3);
                pathAsteroide.lineTo((float) 0.8, (float) 0.2);
                pathAsteroide.lineTo((float) 1.0, (float) 0.4);
                pathAsteroide.lineTo((float) 0.8, (float) 0.6);
                pathAsteroide.lineTo((float) 0.9, (float) 0.9);
                pathAsteroide.lineTo((float) 0.8, (float) 1.0);
                pathAsteroide.lineTo((float) 0.4, (float) 1.0);
                pathAsteroide.lineTo((float) 0.0, (float) 0.6);
                pathAsteroide.lineTo((float) 0.0, (float) 0.2);
                pathAsteroide.lineTo((float) 0.3, (float) 0.0);

                ShapeDrawable dAsteroide = new ShapeDrawable(
                        new PathShape(pathAsteroide, 1, 1));
                dAsteroide.getPaint().setColor(Color.WHITE);
                dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
                dAsteroide.setIntrinsicWidth(100);
                dAsteroide.setIntrinsicHeight(100);
                drawableAsteroide = dAsteroide;
                setBackgroundColor(Color.BLACK);
            } else {
                // Utilizar Bitmap
                drawableAsteroide = AppCompatResources.getDrawable(context, R.drawable.asteroide1);
            }
        }

        //NAVE
        if (pref.getString("graficos", "1").equals("3")) {
            // Utilizar VectorDrawable
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            /// Obtener el nombre del recurso vectorial según el tamaño
            String escogeNave = "navee"; // Puedes ajustar este valor según tus necesidades
            String nombreRecurso = escogeNave;
            int vectorDrawableResourceId = getResources().getIdentifier(nombreRecurso, "drawable", context.getPackageName());
            // Verificar si el recurso vectorial existe antes de intentar cargarlo
            if (vectorDrawableResourceId != 0) {
                // Utilizar un recurso vectorial existente
                drawableNave = AppCompatResources.getDrawable(context, vectorDrawableResourceId);
                setBackgroundColor(Color.BLACK);
            } else {
                Log.e("VistaJuego", "Recurso vectorial no encontrado para " + nombreRecurso);
                drawableNave = AppCompatResources.getDrawable(context, R.drawable.nave);
            }
        } else {
            // Utilizar Bitmap (o ShapeDrawable, según tu necesidad)
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            if (pref.getString("graficos", "1").equals("0")) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                Path pathNave = new Path();
                pathNave.moveTo(0.0f, 0.0f);
                pathNave.lineTo(20.0f, 7.5f);
                pathNave.lineTo(0.0f, 15.0f);
                pathNave.lineTo(0.0f, 0.0f);
                ShapeDrawable dNave = new ShapeDrawable(
                        new PathShape(pathNave, 20, 15));
                dNave.getPaint().setColor(Color.WHITE);
                dNave.getPaint().setStyle(Paint.Style.STROKE);
                dNave.setIntrinsicWidth(50);
                dNave.setIntrinsicHeight(50);
                drawableNave = dNave;
                setBackgroundColor(Color.BLACK);
            } else {
                setLayerType(View.LAYER_TYPE_HARDWARE, null);
                drawableNave =
                        AppCompatResources.getDrawable(context, R.drawable.nave);

            }
        }

        //MISIL
        if (pref.getString("graficos", "1").equals("0")) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ShapeDrawable dMisil = new ShapeDrawable( new RectShape());
            dMisil.getPaint().setColor(Color.WHITE);
            dMisil.getPaint().setStyle(Paint.Style.STROKE);
            dMisil.setIntrinsicWidth(15);
            dMisil.setIntrinsicHeight(3);
            drawableMisil = dMisil;
        } else {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            drawableMisil =
                    AppCompatResources.getDrawable(context, R.drawable.misil1);
        }
        misil = new Grafico(this, drawableMisil);
        asteroides = new ArrayList<Grafico>();
        nave = new Grafico(this, drawableNave);
        for (int i = 0; i < numAsteroides; i++) {
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
        SensorManager mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if(!listSensors.isEmpty()){
            Sensor orientationSensor = listSensors.get(0);
            mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override protected void onSizeChanged(int ancho, int alto,
                                           int ancho_anter, int alto_anter) {
        super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
        // Una vez que conocemos nuestro ancho y alto.
        for (Grafico asteroide : asteroides) {
            do{
                asteroide.setCenX(Math.random() * ancho);
                asteroide.setCenY(Math.random() * alto);
            } while (asteroide.distancia(nave) < (ancho+alto)/5);
        }
        nave.setCenX(ancho / 2.0);
        nave.setCenY(alto / 2.0);
        ultimoProceso = System.currentTimeMillis();
        thread.start();
        SensorManager mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        // Activar/desactivar sensores según la preferencia
        if (usarSensores) {
            Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometerSensor != null) {
                mSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SensorManager mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.unregisterListener(this);
    }
    @Override protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        if (nave != null) {
            nave.dibujaGrafico(canvas);
        }
        synchronized (asteroides) {
            for (Grafico asteroide : asteroides) {
                asteroide.dibujaGrafico(canvas);
            }
        }
        if (misilActivo) {
            misil.dibujaGrafico(canvas);
        }
    }
    // //// NAVE //////
    private Grafico nave;// Gráfico de la nave
    private int giroNave; // Incremento de dirección
    private float aceleracionNave; // aumento de velocidad
    private static final int MAX_VELOCIDAD_NAVE=20;
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;
    private static final float FACTOR_DECELERACION_NAVE = 0.96f; // Factor de deceleración

    // //// THREAD Y TIEMPO //////
    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;
    protected void actualizaFisica(){
        long ahora = System.currentTimeMillis();
        if(ultimoProceso + PERIODO_PROCESO > ahora){
            return; //Sali si el periodo de proceso no se ha cumplido
        }
        //Para una ejecuacucion de tiempo real calculamos el factor de movimiento
        double factorNov = (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora; //Para la proxima vez
        // Actualizamos velocidad y dirección de la nave a partir de giroNave y aceleracionNave (según la entrada del jugador)
        nave.setAngulo((int) (nave.getAngulo() + giroNave * factorNov));
        double nIncX = nave.getIncX() + aceleracionNave *
                Math.cos(Math.toRadians(nave.getAngulo())) * factorNov;
        double nIncY = nave.getIncY() + aceleracionNave *
                Math.sin(Math.toRadians(nave.getAngulo())) * factorNov;
        // Actualizamos si el módulo de la velocidad no excede el máximo
        if (Math.hypot(nIncX,nIncY) <= MAX_VELOCIDAD_NAVE){
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }
        // Actualizamos posiciones X e Y
        nave.incrementaPos(factorNov);
        for (Grafico asteroide : asteroides) {
            asteroide.incrementaPos(factorNov);
        }
        // Actualizamos velocidad y dirección de la nave a partir de giroNave y aceleracionNave (según la entrada del jugador)
        nave.setAngulo((int) (nave.getAngulo() + giroNave * factorNov));

        // Calculamos los cambios en x e y directamente desde la velocidad y ángulo
        double cambioX = nave.getIncX() + aceleracionNave *
                Math.cos(Math.toRadians(nave.getAngulo())) * factorNov;
        double cambioY = nave.getIncY() + aceleracionNave *
                Math.sin(Math.toRadians(nave.getAngulo())) * factorNov;

        // Aplicamos la deceleración
        cambioX *= FACTOR_DECELERACION_NAVE;
        cambioY *= FACTOR_DECELERACION_NAVE;

        // Actualizamos si el módulo de la velocidad no excede el máximo
        if (Math.hypot(cambioX, cambioY) <= MAX_VELOCIDAD_NAVE) {
            nave.setIncX(cambioX);
            nave.setIncY(cambioY);
        }

        // Actualizamos posiciones X e Y
        nave.incrementaPos(factorNov);
        for (Grafico asteroide : asteroides) {
            asteroide.incrementaPos(factorNov);
        }
        //Actualizamos posicion de misil1
        if (misilActivo){
            misil.incrementaPos(factorNov);
            tiempoMisil-=factorNov;
            if (tiempoMisil < 0){
                misilActivo = false;
            }else {
                for (int i = 0; i < asteroides.size(); i++)
                    if (misil.verificaColision(asteroides.get(i))){
                        destruyeAsteroide(i);
                        break;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    private boolean hayValorInicial = false;
    private float valorInicial;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valor = event.values[1]; // Valor en el eje y
            if (!hayValorInicial) {
                valorInicial = valor;
                hayValorInicial = true;
            }
            giroNave = (int) ((valor - valorInicial) * 2); // Ajusta el factor según sea necesario
        }
    }

    // //// MISISL //////
    private Grafico misil;
    private  static int PASO_VELOCIDAD_MISIL = 12;
    private boolean misilActivo = false;
    private int tiempoMisil;
    private void destruyeAsteroide(int i){
        synchronized (asteroides){
            asteroides.remove(i);
            misilActivo = false;
            this.postInvalidate();
        }

    }
    private void activaMisil(){
        misil.setCenX(nave.getCenX());
        misil.setCenY(nave.getCenY());
        misil.setAngulo(nave.getAngulo());
        misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo()))*PASO_VELOCIDAD_MISIL);
        misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo()))*PASO_VELOCIDAD_MISIL);
        tiempoMisil = (int)Math.min(this.getWidth() / Math.abs(misil.getIncX()), this.getHeight() / Math.abs(misil.getIncY())) - 2;
        misilActivo = true;
    }
    class ThreadJuego extends Thread{
        @Override
        public void run(){
            while (true){
                actualizaFisica();
            }
        }
    }
    @Override
    public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
        super.onKeyDown(codigoTecla, evento);
        // Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = Math.abs(aceleracionNave) + PASO_ACELERACION_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                giroNave = -PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = +PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                activaMisil();
                break;
            default:
                // Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }
    @Override
    public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
        super.onKeyUp(codigoTecla, evento);

        // Suponemos que vamos a procesar la liberación de la tecla
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = 0;
                break;
            default:
                // Si estamos aquí, no hay liberación de tecla que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }
    private float mX=0, mY=0;
    private boolean disparo=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy < 20 & dx > 20) {
                    giroNave = Math.round((x - mX) / 2);
                    disparo = false;
                } else if (dx < 20 && dy > 20) {
                    aceleracionNave = Math.round((y - mY) / 10);
                    disparo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave = 0;
                aceleracionNave = 0;
                if (disparo) {
                    activaMisil();
                }
                break;
        }
        mX = x;
        mY = y;
        return true;
    }
}