import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MenuItemActionListener implements ActionListener {
    /*
    Name:
    newFile,
    openFile,
    saveFile,
    full,
    half,
    quash,
    recover
    * */
    private void openFile(File file) {
        try {
            StartUp.mainWindow.getDrawPanel().setImage(ImageIO.read(file));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private BufferedImage makePanel(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.print(g);
        return bi;
    }

    private void saveFile(File f) throws IOException {
        BufferedImage im = makePanel(StartUp.mainWindow.getDrawPanel());
        ImageIO.write(im, "png", f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JMenuItem) e.getSource()).getName();
        if (name.equals("newFile")) {
            StartUp.mainWindow.createNewDrawPanel();
        } else if (name.equals("openFile")) {
            JFileChooser fileChooser = new JFileChooser(new File("."));
            fileChooser.setFileFilter(new FileNameExtensionFilter("图片", "jpg", "png"));
            if (fileChooser.showOpenDialog(StartUp.mainWindow) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                openFile(file);
            }

        } else if (name.equals("saveFile")) {
            JFileChooser fileChooser = new JFileChooser(new File("."));
            fileChooser.setFileFilter(new FileNameExtensionFilter("图片", "jpg", "png"));
            if (fileChooser.showSaveDialog(StartUp.mainWindow) == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile() + ".png");
                try {
                    saveFile(file);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }
        } else if (name.equals("full")) {
        } else if (name.equals("half")) {

        } else if (name.equals("quash")) {
            StartUp.mainWindow.getDrawPanel().quash();
        } else if (name.equals("recover")) {
            StartUp.mainWindow.getDrawPanel().recover();
        } else {
            return;
        }
        System.out.println(e.toString());
    }
}
