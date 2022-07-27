// A command line music player.
// Java summer course 2022.
// Author: Malla Grönqvist
// Menu.java
//*****************************************************************************

package spotifoo;

import java.util.ArrayList;

public class Menu {
    private final ArrayList<MenuItem> menuItems = new ArrayList<>();
    private String menuTitle;

    public void addItem(String menuText) {
        MenuItem tempMenuItem = new MenuItem(menuText);
        menuItems.add(tempMenuItem);
    }

    public int getNumberOfOptions() {
        return menuItems.size();
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public ArrayList<MenuItem> getOptions() {
        return menuItems;
    }

    private static class MenuItem {

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
}

