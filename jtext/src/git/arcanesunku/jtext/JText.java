package git.arcanesunku.jtext;

import git.arcanesunku.utils.Window;
import git.arcanesunku.utils.editor.TextMenu;
import git.arcanesunku.utils.editor.TextMenuBar;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JText {

    private final Window mWindow;
    private File mFile;

    private JText() {
        mWindow = new Window("JText", 800, 400);
        setupWindow();
    }

    private void setupWindow() {
        mFile = new File("Untitled.txt");

        mWindow.setTitle(String.format(": %s", mFile.getName()), true);
        populateMenuBar();
        mWindow.show();
    }

    private void populateMenuBar() {
        // File Context Menu
        TextMenuBar bar = mWindow.getMenuBar();
        TextMenu file_menu = new TextMenu("File");

        JMenuItem new_file = new JMenuItem("New...");
        new_file.addActionListener(e -> {
            if(mFile.exists()) {
                switch(JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to close [%s]?", mFile.getName())))
                {
                    case 0:
                        createNewFile();
                        break;

                    case 1, 2:
                        break;
                }
            } else {
                createNewFile();
            }
        });
        file_menu.addMenuItem(new_file);

        JMenuItem save_file = new JMenuItem("Save");
        save_file.addActionListener(e -> {
            JTextArea text_area = mWindow.getTextArea();
            String text_context = text_area.getText();

            if(!mFile.exists()) {
                try {
                    if(!mFile.createNewFile())
                        throw new IOException(String.format("Failed to save [%s]!", mFile.getName()));

                    saveContent(mFile, text_context);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            } else {
                if (JOptionPane.showConfirmDialog(null, String.format("File [%s] already exists! Do you want to overwrite it?", mFile.getName()), "File Overwrite?", JOptionPane.YES_NO_OPTION) == 0) {
                    saveContent(mFile, text_context);
                }
            }
        });
        file_menu.addMenuItem(save_file);

        JMenuItem exit_app = new JMenuItem("Exit");
        exit_app.addActionListener(e -> mWindow.close());
        file_menu.addMenuItem(exit_app);

        // Edit Context Menu


        // Add context menus
        bar.add(file_menu);
    }

    private void saveContent(File file, String content) {
        try(FileWriter writer = new FileWriter(file)) {
            writer.append(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewFile() {
        mFile = new File("New File.txt");
        mWindow.setTitle(String.format(": %s", mFile.getName()), true);
    }

    public static void main(String[] args) {
        new JText();
    }

}
