package handler;

import java.awt.Point;

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
    
    public void add(Vector v2) {
        this.x += v2.x;
        this.y += v2.y;
    }
    
    public void clear() {
    	this.x = 0;
    	this.y = 0;
    }
    
}
