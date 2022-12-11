package git.arcanesunku.utils.components;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TextMenu extends JMenu {

    private final List<JComboBox<JMenuItem>> mDropDownItems;
    private final List<JMenuItem> mMenuItems;

    public TextMenu(String text) {
        super(text);

        mDropDownItems = new ArrayList<>();
        mMenuItems = new ArrayList<>();
    }

    public TextMenu() {
        super();

        mDropDownItems = new ArrayList<>();
        mMenuItems = new ArrayList<>();
    }

    public void addDropDownItem(JComboBox<JMenuItem> dropDown) {
        mDropDownItems.add(dropDown);
        int index = mDropDownItems.indexOf(dropDown);

        add(mDropDownItems.get(index));
    }

    public void addMenuItem(JMenuItem item) {
        mMenuItems.add(item);
        int index = mMenuItems.indexOf(item);

        add(mMenuItems.get(index));
    }

    public void removeMenuItem(JMenuItem item) {
        mMenuItems.remove(item);
        remove(item);
    }

    public List<JMenuItem> getMenuItems() {
        return mMenuItems;
    }

}
