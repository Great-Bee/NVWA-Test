package com.xiwa.test.bean;

import com.xiwa.nvwa.bean.Field;
import com.xiwa.nvwa.bean.OI;

/**
 * Test OI
 *
 * Created by root on 12/16/14.
 */
public class TestOI {
    private OI oi;
    private Field intFie;
    private Field doubleFie;
    private Field varchar;
    private Field text;
    private Field date;

    public OI getOi() {
        return oi;
    }

    public void setOi(OI oi) {
        this.oi = oi;
    }

    public Field getIntFie() {
        return intFie;
    }

    public void setIntFie(Field intFie) {
        this.intFie = intFie;
    }

    public Field getDoubleFie() {
        return doubleFie;
    }

    public void setDoubleFie(Field doubleFie) {
        this.doubleFie = doubleFie;
    }

    public Field getVarchar() {
        return varchar;
    }

    public void setVarchar(Field varchar) {
        this.varchar = varchar;
    }

    public Field getText() {
        return text;
    }

    public void setText(Field text) {
        this.text = text;
    }

    public Field getDate() {
        return date;
    }

    public void setDate(Field date) {
        this.date = date;
    }
}
