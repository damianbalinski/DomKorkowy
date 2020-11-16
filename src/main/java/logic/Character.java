package logic;

import graphics.Rectangle;

public class Character extends Entity {
    public int hp;
    private String texture;
    protected CharacterState state;

    public Character(float posX, float posY, float width, float height, String texture) {
        gravityFlag = true;
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.texture = texture;
        this.rectangle.initGL(this.texture);
    }

    @Override
    public void move() {}

    @Override
    public void draw() {
        rectangle.draw();
    }

    @Override
    public void update(){
        this.draw();
    }
}
