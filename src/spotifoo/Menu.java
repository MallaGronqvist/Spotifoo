package spotifoo;

import java.util.Scanner;
import java.util.Vector;

public class Menu {

    public String getMenuTitle() {
        return menuTitle;
    }

    private class MenuItem {

        private final String menuText;

        public MenuItem() {
            menuText = "";
        }

        public MenuItem(String menuText) {
            this.menuText = menuText;
        }

        public String getMenuText() {
            return menuText;
        }
    }

    private final Vector<MenuItem> menuItems = new Vector<>();
    private String menuTitle;

    public void addItem(String menuText) {
        MenuItem tempMenuItem = new MenuItem(menuText);
        menuItems.add(tempMenuItem);
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuChoice() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Enter your choice from the menu options:");
            Scanner keyboard = new Scanner(System.in);
            String choice = keyboard.nextLine();
            try {
                int i = Integer.parseInt(choice);
                if (i >= 0 && i <= menuItems.size()) {
                    validInput = true;
                    return i;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
        // An extra return statement to keep the compiler happy.
        return 0;
    }

    public void printMenuItems() {
        System.out.println(menuTitle);
        int i = 1;
        for (var item : menuItems) {
            System.out.println("[" + i + "] " + item.getMenuText());
            i++;
        }
    }

    public Vector<MenuItem> getMenuItems() {
        return menuItems;
    }
}

