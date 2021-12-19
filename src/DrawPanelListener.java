import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
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
    private TextDialog textDialog;

    private ArcStatus drawStatus = ArcStatus.NOT_DRAWING;
    private ArcStatus direction = ArcStatus.NO_DIRECTION;
    private Dimension center, startPoint;
    private int radius;
    private Vector3 benchmark;
    private Rectangle rectangle;

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
        textDialog = new TextDialog(StartUp.mainWindow);

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
        //System.out.println("create");
        currentColor = Color.BLACK;
        lastColor = Color.WHITE;
        textDialog = new TextDialog(StartUp.mainWindow);

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
        //System.out.println(activeTool.toString() + "pc");
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
            } else if (s.getShape() == ETools.ELLIPTICAL) {
                g2.drawOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            } else if (s.getShape() == ETools.PENTAGON) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 5);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 5);
                }
            } else if (s.getShape() == ETools.HEXAGON) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 6);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 6);
                }
            } else if (s.getShape() == ETools.TRIANGLE) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 3);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 3);
                }
            } else if (s.getShape() == ETools.ARC) {
                g2.drawArc(s.getRectangle().x, s.getRectangle().y, s.getRectangle().width, s.getRectangle().height,
                        s.getStartAngle(), s.getDrawAngle());
                if (s.transparent == false) {
                    g2.fillArc(s.getRectangle().x, s.getRectangle().y, s.getRectangle().width, s.getRectangle().height,
                            s.getStartAngle(), s.getDrawAngle());
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
            } else if (s.getShape() == ETools.ELLIPTICAL) {
                g2.drawOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillOval(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                }
            } else if (s.getShape() == ETools.PENTAGON) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 5);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 5);
                }
            } else if (s.getShape() == ETools.HEXAGON) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 6);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 6);
                }
            } else if (s.getShape() == ETools.TRIANGLE) {
                g2.drawPolygon(s.getPointsX(), s.getPointsY(), 3);
                if (s.transparent == false) {
                    g2.setColor(s.getFillColor());
                    g2.fillPolygon(s.getPointsX(), s.getPointsY(), 3);
                }
            } else if (s.getShape() == ETools.ARC) {
                g2.drawArc(s.getRectangle().x, s.getRectangle().y, s.getRectangle().width, s.getRectangle().height,
                        s.getStartAngle(), s.getDrawAngle());
                if (s.transparent == false) {
                    g2.fillArc(s.getRectangle().x, s.getRectangle().y, s.getRectangle().width, s.getRectangle().height,
                            s.getStartAngle(), s.getDrawAngle());
                }
            }
        }

    }

    public void setTool(ETools tool) {
        this.activeTool = tool;
        //System.out.println(this.toString());
        //System.out.println(activeTool.toString() + "st");
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
//        Color targetColor = new Color(canvas.getRGB((int) point.getX(), (int) point.getY()));
//        Queue<Point2D.Double> queue = new LinkedList<Point2D.Double>();
//        queue.add(point);
//        if (!targetColor.equals(fillColor)) ;
//        while (!queue.isEmpty()) {
//            Point2D.Double p = queue.remove();
//
//            if ((int) p.getX() >= 0 && (int) p.getX() < canvas.getWidth() &&
//                    (int) p.getY() >= 0 && (int) p.getY() < canvas.getHeight())
//                if (canvas.getRGB((int) p.getX(), (int) p.getY()) == targetColor.getRGB()) {
//                    canvas.setRGB((int) p.getX(), (int) p.getY(), fillColor.getRGB());
//                    queue.add(new Point2D.Double(p.getX() - 1, p.getY()));
//                    queue.add(new Point2D.Double(p.getX() + 1, p.getY()));
//                    queue.add(new Point2D.Double(p.getX(), p.getY() - 1));
//                    queue.add(new Point2D.Double(p.getX(), p.getY() + 1));
//                }
//        }
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

    private int calcDistance(Dimension a, Dimension b) {
        return (int) Math.sqrt((a.width - b.width) * (a.width - b.width)
                + (a.height - b.height) * (a.height - b.height));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (activeTool == ETools.ARC) {
            if (drawStatus == ArcStatus.NOT_DRAWING) {
                center = new Dimension(e.getX(), e.getY());
                drawStatus = ArcStatus.DEFINED_CENTER;
            } else if (drawStatus == ArcStatus.DEFINED_CENTER) {
                startPoint = new Dimension(e.getX(), e.getY());
                radius = calcDistance(startPoint, center);
                benchmark = new Vector3(startPoint.width - center.width,
                        -startPoint.height + center.height, 0);
                drawStatus = ArcStatus.DEFINED_R;
                rectangle = new Rectangle(center.width - radius, center.height - radius,
                        2 * radius, 2 * radius);
            } else if (drawStatus == ArcStatus.DEFINED_R) {
                if (preview.size() != 0) {
                    shapes.push(preview.pop());
                    preview.clear();
                }
                drawStatus = ArcStatus.NOT_DRAWING;
                direction = ArcStatus.NO_DIRECTION;
            }
        } else if (activeTool == ETools.STRAW) {
            try {
                int x = e.getX(), y = e.getY();
                x += StartUp.mainWindow.getLocation().x;
                y += StartUp.mainWindow.getLocation().y;
                x += StartUp.mainWindow.getDrawBackPanel().getLocation().x;
                y += StartUp.mainWindow.getDrawBackPanel().getLocation().y;
                x += getLocation().x;
                y += getLocation().y;
                // TODO:暂时不清楚为啥get到的坐标还是和实际坐标差了8, 31，猜测是标题栏的长宽没被算进去
                x += 8;
                y += 31;
                //
                System.out.println("isHere");
                Robot robot = new Robot();
                System.out.println(x + " " + y);
                Color color = robot.getPixelColor(x, y);
                System.out.println(color.toString());
                StartUp.mainWindow.setLastColor(getCurrentColor());
                setColor(color);
                StartUp.mainWindow.setCurrentColor(getCurrentColor());
            } catch (AWTException ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (preview.size() != 0 && activeTool != ETools.ARC) {
            shapes.push(preview.pop());
            preview.clear();
        } else if (activeTool == ETools.TEXT) {
            int result = textDialog.showCustomDialog(StartUp.mainWindow);
            if (result == TextDialog.APPLY_OPTION) {
                shapes.push(new Shape(x1, y1, textDialog.getInputSize(), textDialog.getFont(),
                        currentColor, stroke, ETools.TEXT, textDialog.getText()));
            }
        } else if (activeTool == ETools.BUCKET) {
            floodFill(new Point2D.Double(x1, y1), currentColor);
        }
        if (dragged) {
            grouped++;
            dragged = false;
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
        //System.out.println(grouped);
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
        //System.out.println(activeTool.toString());
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
        } else if (activeTool == ETools.ELLIPTICAL) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.ELLIPTICAL, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.ELLIPTICAL, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.ELLIPTICAL, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.ELLIPTICAL, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        } else if (activeTool == ETools.PENTAGON) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.PENTAGON, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.PENTAGON, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.PENTAGON, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.PENTAGON, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        } else if (activeTool == ETools.HEXAGON) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.HEXAGON, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.HEXAGON, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.HEXAGON, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.HEXAGON, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        } else if (activeTool == ETools.TRIANGLE) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, primary, stroke, ETools.TRIANGLE, secondary, transparent));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, primary, stroke, ETools.TRIANGLE, secondary, transparent));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, primary, stroke, ETools.TRIANGLE, secondary, transparent));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, primary, stroke, ETools.TRIANGLE, secondary, transparent));
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        StartUp.mainWindow.setMousePosLabel(e.getX(), e.getY());

        if (activeTool == ETools.ARC) {
            Color primary = currentColor;
            Color secondary = lastColor;
            x2 = e.getX();
            y2 = e.getY();
            if (drawStatus == ArcStatus.NOT_DRAWING) {
                return;
            } else if (drawStatus == ArcStatus.DEFINED_CENTER) {
                preview.push(new Shape(x1, y1, x2, y2, primary, stroke, ETools.LINE, secondary, transparent));
                StartUp.mainWindow.getDrawPanel().repaint();
            } else if (drawStatus == ArcStatus.DEFINED_R) {
                //System.out.println(direction);
                if (direction == ArcStatus.NO_DIRECTION) {
                    Vector3 temp = new Vector3(x2 - center.width, -y2 + center.height, 0);
                    direction = benchmark.calcDirection(temp);
                } else if (direction == ArcStatus.LEFT) {
                    //System.out.println(benchmark.print());
                    int startAngle = benchmark.normalization().calcAngle(new Vector3(1, 0, 0));
                    Vector3 temp = new Vector3(x2 - center.width, -y2 + center.height, 0);
                    int drawAngle = temp.normalization().calcAngle(benchmark.normalization());
                    //System.out.println(startAngle + " " + drawAngle);
                    //System.out.println(temp.calcDirection(benchmark));
                    preview.push(new Shape(rectangle, primary, stroke, ETools.ARC, secondary, transparent, startAngle, drawAngle));
                } else if (direction == ArcStatus.RIGHT) {
                    int startAngle = benchmark.normalization().calcAngle(new Vector3(1, 0, 0));
                    Vector3 temp = new Vector3(x2 - center.width, -y2 + center.height, 0);
                    int drawAngle = -(360 - temp.normalization().calcAngle(benchmark.normalization()));
                    preview.push(new Shape(rectangle, primary, stroke, ETools.ARC, secondary, transparent, startAngle, drawAngle));
                }
            }
            StartUp.mainWindow.getDrawPanel().repaint();
        }
    }
}
