package git.arcanesunku.utils;

import git.arcanesunku.utils.editor.TextMenuBar;

import javax.swing.*;
import java.awt.*;

public class Window {
    private final int mCloseOperation;

    private JFrame mFrame;
    private JPanel mPanel;

    private JTextArea mTextArea;
    private TextMenuBar mMenuBar;

    private String mTitle;
    private int mWidth, mHeight;

    public Window(String title, int width, int height, int closeOperation) {
        mTitle = title;

        mWidth = width;
        mHeight = height;

        mCloseOperation = closeOperation;
        init();
    }

    public Window(String title, int width, int height) {
        this(title, width, height, JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        mFrame = new JFrame();
        mPanel = new JPanel();

        mTextArea = new JTextArea();
        mMenuBar = new TextMenuBar();

        Dimension size = new Dimension(mWidth, mHeight);
        mPanel.setPreferredSize(size);
        mPanel.setLayout(new GridLayout());

        setupTypingArea(mPanel, mTextArea);

        mFrame.setPreferredSize(size);
        mFrame.setDefaultCloseOperation(mCloseOperation);
        mFrame.setLayout(new BorderLayout());
        mFrame.setTitle(mTitle);
        mFrame.add(mMenuBar, BorderLayout.NORTH);
        mFrame.add(mPanel, BorderLayout.CENTER);
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);

        mMenuBar.setVisible(true);
    }

    public void show() {
        mFrame.setVisible(true);
    }

    public void close() {
        mFrame.dispose();
        System.exit(0);
    }

    public void setTitle(String title, boolean append) {
        if(!append) {
            mTitle = title;
            mFrame.setTitle(mTitle);
        } else {
            mFrame.setTitle(mTitle + title);
        }
    }

    public JTextArea getTextArea() {
        return mTextArea;
    }

    public TextMenuBar getMenuBar() {
        return mMenuBar;
    }

    public void setTitle(String title) {
        setTitle(title, false);
    }

    private void setupTypingArea(JPanel panel, JTextArea typeArea) {
        typeArea.setPreferredSize(mFrame.getSize());
        panel.add(typeArea);
    }

}
