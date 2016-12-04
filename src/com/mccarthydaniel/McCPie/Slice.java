package com.mccarthydaniel.McCPie;

import java.awt.Color;

/**
 * A pie slice that may be drawn on {@link McCPie} pie chart.
 * @author Daniel McCarthy
 */
public class Slice {

    private double value;
    private String name;
    private Color colour;

    /**
     * Initialises the slice with a value of 0 a name of "" and a colour thats black.
     */
    public Slice() {
        this(0, "", Color.BLACK);
    }

    /**
     * Initialises the slice with the value, name and colour passed to it.
     * @param value The value of the slice
     * @param name The name of the slice
     * @param colour The colour of the slice
     */
    public Slice(double value, String name, Color colour) {
        this.value = value;
        this.name = name;
        this.colour = colour;
    }

    /**
     * Sets the value of the slice
     * @param value the value of the slice
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Sets the name of the slice
     * @param name the name of the slice
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the colour of the slice
     * @param colour the colour of the slice
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }
    
    /**
     * Returns the value of the slice
     * @return the value of the slice
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Returns the name of the slice
     * @return the name of the slice
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the colour of the slice
     * @return the colour of the slice
     */
    public Color getColour() {
        return this.colour;
    }
}
