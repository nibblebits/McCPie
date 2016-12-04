
package com.mccarthydaniel.McCPie.exceptions;

/**
 * Thrown in situations where no more colours are available
 * @author dansb
 */
public class NoColoursAvailableException extends Exception {
    
    public NoColoursAvailableException(String message) {
        super(message);
    }
}
