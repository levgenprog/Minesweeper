import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    protected PApplet thisPApplet = this;
    private static final int MINES_FOR_BEGINNER = 10;
    private static final int MINES_FOR_INTERMEDIATE = 40;
    private static final int MINES_FOR_EXPERT = 99;

    private static final int FIELD_WIDTH_FOR_BEGINNER = 9;
    private static final int FIELD_HEIGHT_FOR_BEGINNER = 9;
    private static final int FIELD_WIDTH_FOR_INTERMEDIATE = 16;
    private static final int FIELD_HEIGHT_FOR_INTERMEDIATE = 16;
    private static final int FIELD_WIDTH_FOR_EXPERT = 33;
    private static final int FIELD_HEIGHT_FOR_EXPERT = 16;

    enum Levels {BEGINNER, INTER, EXPERT}

    private static Levels currentLevel = Levels.BEGINNER;

    float centerX, centerY, fieldWidth, fieldHeight;

    Game game;
    ArrayList<PButton> buttons = new ArrayList<>();
    PButton face, levelBeginner, levelInter, levelPro;
    float buttonWidth = 200;
    float buttonHeight = 50;

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        centerX = width / 2f;
        centerY = height / 2f;
        switch (currentLevel) {
            case BEGINNER:
                game = new Game(FIELD_WIDTH_FOR_BEGINNER, FIELD_HEIGHT_FOR_BEGINNER, MINES_FOR_BEGINNER);
                break;
            case INTER:
                game = new Game(FIELD_WIDTH_FOR_INTERMEDIATE, FIELD_HEIGHT_FOR_INTERMEDIATE, MINES_FOR_INTERMEDIATE);
                break;
            case EXPERT:
                game = new Game(FIELD_WIDTH_FOR_EXPERT, FIELD_HEIGHT_FOR_EXPERT, MINES_FOR_EXPERT);
                break;
        }
        fieldWidth = Game.size.x * Game.IMAGE_SIZE;
        fieldHeight = Game.size.y * Game.IMAGE_SIZE;

        run();

        face = new PButton(thisPApplet, centerX - 32, centerY - fieldHeight / 2 - 100, 64, loadImage("smile_face.png"));
        face.setBackgroundImageHover(loadImage("face_think.png"));
        levelBeginner = new PButton(thisPApplet, centerX + fieldWidth / 2 + 50, centerY - 250, buttonWidth, buttonHeight, "Beginner");
        levelInter = new PButton(thisPApplet, centerX + fieldWidth / 2 + 50, centerY - 50, buttonWidth, buttonHeight, "Intermediate");
        levelPro = new PButton(thisPApplet, centerX + fieldWidth / 2 + 50, centerY + 150, buttonWidth, buttonHeight, "Expert");

    }

    private void run() {
        for (Coordinates coordinates : Game.getAllCoordinates()) {
            PButton button = new PButton(thisPApplet, centerX - fieldWidth / 2 + coordinates.x * Game.IMAGE_SIZE,
                    centerY - fieldHeight / 2 + coordinates.y * Game.IMAGE_SIZE, Game.IMAGE_SIZE, coordinates);
            buttons.add(button);
        }
    }

    protected static boolean isGameOver() {
        boolean gameOver = false;
        if (Game.status.equals(Game.GameStates.LOST) || Game.status.equals(Game.GameStates.WON)) {
            gameOver = true;
            Game.timer.stop();
        }
        return gameOver;
    }

    @Override
    public void draw() {
        background(0xff424242);
        for (PButton button : buttons) {
            button.setBackgroundImage(loadImage(game.flag.getFlagChar(button.getButtonCoordinates())));
            button.setBackgroundImageHover(button.getBackgroundImage());
            button.setBackgroundImageActive(button.getBackgroundImage());
            button.draw();
        }
        face.draw();
        levelBeginner.draw();
        levelPro.draw();
        levelInter.draw();
        if (isGameOver()) {
            switch (Game.status) {
                case LOST:
                    face.setBackgroundImage(loadImage("face_lose.png"));
                    break;
                case WON:
                    face.setBackgroundImage(loadImage("face_win.png"));
                    break;
            }
            for (PButton button : buttons) {
                button.setDisabled();
            }
        }


        textAlign(CENTER, CENTER);
        textSize(37);
        fill(255, 0, 0);
        text(+Game.bomb.bombsLeft, centerX + 130, centerY - fieldHeight / 2 - 55);
        int s = Game.timer.getSeconds();
        text(s, centerX - 130, centerY - fieldHeight / 2 - 55);
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (face.areCoordinatesInside(mouseX, mouseY)) {
                this.game = null;
                buttons.clear();
                setup();
            } else if (levelBeginner.areCoordinatesInside(mouseX, mouseY)) {
                this.game = null;
                buttons.clear();
                currentLevel = Levels.BEGINNER;
                setup();
            } else if (levelInter.areCoordinatesInside(mouseX, mouseY)) {
                this.game = null;
                buttons.clear();
                currentLevel = Levels.INTER;
                setup();
            } else if (levelPro.areCoordinatesInside(mouseX, mouseY)) {
                this.game = null;
                buttons.clear();
                currentLevel = Levels.EXPERT;
                setup();
            } else {
                for (PButton button : buttons) {
                    if (button.areCoordinatesInside(mouseX, mouseY)) {
                        game.leftClick(button.getButtonCoordinates());
                        button.setBackgroundImage(loadImage(game.flag.getFlagChar(button.getButtonCoordinates())));
                        button.setBackgroundImageActive(button.getBackgroundImage());
                        button.setBackgroundImageHover(button.getBackgroundImage());
                    }
                }
            }
        }
        if (mouseButton == RIGHT) {
            for (PButton button : buttons) {
                if (button.areCoordinatesInside(mouseX, mouseY)) {
                    game.rightClick(button.getButtonCoordinates());
                    button.setBackgroundImage(loadImage(game.flag.getFlagChar(button.getButtonCoordinates())));
                    button.setBackgroundImageActive(button.getBackgroundImage());
                    button.setBackgroundImageHover(button.getBackgroundImage());
                }
            }
        }
        for (PButton button : buttons) {
            button.mousePressed();
        }
        face.mousePressed();
        levelBeginner.mousePressed();
        levelInter.mousePressed();
        levelPro.mousePressed();
    }

    @Override
    public void mouseReleased() {
        for (PButton button : buttons) {
            button.mouseReleased();
        }
        face.mouseReleased();
        levelBeginner.mouseReleased();
        levelInter.mouseReleased();
        levelPro.mouseReleased();
    }

    @Override
    public void mouseClicked() {
        for (PButton button : buttons) {
            button.mouseCLicked();
        }
        face.mouseCLicked();
        levelBeginner.mouseCLicked();
        levelInter.mouseCLicked();
        levelPro.mouseCLicked();
    }

    @Override
    public void mouseMoved() {
        for (PButton button : buttons) {
            button.mouseMoved();
        }
        face.mouseMoved();
        levelBeginner.mouseMoved();
        levelInter.mouseMoved();
        levelPro.mouseMoved();
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
