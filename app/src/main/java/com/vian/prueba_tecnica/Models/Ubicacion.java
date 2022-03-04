package com.vian.prueba_tecnica.Models;

public class Ubicacion {

    private String latitud;
    private String longitud;
    private String fecha_almacenamiento;


    public Ubicacion(){

    }

    public Ubicacion(String latitud, String longitud, String fecha_almacenamiento) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_almacenamiento = fecha_almacenamiento;
    }


    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha_almacenamiento() {
        return fecha_almacenamiento;
    }

    public void setFecha_almacenamiento(String fecha_almacenamiento) {
        this.fecha_almacenamiento = fecha_almacenamiento;
    }
}
