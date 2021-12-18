import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextDialog extends JDialog implements ActionListener, ChangeListener {
    static final long serialVersionUID = 42L;

    public static final int APPLY_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    int userResponse;

    JTextField example;
    JTextField input;
    JButton ok;
    JButton cancel;
    JComboBox fonts;
    JComboBox sizes;
    Color Color;

    Font font;


    int inputSize;
    Object inputFont;

    String inputText;
    Font mainFont;

    TextDialog(Frame owner) {
        super(owner, "文本框", true);
        this.setResizable(false);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("assets/Logo.png").getImage());
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontList = ge.getAvailableFontFamilyNames();
        String[] fontSize = {"30", "32", "36", "38", "40", "42", "44", "46", "48", "50", "52", "53", "54", "55"};

        sizes = new JComboBox(fontSize);
        fonts = new JComboBox(fontList);

        example = new JTextField("Hello World!");
        example.setHorizontalAlignment(SwingConstants.CENTER);
        example.setFont(new Font("Consolas", Font.PLAIN, 28));
        example.setEditable(false);
        example.setPreferredSize(new Dimension(200, 50));

        ok = new JButton("确定");
        cancel = new JButton("取消");
        ok.setPreferredSize(cancel.getPreferredSize());

        input = new JTextField("Hello World!");
        input.setPreferredSize(new Dimension(200, 50));
        input.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }


            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }


            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        ok.addActionListener(this);
        cancel.addActionListener(this);
        input.addActionListener(this);
        fonts.addActionListener(this);
        sizes.addActionListener(this);

        JPanel p0 = new JPanel();
        p0.add(input);
        p0.setBorder(new TitledBorder(new EtchedBorder(), "Text"));

        JPanel p1 = new JPanel();
        p1.add(fonts);
        p1.setBorder(new TitledBorder(new EtchedBorder(), "Font Family"));

        JPanel p2 = new JPanel(); // use FlowLayout
        p2.add(sizes);
        p2.setBorder(new TitledBorder(new EtchedBorder(), "Font Size"));
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.add(example);
        p3.setPreferredSize(new Dimension(250, 60));
        p3.setMaximumSize(new Dimension(250, 60));
        p3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel p4 = new JPanel();
        p4.add(ok);
        p4.add(cancel);
        p4.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(p0);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p4);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Dimension d1 = p3.getPreferredSize();

        this.setContentPane(p);
        this.pack();
    }

    public Font getFont() {
        return this.mainFont;
    }

    public String getText() {
        return this.inputText;
    }

    public int getInputSize() {
        return this.inputSize;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == ok) {
            userResponse = APPLY_OPTION;
            update();
            this.setVisible(false);
        } else if (source == cancel) {
            userResponse = CANCEL_OPTION;
            this.setVisible(false);
        } else {
            update();
        }

    }

    public void update() {
        inputText = input.getText();
        inputSize = Integer.valueOf((String) sizes.getSelectedItem());
        mainFont = new Font((String) fonts.getSelectedItem(), Font.PLAIN, inputSize);
        example.setFont(mainFont);
        example.setText(inputText);
    }

    public Color getColor() {
        return example.getBackground();
    }

    public int showCustomDialog(Frame f) {
        this.setLocationRelativeTo(f);

        this.setVisible(true);

        return userResponse;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        update();
    }
}