package models;

import java.util.Date;

/**
 * Created by JULIAN on 07/12/2016.
 */

public class SubProceso {

    String id;
    Date fecha;
    String nombre;
    String idProceso;
    String stateShow;
    int disponibilidad;
    int numeroenProceso;
    int estado;

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public String getStateShow() {
        return stateShow;
    }

    public void setStateShow(String stateShow) {
        this.stateShow = stateShow;
    }
    

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public int getNumeroenProceso() {
        return numeroenProceso;
    }

    public void setNumeroenProceso(int numeroenProceso) {
        this.numeroenProceso = numeroenProceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
