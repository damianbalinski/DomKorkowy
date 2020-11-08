package graphics;

import logic.Player;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    private Window window;
    Rectangle[] rectangles;
    Player player;
    float offsetX = 0.0f, offsetY = 0.0f;

    @Override
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window.getWindowHandle());
        glfwDestroyWindow(window.getWindowHandle());
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        window = new Window();
        GL.createCapabilities();
    }

    private void action() {
        if (Input.RIGHT) {
            offsetX = 0.01f;
        } else if (Input.LEFT) {
            offsetX = -0.01f;
        }

        if (Input.UP) {
            offsetY = 0.01f;
        } else if (Input.DOWN) {
            offsetY = -0.01f;
        }

        rectangles[0].draw();

        if (player.shape.collidesWith(rectangles[1]) && offsetY < 0.0f) {
            offsetY = 0.0f;
        }

        player.shape.move(offsetX, offsetY);
        player.shape.draw();

        if (Input.MOUSE_X > -2.0f && Input.MOUSE_Y > -2.0f) {
            if (rectangles[1].hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
                rectangles[1].move(-0.3f, 0.2f);
            }
        }
        rectangles[1].draw();

        offsetX = 0.0f;
        offsetY = 0.0f;

        Input.resetMouse();
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        rectangles = new Rectangle[2];
        rectangles[0] = new Rectangle(-1.0f, -1.0f * 9.0f / 16.0f, 2.0f, 2.0f * 9.0f / 16.0f);
        rectangles[0].initGL("bg.jpg");

        player = new Player(-0.7f, -0.4f, 0.3f, 0.3f, "korkowa_postac.png");
        //player.shape.initGL("korkowa_postac.png");
        rectangles[1] = new Rectangle(-0.2f, -0.2f, 1.0f, 0.1f);
        rectangles[1].initGL("platforma.png");

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            action();

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers

            // sprawdza czy zaszly jakies eventy
            glfwPollEvents();
        }
    }
}
