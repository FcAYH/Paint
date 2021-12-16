import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;

public class StartUp {
    public static PaintMainWindow mainWindow;
    public static void main(String[] args)
    {
        FlatGitHubIJTheme.setup();
        mainWindow = new PaintMainWindow();
    }
}
