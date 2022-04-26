import processing.core.PApplet;
import processing.core.PImage;

interface MouseClickListener {
    void mouseClicked();
}

public class PButton {
    enum State {
        NORMAL,
        HOVER,
        ACTIVE
    }

    private State currentState = State.NORMAL;

    private final float x, y, width, height;
    private Coordinates coordinates;

    private String label = "";

    private PImage backgroundImage = null;
    private PImage backgroundImageActive = null;
    private PImage backgroundImageHover = null;

    private boolean enabled = true;

    private final MouseClickListener clickListener = () -> {
    };

    private final PApplet applet;

    public PButton(PApplet applet, float x, float y, float width, float height, String label) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    public PButton(PApplet applet, float x, float y, float size, Coordinates coordinates) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        this.coordinates = coordinates;
    }

    public PButton(PApplet applet, float x, float y, float size, PImage backgroundImage) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        this.backgroundImage = backgroundImage;
    }

    Coordinates getButtonCoordinates() {
        return coordinates;
    }


    PImage getBackgroundImage() {
        return backgroundImage;
    }

    void setBackgroundImage(PImage backgroundImage) {
        if (!enabled) return;
        this.backgroundImage = backgroundImage;
    }

    void setBackgroundImageActive(PImage backgroundImageActive) {
        if (!enabled) return;
        this.backgroundImageActive = backgroundImageActive;
    }

    void setBackgroundImageHover(PImage backgroundImageHover) {
        if (!enabled) return;
        this.backgroundImageHover = backgroundImageHover;
    }

    void setDisabled() {
        this.enabled = false;
    }

    void mousePressed() {
        if (!enabled) return;

        if (areCoordinatesInside(applet.mouseX, applet.mouseY)) {
            currentState = State.ACTIVE;
        }
    }

    void mouseReleased() {
        if (!enabled) return;

        if (areCoordinatesInside(applet.mouseX, applet.mouseY)) {
            currentState = State.HOVER;
        } else {
            currentState = State.NORMAL;
        }
    }

    void mouseCLicked() {
        if (!enabled) return;

        if (areCoordinatesInside(applet.mouseX, applet.mouseY)) {
            clickListener.mouseClicked();
        }
    }

    void mouseMoved() {
        if (!enabled) return;

        if (areCoordinatesInside(applet.mouseX, applet.mouseY)) {
            currentState = State.HOVER;
        } else {
            currentState = State.NORMAL;
        }
    }

    void draw() {
        int fontSize = 30;
        int textColor = 0xff0300a8;
        if (currentState == State.NORMAL) {
            int backgroundColor = 0xff999393;
            applet.fill(backgroundColor);
            applet.rect(x, y, width, height);
            if (backgroundImage != null) {
                applet.image(backgroundImage, x, y, width, height);
            }

            if (!label.isEmpty()) {
                applet.fill(textColor);
                applet.textAlign(applet.CENTER, applet.CENTER);
                applet.textSize(fontSize);
                applet.text(label, x + width / 2, y + height / 2);
            }
        } else if (currentState == State.HOVER) {
            if (backgroundImageHover != null) {
                applet.image(backgroundImageHover, x, y, width, height);
            } else {
                int backgroundColorHover = 0xff806565;
                applet.fill(backgroundColorHover);
                applet.rect(x, y, width, height);
            }
            if (!label.isEmpty()) {
                applet.fill(textColor);
                applet.textAlign(applet.CENTER, applet.CENTER);
                applet.textSize(fontSize);
                applet.text(label, x + width / 2, y + height / 2);
            }
        } else if (currentState == State.ACTIVE) {
            int backgroundColorActive = 0xffb5b3b3;
            applet.fill(backgroundColorActive);
            applet.rect(x, y, width, height);
            if (backgroundImageActive != null) {
                applet.image(backgroundImageActive, x, y, width, height);
            }
            if (!label.isEmpty()) {
                int textColorActive = 0xffEE4444;
                applet.fill(textColorActive);
                applet.textAlign(applet.CENTER, applet.CENTER);
                applet.textSize(fontSize);
                applet.text(label, x + width / 2, y + height / 2);
            }
        }
    }

    public boolean areCoordinatesInside(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width &&
                mouseY >= y && mouseY < y + height;
    }
}
