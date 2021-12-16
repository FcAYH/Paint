import javax.swing.*;
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
    private JButton roundBtn;
    private JButton ellipticalBtn;
    private JButton pentagonBtn;
    private JButton hexagonBtn;
    private JButton magnifyingBtn;
    private JPanel mainTool;
    private JPanel shapeTools;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
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
    private JMenu file, view, edit;
    private JMenuItem openFile, newFile, saveFile;
    private JMenuItem full, half;
    private JMenuItem quash, recover;
    private DrawPanelListener drawPanelListener = new DrawPanelListener();
    private MenuItemActionListener menuItemActionListener = new MenuItemActionListener();

    public PaintMainWindow() {
        super("画图");
        setIconImage(new ImageIcon("assets/Logo.png").getImage());
        setSize(1200, 800);
        setLocation(500, 400);
        Initialize();
        add(paintMainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        drawPanel.setSize(800, 600);
        Dimension temp = new Dimension(1100, 550);
        drawPanel.setMaximumSize(temp);
        drawPanel.setMinimumSize(temp);
        drawPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        drawPanel.setBackground(Color.white);
        setCanvasSizeLabel(1100, 550);
        setMousePosLabel(0, 0);
        drawPanel.addMouseListener(drawPanelListener);
        drawPanel.addMouseMotionListener(drawPanelListener);
        //drawBackPanel.add(drawPanel);
        drawPanel.requestFocus();

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
        roundBtn.setIcon(new ImageIcon("assets/round (Icon).png"));
        ellipticalBtn.setIcon(new ImageIcon("assets/elliptical (Icon).png"));
        hexagonBtn.setIcon((new ImageIcon("assets/hexagon (Icon).png")));
        pentagonBtn.setIcon(new ImageIcon("assets/pentagon (Icon).png"));
        triangleBtn.setIcon(new ImageIcon("assets/triangle (Icon).png"));
        rectangleBtn.setIcon(new ImageIcon("assets/rectangle (Icon).png"));
        selectBtn.setIcon(new ImageIcon("assets/constituency (Icon).png"));
        paletteBtn.setIcon(new ImageIcon("assets/color (Icon).png"));

        paletteBtn.addActionListener(this);
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
        JColorChooser.showDialog(this, "拾色器", new Color(121, 203, 96));
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

                                           changeDrawPanelSize(newDrawPanelWidth, newDrawPanelHeight);
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

    private void changeDrawPanelSize(int width, int height) {
        Dimension temp = new Dimension(width, height);
        drawPanel.setSize(temp);
        drawPanel.setMaximumSize(temp);
        drawPanel.setMinimumSize(temp);

        drawPanel.repaint();
        setCanvasSizeLabel(width, height);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newFile) {
            System.out.println("dsd");
        } else if (e.getSource() == paletteBtn) {
            showColorChooser();
        }
    }
}
