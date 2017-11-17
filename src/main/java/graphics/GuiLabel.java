package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GuiLabel extends GuiComponent {

    private String text;
    private Font font = new Font("Times New Java", Font.PLAIN, 15);
    private Color colour = Color.BLACK;

    public GuiLabel(int x, int y, int width, int height, String text, Color col) {
        super(x, y, width, height);
        this.colour = col;
    }

    public GuiLabel(int x, int y, int width, int height, String text, Color col, Font font) {
        super(x, y, width, height);
        this.font = font;
        this.colour = col;
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
        g.drawString(text, x, y);
        super.render(g);
    }
    
}
