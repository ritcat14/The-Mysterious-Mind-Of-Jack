package handler;

import java.awt.Point;

import core.Decoder;

public class Vector {
    
    public double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector() {
        this.x = 0;
        this.y = 0;
    }
    
    public boolean equals(Vector v2) {
        return (v2.x == x && v2.y == y);
    }
    
    public Point getPoint() {
        return new Point((int)x, (int)y);
    }
    
    public Vector add(Vector v2) {
        this.x += v2.x;
        this.y += v2.y;
        return this;
    }
    
    public Vector multiply(Vector v2) {
    	this.x *= v2.x;
    	this.y *= v2.y;
    	return this;
    }
    
    public Vector tile(double x, double y) {
    	this.x = Decoder.TILE_SIZE * x;
    	this.y = Decoder.TILE_SIZE * y;
    	return this;
    }
    
    public void clear() {
    	this.x = 0;
    	this.y = 0;
    }
    
}
