package com.example.asteroides;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Grafico {
    private Drawable drawable;
    private int ancho, alto;
    private int radioColision;
    private int xAnterior, yAnterior;
    private int radioInval;
    private View view;

    private double cenX, cenY;  // Modificado a double
    private double incX, incY;  // Modificado a double
    private double angulo, rotacion;

    public Grafico(View view, Drawable drawable) {
        this.view = view;
        this.drawable = drawable;
        ancho = drawable.getIntrinsicWidth();
        alto = drawable.getIntrinsicHeight();
        radioColision = (alto + ancho) / 4;
        radioInval = (int) Math.hypot(ancho / 2, alto / 2);
    }

    public void dibujaGrafico(Canvas canvas) {
        int x = (int) (cenX - ancho / 2);
        int y = (int) (cenY - alto / 2);
        drawable.setBounds(x, y, x + ancho, y + alto);
        canvas.save();
        canvas.rotate((float) angulo, (float) cenX, (float) cenY);
        drawable.draw(canvas);
        canvas.restore();
        xAnterior = (int) cenX;
        yAnterior = (int) cenY;
    }

    public void incrementaPos(double factor) {
        cenX += incX * factor;
        cenY += incY * factor;
        angulo += rotacion * factor;

        if (cenX < 0) cenX = view.getWidth();
        if (cenX > view.getWidth()) cenX = 0;
        if (cenY < 0) cenY = view.getHeight();
        if (cenY > view.getHeight()) cenY = 0;

        view.postInvalidate((int) (cenX - radioInval), (int) (cenY - radioInval),
                (int) (cenX + radioInval), (int) (cenY + radioInval));
        view.postInvalidate(xAnterior - radioInval, yAnterior - radioInval,
                xAnterior + radioInval, yAnterior + radioInval);
    }

    public double distancia(Grafico g) {
        return Math.hypot(cenX - g.cenX, cenY - g.cenY);
    }

    public boolean verificaColision(Grafico g) {
        return (distancia(g) < (radioColision + g.radioColision));
    }

    public void setIncX(double incX) {
        this.incX = incX;
    }

    public void setIncY(double incY) {
        this.incY = incY;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public void setRotacion(double rotacion) {
        this.rotacion = rotacion;
    }

    public void setCenX(double cenX) {
        this.cenX = cenX;
    }

    public void setCenY(double cenY) {
        this.cenY = cenY;
    }

    public double getAngulo() {
        return angulo;
    }

    public double getIncX() {
        return incX;
    }

    public double getIncY() {
        return incY;
    }

    public double getCenX(){ return cenX; }
    public double getCenY(){ return cenY; }
}