package spotifoo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Menu {

    private class MenuItem {

        private final String menuText;

        public MenuItem() {
            menuText = "";
        }

        public MenuItem(String menuText) {
            this.menuText = menuText;
        }

        @Override
        public String toString() {
            return menuText;
        }
    }

    private final ArrayList<MenuItem> menuItems = new ArrayList<>();
    private String menuTitle;

    public void addItem(String menuText) {
        MenuItem tempMenuItem = new MenuItem(menuText);
        menuItems.add(tempMenuItem);
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getNumberOfOptions(){
        return menuItems.size();
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public ArrayList<MenuItem> getOptions() {
        return menuItems;
    }
}

