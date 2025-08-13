package jilmar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;

public class LabelRound extends JLabel {

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    // --- Colores de gradiente (opcionales) ---
    private Color color1 = null;
    private Color color2 = null;
    private Color color3 = null;

    public LabelRound() {
        setOpaque(false);
    }

    // ----------- Getters y setters normales ----------
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int roundTopLeft) { this.roundTopLeft = roundTopLeft; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int roundTopRight) { this.roundTopRight = roundTopRight; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int roundBottomLeft) { this.roundBottomLeft = roundBottomLeft; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int roundBottomRight) { this.roundBottomRight = roundBottomRight; repaint(); }

    // ----------- Métodos nuevos para gradiente ----------
    public void setGradientColors(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = null;
        repaint();
    }

    public void setGradientColors(Color c1, Color c2, Color c3) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = c3;
        repaint();
    }

    public void clearGradient() {
        this.color1 = null;
        this.color2 = null;
        this.color3 = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear la forma redondeada combinando esquinas
        Area area = new Area(createRoundTopLeft());
        if (roundTopRight > 0)  area.intersect(new Area(createRoundTopRight()));
        if (roundBottomLeft > 0)  area.intersect(new Area(createRoundBottomLeft()));
        if (roundBottomRight > 0) area.intersect(new Area(createRoundBottomRight()));

        // Pintar fondo sólido o gradiente
        if (color1 != null && color2 != null) {
            Paint paint;
            if (color3 == null) {
                // Gradiente de 2 colores
                paint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            } else {
                // Gradiente de 3 colores
                float[] fractions = {0f, 0.5f, 1f};
                Color[] colors = {color1, color2, color3};
                paint = new LinearGradientPaint(0, 0, 0, getHeight(), fractions, colors);
            }
            g2.setPaint(paint);
        } else {
            g2.setColor(getBackground()); // comportamiento original
        }

        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }

    // ----------- Métodos privados para crear esquinas -----------
    private Shape createRoundTopLeft() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundTopLeft);
        int roundY = Math.min(height, roundTopLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundTopRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(height, roundTopRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(height, roundBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }
}
