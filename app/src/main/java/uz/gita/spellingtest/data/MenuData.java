package uz.gita.spellingtest.data;

import androidx.annotation.Keep;

@Keep
public class MenuData {
    private int menuImage;
    private String menuTitle;
    private final int resultIndicator;
    private final boolean isOpen;

    public MenuData(int menuImage, String menuTitle, int resultIndicator, boolean isOpen) {
        this.menuImage = menuImage;
        this.menuTitle = menuTitle;
        this.resultIndicator = resultIndicator;
        this.isOpen = isOpen;
    }

    public int getMenuImage() {
        return menuImage;
    }
    public String getMenuTitle() { return menuTitle;}
    public int getMenuResultIndicator() {return resultIndicator;}
    public boolean isOpen() {return isOpen;}

    public void setMenuImage(int menuImage) {
        this.menuImage = menuImage;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }
}
