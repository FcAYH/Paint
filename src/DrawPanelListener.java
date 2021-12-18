import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DrawPanelListener extends JPanel implements MouseListener, MouseMotionListener {

    private int x1, y1, x2, y2;
    private boolean dragged = false;
    private Color currentColor;
    private Color lastColor;
    private boolean transparent;
    private int grouped;
    private BasicStroke stroke = new BasicStroke((float) 2);

    private Stack<Shape> shapes;
    private Stack<Shape> removed;
    private Stack<Shape> preview;

    private BufferedImage canvas;
    private Graphics2D graphics2D;
    private ETools activeTool;


    public static boolean isInCanvas = false;

    public DrawPanelListener(int x, int y) {
        setSize(x, y);
        Dimension temp = new Dimension(x, y);
        setMaximumSize(temp);
        setMinimumSize(temp);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        setBackground(Color.white);

        addMouseListener(this);
        addMouseMotionListener(this);
        requestFocus();
        activeTool = ETools.PENCIL;
        currentColor = Color.BLACK;
        lastColor = Color.WHITE;

        this.shapes = new Stack<Shape>();
        this.removed = new Stack<Shape>();
        this.grouped = 1;
        this.preview = new Stack<Shape>();
        this.transparent = true;
    }

    public DrawPanelListener() {
        setSize(1100, 550);
        Dimension temp = new Dimension(1100, 550);
        setMaximumSize(temp);
        setMinimumSize(temp);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        setBackground(Color.white);

        addMouseListener(this);
        addMouseMotionListener(this);
        requestFocus();
        activeTool = ETools.PENCIL;
        System.out.println("create");
        currentColor = Color.BLACK;
        lastColor = Color.WHITE;
        this.shapes = new Stack<Shape>();
        this.removed = new Stack<Shape>();
        this.grouped = 1;
        this.preview = new Stack<Shape>();
        this.transparent = true;
    }

    public void changeDrawPanelSize(int width, int height) {
        shapes.clear();
        removed.clear();
        preview.clear();

        Dimension temp = new Dimension(width, height);
        setSize(temp);
        setMaximumSize(temp);
        setMinimumSize(temp);

        repaint();
        StartUp.mainWindow.setCanvasSizeLabel(width, height);
    }

    public void paintComponent(Graphics g) {
        System.out.println(activeTool.toString() + "pc");
        Dimension dimension = StartUp.mainWindow.getDrawPanel().getSize();
        if (canvas == null) {
            canvas = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
            graphics2D = canvas.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(canvas, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape s : shapes) {
            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());

            if (s.getShape() == ETools.LINE) {
                g2.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
            } else if (s.getShape() == ETools.RECTANGLE) {

                g2.drawRect(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillRect(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            } else if (s.getShape() == ETools.ROUND) {
                g2.drawOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            } else if (s.getShape() == ETools.TEXT) {
                g2.setFont(s.getFont());
                g2.drawString(s.getMessage(), s.getX1(), s.getY1());
            }
        }
        if (preview.size() > 0) {
            Shape s = preview.peek();
            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());
            if (s.getShape() == ETools.LINE) {
                g2.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());

            } else if (s.getShape() == ETools.RECTANGLE) {

                g2.drawRect(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillRect(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            } else if (s.getShape() == ETools.ROUND) {
                g2.drawOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            }
        }

    }

    public void setTool(ETools tool) {
        this.activeTool = tool;
        System.out.println(this.toString());
        System.out.println(activeTool.toString() + "st");
    }

    public void setImage(BufferedImage image) {
        graphics2D.dispose();
        this.setInkPanel(image.getWidth(), image.getHeight());
        canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void setInkPanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.setSize(width - 3, height - 3);
        this.setPreferredSize(new Dimension(width - 3, height - 3));
        clear();

    }

    public void clear() {
        Dimension dimension = StartUp.mainWindow.getDrawPanel().getSize();
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, dimension.width, dimension.height);
        shapes.removeAllElements();
        removed.removeAllElements();
        StartUp.mainWindow.getDrawPanel().repaint();
        graphics2D.setColor(currentColor);
    }


    public void setColor(Color c) {
        lastColor = currentColor;
        currentColor = c;
        graphics2D.setColor(c);
    }

    public void setThickness(float f) {
        stroke = new BasicStroke(f);
        graphics2D.setStroke(stroke);
    }

    public void setTransparency(Boolean b) {
        this.transparent = b;
    }

    public void floodFill(Point2D.Double point, Color fillColor) {
        Color targetColor = new Color(canvas.getRGB((int) point.getX(), (int) point.getY()));
        Queue<Point2D.Double> queue = new LinkedList<Point2D.Double>();
        queue.add(point);
        if (!targetColor.equals(fillColor)) ;
        while (!queue.isEmpty()) {
            Point2D.Double p = queue.remove();

            if ((int) p.getX() >= 0 && (int) p.getX() < canvas.getWidth() &&
                    (int) p.getY() >= 0 && (int) p.getY() < canvas.getHeight())
                if (canvas.getRGB((int) p.getX(), (int) p.getY()) == targetColor.getRGB()) {
                    canvas.setRGB((int) p.getX(), (int) p.getY(), fillColor.getRGB());
                    queue.add(new Point2D.Double(p.getX() - 1, p.getY()));
                    queue.add(new Point2D.Double(p.getX() + 1, p.getY()));
                    queue.add(new Point2D.Double(p.getX(), p.getY() - 1));
                    queue.add(new Point2D.Double(p.getX(), p.getY() + 1));
                }
        }
    }

    public void quash() {
        if (shapes.size() > 0 && shapes.peek().group == 0) {
            removed.push(shapes.pop());
            repaint();
        } else if (shapes.size() > 0 && shapes.peek().group != 0) {

            Shape lastRemoved = shapes.pop();
            removed.push(lastRemoved);

            while (shapes.isEmpty() == false && shapes.peek().group == lastRemoved.group) {
                removed.push(shapes.pop());
                repaint();

            }
        }
    }

    public void recover() {
        if (removed.size() > 0 && removed.peek().group == 0) {
            shapes.push(removed.pop());
            repaint();
        } else if (removed.size() > 0 && removed.peek().group != 0) {

            Shape lastRemoved = removed.pop();
            shapes.push(lastRemoved);

            while (removed.isEmpty() == false && removed.peek().group == lastRemoved.group) {
                shapes.push(removed.pop());
                repaint();

            }
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (preview.size() != 0) {
            shapes.push(preview.pop());
            preview.clear();
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isInCanvas = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isInCanvas = false;
        StartUp.mainWindow.setMousePosLabel(0, 0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        StartUp.mainWindow.setMousePosLabel(e.getX(), e.getY());

        Color primary = currentColor;
        Color secondary = lastColor;
        if (SwingUtilities.isRightMouseButton(e)) {
            primary = secondary;
            secondary = currentColor;
        }
        x2 = e.getX();
        y2 = e.getY();
        this.dragged = true;
        System.out.println(activeTool.toString());
        if (activeTool == ETools.ERASER) {
            shapes.push(new Shape(x1, y1, x2, y2, Color.white, stroke, ETools.LINE, grouped));
            StartUp.mainWindow.getDrawPanel().repaint();
            x1 = x2;
            y1 = y2;
        } else if (activeTool == ETools.PENCIL) {
            shapes.push(new Shape(x1, y1, x2, y2, primary, stroke, ETools.LINE, grouped));
            StartUp.mainWindow.getDrawPanel().repaint();
            x1 = x2;
            y1 = y2;
        } else if (activeTool == ETools.LINE) {
            preview.push(new Shape(x1, y1, x2, y2, primary, stroke, ETools.LINE, secondary, transparent));
            StartUp.mainWindow.getDrawPanel().repaint();
        } else if (activeTool == ETools.RECTANGLE) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.RECTANGLE, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.RECTANGLE, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.RECTANGLE, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.RECTANGLE, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        } else if (activeTool == ETools.ROUND) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.ROUND, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.ROUND, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.ROUND, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.ROUND, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        StartUp.mainWindow.setMousePosLabel(e.getX(), e.getY());
    }
}
