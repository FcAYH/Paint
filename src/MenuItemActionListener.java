import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JMenuItem) e.getSource()).getName();
        if (name.equals("newFile")) {
            StartUp.mainWindow.createNewDrawPanel();
        } else if (name.equals("openFile")) {

        } else if (name.equals("saveFile")) {
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
