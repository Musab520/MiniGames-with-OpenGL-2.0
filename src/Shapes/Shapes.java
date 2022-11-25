package Shapes;

import java.awt.Color;

public abstract class Shapes {
    private Color color;
    private boolean isDifferent=false;
    private boolean isClicked=false;

    public Shapes() {

    }

    public Shapes(Color color, boolean isDifferent, boolean isClicked) {
        this.color = color;
        this.isDifferent = isDifferent;
        this.isClicked=isClicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isDifferent() {
        return isDifferent;
    }

    public void setDifferent(boolean isDifferent) {
        this.isDifferent = isDifferent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public abstract String returnShapeType() ;
}