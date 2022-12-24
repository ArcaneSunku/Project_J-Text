package git.arcanesunku.components;

import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

public class TextMenuBar extends JMenuBar {
    private final List<TextMenu> mMenuEntries;

    public TextMenuBar() {
        super();
        mMenuEntries = new ArrayList<>();
    }

    public void addTextMenu(TextMenu contextMenu) {
        mMenuEntries.add(contextMenu);
        int index = mMenuEntries.indexOf(contextMenu);

        add(mMenuEntries.get(index));
    }

    public void removeTextMenu(TextMenu contextMenu) {
        TextMenu menu = mMenuEntries.get(mMenuEntries.indexOf(contextMenu));
        remove(menu);

        mMenuEntries.remove(menu);
    }



}
