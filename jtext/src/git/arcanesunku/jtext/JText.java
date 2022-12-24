package git.arcanesunku.jtext;

import git.arcanesunku.Window;
import git.arcanesunku.utils.Log;
import git.arcanesunku.utils.SystemData;
import git.arcanesunku.components.TextMenu;
import git.arcanesunku.components.TextMenuBar;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JText {

    private final Window mWindow;

    private File mFile;
    private JFileChooser mFileChooser;
    private int mNewFileCounter;

    private JText() {
        Log.setLevel(Log.Level.INFO);
        mWindow = new Window("JText", 800, 400);
        setupWindow();

        String localUserDir = String.format("%s\\.jtext", SystemData.getLocalUserDir());
        File file = new File(localUserDir);
        if(!file.isDirectory()) {
            boolean dirMade = file.mkdir();
            if(dirMade)
                System.out.println("Directory Created!");
        }
    }

    private void setupWindow() {
        mFileChooser = new JFileChooser();
        mFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Simple Text File", ".txt"));
        mFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Config File for Applications", ".properties", ".config", ".ini"));

        mNewFileCounter = 1;
        mFile = new File("Untitled.txt");
        mWindow.setTitle(String.format(": %s", mFile.getName()), true);

        populateMenuBar();
        mWindow.show();
    }

    private void populateMenuBar() {
        // File Context Menu
        TextMenuBar bar = mWindow.getMenuBar();
        TextMenu file_menu = new TextMenu("File");

        JMenuItem new_file = new JMenuItem("New");
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

        JMenuItem load_file = new JMenuItem("Open");
        load_file.addActionListener(e -> {
            // TODO: Implement file loading
            Log.print("Not Implemented");
        });
        file_menu.add(load_file);

        JMenuItem save_file = new JMenuItem("Save");
        save_file.addActionListener(e -> {
            JTextArea text_area = mWindow.getTextArea();
            String text_context = text_area.getText();

            // We assume if we are saving that the file is intended for overwrite, that is if it exists
            if(!mFile.exists()) {
                try {
                    if(shouldSave(mFile)) {
                        mFile = mFileChooser.getSelectedFile();
                        mWindow.setTitle(String.format(": %s", mFile.getName()), true);

                        if(!mFile.createNewFile())
                            throw new IOException(String.format("Failed to save [%s]!", mFile.getName()));

                        saveContent(mFile, text_context);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            } else {
                saveContent(mFile, text_context);
            }
        });
        file_menu.addMenuItem(save_file);

        // Edit Context Menu
        TextMenu edit_menu = new TextMenu("Edit");

        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(e -> {
            // TODO: Add Undo functionality
            Log.print("Not Implemented");
        });
        edit_menu.add(undo);

        // Help Context Menu
        TextMenu help_menu = new TextMenu("Help");

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "J-Text is a text editor made with Java 17 by ArcaneSunku.\n" +
                                                                        "It is entirely free and open-source.", "About", JOptionPane.PLAIN_MESSAGE);
        });
        help_menu.add(about);

        JMenuItem logFile = new JMenuItem("Log To File");
        logFile .addActionListener(e -> {
            Log.logToFile(!Log.loggingToFile());
        });
        help_menu.add(logFile);

        JMenuItem exit_app = new JMenuItem("Exit");
        exit_app.addActionListener(e -> mWindow.close());
        help_menu.addMenuItem(exit_app);

        // Add context menus
        bar.add(file_menu);
        bar.add(edit_menu);
        bar.add(help_menu);
    }

    private boolean shouldSave(File file) {
        mFileChooser.setSelectedFile(file);
        return mFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION;
    }

    private void saveContent(File file, String content) {
        try(FileWriter writer = new FileWriter(file)) {
            writer.append(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewFile() {
        mFile = new File(String.format("Untitled%d.txt", mNewFileCounter++));
        mWindow.setTitle(String.format(": %s", mFile.getName()), true);
    }

    public static void main(String[] args) {
        new JText();
    }

}
