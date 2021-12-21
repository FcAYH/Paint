import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

public class PaintMainWindow extends JFrame implements ActionListener {
    private JPanel paintMainPanel;
    private JMenuBar mainMenu;
    private JPanel toolPanel;
    private JPanel drawBackPanel;
    private JButton pencilBtn;
    private JButton bucketBtn;
    private JButton eraserBtn;
    private JButton strawBtn;
    private JButton textBtn;
    private JButton brushesBtn;
    private JButton lineBtn;
    private JButton triangleBtn;
    private JButton arcBtn;
    private JButton ellipticalBtn;
    private JButton pentagonBtn;
    private JButton hexagonBtn;
    private JButton magnifyingBtn;
    private JPanel mainTool;
    private JPanel shapeTools;
    private JButton blackBtn;
    private JButton garyBtn;
    private JButton lightGaryBtn;
    private JButton darkRedBtn;
    private JButton pinkBtn;
    private JButton citrusColorBtn;
    private JButton redBtn;
    private JButton waxyYellowBtn;
    private JButton mistyColorBtn;
    private JButton orangeBtn;
    private JButton lightGreenBtn;
    private JButton blueBtn;
    private JButton lightYellowBtn;
    private JButton ultramarineBtn;
    private JButton lightPurpleBtn;
    private JPanel colorPanel;
    private JButton paletteBtn;
    private JPanel brushPanel;
    private JButton rectangleBtn;
    private JButton selectBtn;
    private JPanel select;
    private JPanel statusPanel;
    private JSpinner sizeSpinner;
    private JPanel sizePanel;
    private JButton nowColorBtn;
    private JButton lastColorBtn;
    private JLabel mousePosLabel;
    private JLabel canvasSizeLabel;
    private JPanel drawPanel;
    private JRadioButton fillBtn;
    private JMenu file, view, edit;
    private JMenuItem openFile, newFile, saveFile;
    private JMenuItem full, half;
    private JMenuItem quash, recover;
    private DrawPanelListener drawPanelListener = new DrawPanelListener();
    private MenuItemActionListener menuItemActionListener = new MenuItemActionListener();

    public PaintMainWindow() {
        super("画图");
        //System.out.println("second");
        setIconImage(new ImageIcon("assets/Logo.png").getImage());
        setSize(1200, 800);
        setLocation(500, 400);
        Initialize();
        add(paintMainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension temp = drawPanel.getSize();
        setCanvasSizeLabel(temp.width, temp.height);
        setMousePosLabel(0, 0);
        setVisible(true);
        brushesBtn.requestFocus();
    }


    private void Initialize() {
        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        file = new JMenu("文件");
        view = new JMenu("视图");
        edit = new JMenu("编辑");

        file.setFont(font);
        view.setFont(font);
        edit.setFont(font);

        newFile = new JMenuItem("新建", new ImageIcon("assets/newFile (Icon).png"));
        openFile = new JMenuItem("打开", new ImageIcon("assets/open (Icon).png"));
        saveFile = new JMenuItem("保存", new ImageIcon("assets/save (Icon).png"));
        newFile.setFont(font);
        openFile.setFont(font);
        saveFile.setFont(font);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        newFile.addActionListener(menuItemActionListener);
        openFile.addActionListener(menuItemActionListener);
        saveFile.addActionListener(menuItemActionListener);
        newFile.setName("newFile");
        openFile.setName("openFile");
        saveFile.setName("saveFile");
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);

        full = new JMenuItem("100%");
        half = new JMenuItem("50%");
        full.addActionListener(menuItemActionListener);
        half.addActionListener(menuItemActionListener);
        full.setName("full");
        half.setName("half");
        view.add(full);
        view.add(half);

        quash = new JMenuItem("撤销", new ImageIcon("assets/quash (Icon).png"));
        recover = new JMenuItem("恢复", new ImageIcon("assets/recover (Icon).png"));
        quash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        recover.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        quash.addActionListener(menuItemActionListener);
        recover.addActionListener(menuItemActionListener);
        quash.setName("quash");
        recover.setName("recover");
        edit.add(quash);
        edit.add(recover);

        mainMenu.add(file);
        mainMenu.add(view);
        mainMenu.add(edit);

        sizeSpinner.setModel(new SpinnerNumberModel(2, 1, 10, 1));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) sizeSpinner.getEditor();
        editor.getTextField().setEditable(false);
        editor.getTextField().setFocusable(false);

