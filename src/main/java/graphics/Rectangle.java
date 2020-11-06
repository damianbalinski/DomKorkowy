package graphics;

import items.items_tree.GameItem;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static constants.ItemTypes.*;

public class Rectangle {
    private final VertexArrayObject VAO;
    private final Texture texture;
    private final Program program;
    public float org_posX, org_posY, posX, posY, width, height;
    private int type;


    public Rectangle(float posX, float posY, float width, float height, String textureName) {
        float[] vertices = new float[]{
                posX, posY, 0.0f,
                posX, posY + height, 0.0f,
                posX + width, posY + height, 0.0f,
                posX + width, posY, 0.0f
        };

        int[] indices = new int[]{
                0, 1, 2,
                0, 3, 2
        };

        float[] texCoords = new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        this.org_posX = posX;
        this.org_posY = posY;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.type = STATIC_ITEM;
        VAO = new VertexArrayObject(vertices, indices, texCoords);
        texture = new Texture(textureName);
        program = new Program("triangle.vert.glsl", "triangle.frag");
    }

    public void draw() {
        glUseProgram(program.programID);
        texture.bind();
        glBindVertexArray(VAO.VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        texture.unbind();
        glUseProgram(0);
    }

    public void move(float x, float y) {
        posX += x;
        posY += y;

        glUseProgram(program.programID);
        program.setFloat2("offset", posX - org_posX, posY - org_posY);
        glUseProgram(0);
    }

    // sprawdza czy (x, y) jest wewnatrz prostokata
    public boolean hasPoint(float x, float y) {
        if (x >= posX && x <= posX + width) {
            return y >= posY && y <= posY + height;
        }

        return false;
    }

    // sprawdza czy prostokaty zachodza na siebie
    public boolean collidesWith(Rectangle other) {
        if (posX <= other.posX + other.width && posX + width >= other.posX) {
            return posY <= other.posY + other.height && posY + height >= other.posY;
        }

        return false;
    }

    public int getType() {
        return type;
    }
}
