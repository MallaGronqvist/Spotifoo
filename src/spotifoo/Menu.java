package spotifoo;

import java.util.Scanner;
import java.util.Vector;

public class Menu {

    private class MenuItem {

        private String menuText;

        public MenuItem(){
            menuText = "";
        }

        public MenuItem(String menuText) {
            this.menuText = menuText;
        }

        public String getMenuText() {
            return menuText;
        }

        public void setMenuText(String menuText) {
            this.menuText = menuText;
        }
    }

    private Vector<MenuItem>menuItems = new Vector<>();
    private String menuTitle;

    public void addItem(String menuText) {
        MenuItem tempMenuItem = new MenuItem(menuText);
        menuItems.add(tempMenuItem);
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuChoice() {
        boolean again = true;
        do{
            try{
                System.out.println("Enter your choice from the menu options:");
                Scanner keyboard = new Scanner(System.in);
                String choice = keyboard.nextLine();
                int i = Integer.parseInt(choice);           // Validate input first!
                if (i >= 0 && i <= menuItems.size()) {
                    again = false;
                    return i;
                } else {
                    System.out.println("Incorrect input. Try again.");
                }
            }catch(Exception e){

            }
        }while(again);
        return 0;  // not needed!
    }
    public void printMenuItems() {
        System.out.println(menuTitle);
        int i = 1;
        for(var item : menuItems) {
            System.out.println("[" + i + "] " + item.getMenuText());
            i++;
        }
    }
}

