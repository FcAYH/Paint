import java.awt.*;

public class Shape {
    public boolean transparent;
    public int group = 0;

    private int x1, x2;
    private int y1, y2;

    private Color color;
    private Color fillColor;
    private BasicStroke stroke;
    private String message;

    // 多边形用
    private int[] pointsX;
    private int[] pointsY;

    // 圆弧用
    private Rectangle rectangle;
    private int startAngle;
    private int drawAngle;

    private ETools shape;
    private Font font;

    public Shape(Rectangle rect, Color color, BasicStroke stroke, ETools shape, Color fill, boolean transparent, int startAngle, int drawAngle) {
        rectangle = rect;
        this.color = color;
        this.stroke = stroke;
        this.shape = shape;
        this.fillColor = fill;
        this.transparent = transparent;
        this.startAngle = startAngle;
        this.drawAngle = drawAngle;
    }

    public Shape(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke, ETools shape, Color fill, boolean transparent) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
        this.shape = shape;
        this.group = 0;
        this.fillColor = fill;
        this.transparent = transparent;
        if (shape == ETools.PENTAGON) {
            setPentagonPoints();
        }
        if (shape == ETools.HEXAGON) {
            setHexagonPoints();
        }
        if (shape == ETools.TRIANGLE) {
            setTrianglePoints();
        }
    }

    public Shape(int x1, int y1, int fontSize, Font font, Color color, BasicStroke stroke, ETools shape, String message) {
        this.x1 = x1;
        this.y1 = y1;
        this.y2 = 0;
        this.font = font;
        this.x2 = fontSize;
        this.color = color;
        this.stroke = stroke;
        this.shape = shape;
        this.group = 0;
        this.message = message;
    }

    public Shape(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke, ETools shape, int group) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
        this.shape = shape;
        this.group = group;
    }

    public ETools getShape() {
        return this.shape;
    }

    public String getMessage() {
        return this.message;
    }

    public Font getFont() {
        return this.font;
    }

    public int getX1() {
        return this.x1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY1() {
        return this.y1;
    }

    public int getY2() {
        return this.y2;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public BasicStroke getStroke() {
        return this.stroke;
    }

    public boolean getTransparency() {
        return this.transparent;
    }

    public int getGroup() {
        return this.group;
    }

    public int[] getPointsX() {
        return pointsX;
    }

    public int[] getPointsY() {
        return pointsY;
    }

    private void setHexagonPoints() {
        pointsX = new int[]{
                x1 + x2 / 4,
                x1,
                x1 + x2 / 4,
                x1 + x2 * 3 / 4,
                x1 + x2,
                x1 + x2 * 3 / 4
        };
        pointsY = new int[]{
                y1,
                y1 + y2 / 2,
                y1 + y2,
                y1 + y2,
                y1 + y2 / 2,
                y1
        };
    }

    private void setPentagonPoints() {
        pointsX = new int[]{
                x1 + x2 / 2,
                x1,
                x1 + (int) (x2 / 4.3),
                x1 + (int) (x2 * 3.3 / 4.3),
                x1 + x2
        };
        pointsY = new int[]{
                y1,
                y1 + (int) (((Math.sqrt(3) - 0.9) / 2) * y2),
                y1 + y2,
                y1 + y2,
                y1 + (int) (((Math.sqrt(3) - 0.9) / 2) * y2)
        };
    }

    private void setTrianglePoints() {
        pointsX = new int[]{
                x1 + x2 / 2,
                x1,
                x1 + x2
        };
        pointsY = new int[]{
                y1,
                y1 + y2,
                y1 + y2
        };
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getDrawAngle() {
        return drawAngle;
    }
}
