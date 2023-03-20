package hu.javaoktatas.peldak.todolist.model;

import java.io.Serializable;
import java.util.Date;

public class ToDoItem implements Serializable {

    private int id;
    private String cim;
    private int teljesitve;
    private int ev;
    private int honap;
    private int nap;
    private int fontossag;

    public ToDoItem() {
        this.id = -1;
    }

    public ToDoItem(String cim, int ev, int honap, int nap, int fontossag) {
        this.id = -1;
        this.cim = cim;
        this.teljesitve = 0;
        this.ev = ev;
        this.honap = honap;
        this.nap = nap;
        this.fontossag = fontossag;
    }

    public ToDoItem(int id, String cim, int teljesitve, int ev, int honap, int nap, int fontossag) {
        this.id = id;
        this.cim = cim;
        this.teljesitve = teljesitve;
        this.ev = ev;
        this.honap = honap;
        this.nap = nap;
        this.fontossag = fontossag;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "id=" + id +
                ", cim='" + cim + '\'' +
                ", teljesitve=" + teljesitve +
                ", ev=" + ev +
                ", honap=" + honap +
                ", nap=" + nap +
                ", fontossag=" + fontossag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public int getTeljesitve() {
        return teljesitve;
    }

    public void setTeljesitve(int teljesitve) {
        this.teljesitve = teljesitve;
    }

    public int getEv() {
        return ev;
    }

    public void setEv(int ev) {
        this.ev = ev;
    }

    public int getHonap() {
        return honap;
    }

    public void setHonap(int honap) {
        this.honap = honap;
    }

    public int getNap() {
        return nap;
    }

    public void setNap(int nap) {
        this.nap = nap;
    }

    public int getFontossag() {
        return fontossag;
    }

    public void setFontossag(int fontossag) {
        this.fontossag = fontossag;
    }
}
