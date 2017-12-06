package graphics;

import handler.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GuiLabel extends GuiComponent {

    private String text;
    public static Font standard = new Font("Times New Java", Font.PLAIN, 15);
    private Font font = GuiLabel.standard;
    private Color colour = Color.BLACK;

    public GuiLabel(Vector pos, Vector size, String text, Color col) {
        super(pos, size);
        this.colour = col;
    }

    public GuiLabel(Vector pos, Vector size, String text, Color col, Font font) {
        super(pos, size);
        this.font = font;
        this.colour = col;
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(colour);
        g.drawString(text, (int)pos.x, (int)pos.y);
        super.render(g);
    }
    
}