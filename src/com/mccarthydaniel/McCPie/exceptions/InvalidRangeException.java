package com.mccarthydaniel.McCPie.exceptions;

/**
 * Thrown in situations where an invalid range has been provided.
 * @author dansb
 */
public class InvalidRangeException extends Exception {

    public InvalidRangeException(String message) {
        super(message);
    }
    
}
