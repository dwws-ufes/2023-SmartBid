package br.com.ufes.labes.smartbid.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class HelloController implements Serializable {
    private static final String[] NAMES =
            {"JButler", "Jakarta EE 9", "PrimeFaces 10", "AdminFaces"};

    private int idx;

    private int count;

    public String getName() {
        count++;
        idx = (idx + 1) % NAMES.length;
        return NAMES[idx];
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }
}
