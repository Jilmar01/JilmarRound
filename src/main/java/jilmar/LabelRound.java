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

    // Gradient colors and orientation
    private Color color1 = null;
    private Color color2 = null;
    private Color color3 = null;
    private boolean gradientHorizontal = false; // false = vertical, true = horizontal

    public LabelRound() {
        setOpaque(false);
    }

    // Getters and setters for rounded corners
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int roundTopLeft) { this.roundTopLeft = roundTopLeft; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int roundTopRight) { this.roundTopRight = roundTopRight; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int roundBottomLeft) { this.roundBottomLeft = roundBottomLeft; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int roundBottomRight) { this.roundBottomRight = roundBottomRight; repaint(); }

    // Gradient setters - vertical gradients
    public void setGradientVerticalVertical(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = null;
        this.gradientHorizontal = false;
        repaint();
    }

    public void setGradientVertical(Color c1, Color c2, Color c3) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = c3;
        this.gradientHorizontal = false;
        repaint();
    }

    // Gradient setters - horizontal gradients
    public void setGradientHorizontal(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = null;
        this.gradientHorizontal = true;
        repaint();
    }

    public void setGradientHorizontal(Color c1, Color c2, Color c3) {
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = c3;
        this.gradientHorizontal = true;
        repaint();
    }

    // Clear gradient (use solid background)
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

        // Create rounded shape
        Area area = new Area(createRoundTopLeft());
        if (roundTopRight > 0)  area.intersect(new Area(createRoundTopRight()));
        if (roundBottomLeft > 0)  area.intersect(new Area(createRoundBottomLeft()));
        if (roundBottomRight > 0) area.intersect(new Area(createRoundBottomRight()));

        // Paint gradient or solid background
        if (color1 != null && color2 != null) {
            Paint paint;
            if (color3 == null) {
                // 2-color gradient
                if (gradientHorizontal) {
                    paint = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
                } else {
                    paint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                }
            } else {
                // 3-color gradient
                float[] fractions = {0f, 0.5f, 1f};
                Color[] colors = {color1, color2, color3};
                if (gradientHorizontal) {
                    paint = new LinearGradientPaint(0, 0, getWidth(), 0, fractions, colors);
                } else {
                    paint = new LinearGradientPaint(0, 0, 0, getHeight(), fractions, colors);
                }
            }
            g2.setPaint(paint);
        } else {
            g2.setColor(getBackground());
        }

        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }

    // Rounded corner shape methods (unchanged)
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
