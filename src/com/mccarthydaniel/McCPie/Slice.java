package com.mccarthydaniel.McCPie;

import java.awt.Color;

/**
 *
 * @author Daniel McCarthy
 */
public class Slice {

    private double value;
    private String name;
    private Color colour;

    public Slice() {
        this(0, "", Color.BLACK);
    }

    public Slice(double value, String name, Color colour) {
        this.value = value;
        this.name = name;
        this.colour = colour;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
    
    public double getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
    
    public Color getColour() {
        return this.colour;
    }
}
