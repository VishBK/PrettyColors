package com.example.vishk.prettycolors;

public class PaletteItem {
    private float[] color1, color2, color3;
    private String title;
    private String description;

    PaletteItem(float[] hsv1, float[] hsv2, float[] hsv3, String t) {
        color1 = hsv1;
        color2 = hsv2;
        color3 = hsv3;
        title = t;
    }

    PaletteItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public float[] getColor1() {
        return color1;
    }

    public float[] getColor2() {
        return color2;
    }

    public float[] getColor3() {
        return color3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String setTitle) { title = setTitle;}

    public String getDescription() { return description; }

}