        pencilBtn.setIcon(new ImageIcon("assets/pencil (Icon).png"));
        eraserBtn.setIcon(new ImageIcon("assets/eraser (Icon).png"));
        bucketBtn.setIcon(new ImageIcon("assets/bucket (Icon).png"));
        strawBtn.setIcon(new ImageIcon("assets/straw (Icon).png"));
        textBtn.setIcon(new ImageIcon("assets/text (Icon).png"));
        magnifyingBtn.setIcon(new ImageIcon("assets/magnifying (Icon).png"));
        brushesBtn.setIcon(new ImageIcon("assets/paint (Icon).png"));
        lineBtn.setIcon(new ImageIcon("assets/line (Icon).png"));
        arcBtn.setIcon(new ImageIcon("assets/arc (Icon).png"));
        ellipticalBtn.setIcon(new ImageIcon("assets/elliptical (Icon).png"));
        hexagonBtn.setIcon((new ImageIcon("assets/hexagon (Icon).png")));
        pentagonBtn.setIcon(new ImageIcon("assets/pentagon (Icon).png"));
        triangleBtn.setIcon(new ImageIcon("assets/triangle (Icon).png"));
        rectangleBtn.setIcon(new ImageIcon("assets/rectangle (Icon).png"));
        selectBtn.setIcon(new ImageIcon("assets/constituency (Icon).png"));
        paletteBtn.setIcon(new ImageIcon("assets/color (Icon).png"));

        fillBtn.addActionListener(this);

        pencilBtn.addActionListener(this);
        eraserBtn.addActionListener(this);
        bucketBtn.addActionListener(this);
        strawBtn.addActionListener(this);
        textBtn.addActionListener(this);
        magnifyingBtn.addActionListener(this);
        brushesBtn.addActionListener(this);
        lineBtn.addActionListener(this);
        arcBtn.addActionListener(this);
        ellipticalBtn.addActionListener(this);
        hexagonBtn.addActionListener(this);
        pentagonBtn.addActionListener(this);
        triangleBtn.addActionListener(this);
        rectangleBtn.addActionListener(this);
        selectBtn.addActionListener(this);
        paletteBtn.addActionListener(this);

        blackBtn.addActionListener(this);
        garyBtn.addActionListener(this);
        lightGaryBtn.addActionListener(this);
        darkRedBtn.addActionListener(this);
        pinkBtn.addActionListener(this);
        citrusColorBtn.addActionListener(this);
        redBtn.addActionListener(this);
        waxyYellowBtn.addActionListener(this);
        mistyColorBtn.addActionListener(this);
        orangeBtn.addActionListener(this);
        lightGreenBtn.addActionListener(this);
        blueBtn.addActionListener(this);
        lightYellowBtn.addActionListener(this);
        ultramarineBtn.addActionListener(this);
        lightPurpleBtn.addActionListener(this);

        nowColorBtn.addActionListener(this);
        lastColorBtn.addActionListener(this);

        sizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                StartUp.mainWindow.getDrawPanel().setThickness((int) sizeSpinner.getValue());
            }
        });
    }

    public DrawPanelListener getDrawPanel() {
        return (DrawPanelListener) drawPanel;
    }

    public void setCanvasSizeLabel(int x, int y) {
        canvasSizeLabel.setIcon(new ImageIcon("assets/canvas (Icon).png"));
        canvasSizeLabel.setText(x + ", " + y + "px");
    }

    public void setMousePosLabel(int x, int y) {
        if (DrawPanelListener.isInCanvas) {
            mousePosLabel.setIcon(new ImageIcon("assets/cursor (Icon).png"));
            mousePosLabel.setText(x + ", " + y + "px");
        } else {
            mousePosLabel.setIcon(new ImageIcon("assets/cursor (Icon).png"));
            mousePosLabel.setText("");
        }
    }

    private void showColorChooser() {
        Color newColor = JColorChooser.showDialog(this, "拾色器", new Color(121, 203, 96));
        if (newColor != null) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            StartUp.mainWindow.getDrawPanel().setColor(newColor);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        }
    }

    public void createNewDrawPanel() {
        JFrame newFileFrame = new JFrame();
        newFileFrame.setTitle("新建画布");
        newFileFrame.setSize(366, 168);
        newFileFrame.setPreferredSize(new Dimension(366, 168));
        newFileFrame.setLayout(null);
        newFileFrame.setResizable(false);
        newFileFrame.pack();

        newFileFrame.setLocation(500, 400);

        newFileFrame.setVisible(true);

        JTextField width = new JTextField();
        width.setSize(100, 25);
        width.setLocation(100, 25);

        JLabel widthLabel = new JLabel("宽度 (px):");
        widthLabel.setSize(75, 25);
        widthLabel.setLocation(25, 25);

        JLabel heightLabel = new JLabel("高度 (px):");
        heightLabel.setSize(75, 25);
        heightLabel.setLocation(25, 75);

        JTextField height = new JTextField();
        height.setLocation(100, 75);
        height.setSize(100, 25);

        JButton okay = new JButton("新建");
        okay.setSize(75, 25);
        okay.setLocation(250, 25);
        okay.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) {
                                       try {
                                           int newDrawPanelWidth = Integer.parseInt(width.getText());
                                           int newDrawPanelHeight = Integer.parseInt(height.getText());
                                           newFileFrame.dispose();

                                           ((DrawPanelListener) drawPanel).changeDrawPanelSize(newDrawPanelWidth, newDrawPanelHeight);
                                       } catch (NumberFormatException nfe) {
                                           JOptionPane.showMessageDialog(null,
                                                   "输入错误，请输入整数",
                                                   "ERROR",
                                                   JOptionPane.ERROR_MESSAGE);
                                       }
                                   }
                               }
        );

        JButton cancel = new JButton("取消");
        cancel.setSize(75, 25);
        cancel.setLocation(250, 75);
        cancel.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         newFileFrame.dispose();
                                     }
                                 }
        );
        ArrayList<Component> focusOrderList = new ArrayList<Component>();
        focusOrderList.add(heightLabel);
        focusOrderList.add(widthLabel);
        focusOrderList.add(width);
        focusOrderList.add(height);
        focusOrderList.add(okay);
        focusOrderList.add(cancel);

        Vector<Component> vector = new Vector<Component>();
        vector.add(heightLabel);
        vector.add(widthLabel);
        vector.add(width);
        vector.add(height);
        vector.add(okay);
        vector.add(cancel);
        newFileFrame.setFocusTraversalPolicy(new NewFileDialogFocusTraversalPolicy(vector));
        newFileFrame.add(heightLabel);
        newFileFrame.add(widthLabel);
        newFileFrame.add(width);
        newFileFrame.add(height);
        newFileFrame.add(okay);
        newFileFrame.add(cancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == paletteBtn) {
            showColorChooser();
        } else if (e.getSource() == pencilBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.PENCIL);
        } else if (e.getSource() == eraserBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.ERASER);
        } else if (e.getSource() == bucketBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.BUCKET);
        } else if (e.getSource() == textBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.TEXT);
        } else if (e.getSource() == magnifyingBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.MAGNIFYING);
        } else if (e.getSource() == strawBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.STRAW);
        } else if (e.getSource() == brushesBtn) {
            // TODO
            ((DrawPanelListener) drawPanel).setTool(ETools.PENCIL);
        } else if (e.getSource() == lineBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.LINE);
        } else if (e.getSource() == arcBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.ARC);
        } else if (e.getSource() == ellipticalBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.ELLIPTICAL);
        } else if (e.getSource() == hexagonBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.HEXAGON);
        } else if (e.getSource() == pentagonBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.PENTAGON);
        } else if (e.getSource() == triangleBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.TRIANGLE);
        } else if (e.getSource() == rectangleBtn) {
            ((DrawPanelListener) drawPanel).setTool(ETools.RECTANGLE);
        } else if (e.getSource() == blackBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.black);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == garyBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.gary);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == lightGaryBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.lightGary);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == darkRedBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.darkRed);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == pinkBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.pink);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == citrusColorBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.citrusColor);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == redBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.red);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == redBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.red);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == waxyYellowBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.waxyYellow);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == mistyColorBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.mistyColor);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == orangeBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.orange);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == lightGreenBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.lightGreen);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == blueBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.blue);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == lightYellowBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.lightYellow);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == ultramarineBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.ultramarine);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == lightPurpleBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(SpecialColor.lightPurple);
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == lastColorBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(lastColorBtn.getBackground());
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == nowColorBtn) {
            setLastColor(((DrawPanelListener) drawPanel).getCurrentColor());
            ((DrawPanelListener) drawPanel).setColor(nowColorBtn.getBackground());
            setCurrentColor(((DrawPanelListener) drawPanel).getCurrentColor());
        } else if (e.getSource() == fillBtn) {
            if (fillBtn.isSelected()) {
                StartUp.mainWindow.getDrawPanel().setTransparency(false);
            } else {
                StartUp.mainWindow.getDrawPanel().setTransparency(true);
            }
        }
    }

    public void setLastColor(Color lastColor) {
        lastColorBtn.setBackground(lastColor);
    }

    public void setCurrentColor(Color currentColor) {
        nowColorBtn.setBackground(currentColor);
    }

    private void createUIComponents() {
        drawPanel = new DrawPanelListener();
    }

    public JPanel getDrawBackPanel() {
        return drawBackPanel;
    }
}