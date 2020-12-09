package graphics;

import com.google.gson.JsonObject;
import logic.*;
import map.MapManager;
import map.Stage;
import map.json.JsonUtils;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import java.util.Collection;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    private Window window;

    public static MapManager map;
    public static Player KORKOWY;
    public static Rectangle HEALTHBAR;

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
        map = new MapManager();
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        initTempMap();
    }

    private void initTempMap() {
        try {
            JsonObject obj = JsonUtils.fromFile("test_file.json");
            map.fromJson(obj);
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(true)
            return;

        Stage stage;

        /////////////////////////
        // STAGE 1
        stage = new Stage("background/sky.png", -0.7f, -0.45f);

        // obiekty statyczne
        stage.addMapEntity(new StaticObject(0.1f, -0.96f, 0.13f, 0.6f, "trees/tree4.png"));
        stage.addMapEntity(new StaticObject(0.7f, -0.95f, 0.06f, 0.3f, "trees/tree4.png"));
        stage.addMapEntity(new StaticObject(-0.65f, -0.11f, 0.12f, 0.2f, "trees/tree1.png"));

        // drzwi
        stage.addMapEntity(new Door(-0.7f, -0.9f, 0.10f, 0.25f, "door.png", false));
        stage.addMapEntity(new Door(0.8f, 0.3f, 0.10f, 0.25f, "door.png", true));

        // przeszkody
        stage.addMapEntity(new Obstacle(-0.8f, -0.52f, 0.13f, 0.08f, "obstacles/spikes_2.png"));
        stage.addMapEntity(new Obstacle(0.5f, -0.52f, 0.13f, 0.08f, "obstacles/spikes_2.png"));
        stage.addMapEntity(new Obstacle(-0.4f, -0.10f, 0.11f, 0.17f, "obstacles/swinging_spike_block.png"));
        stage.addMapEntity(new WheelObstacle(0.4f, 0.4f, 0.11f, 0.11f*Config.RESOLUTION, "obstacles/blade_2.png", 1.0f));

        // planformy
        stage.addMapEntity(new Platform(-0.2f, -0.6f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(0.0f, 0.2f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -0.6f, 0.5f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-0.75f, -0.2f, 0.8f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));

        // moby
        stage.addMapEntity(new Mob(0.5f, -0.5f, 0.08f, 0.18f,"koniec.png",-0.2f,0.7f,0.01f, Entity.RIGHT));

//        stage.buildHashMap();
//        stage.buildStage();
//        stage.initStage();
        map.addStage(stage);


        /////////////////////////
        // STAGE 2
        stage = new Stage("background/back.png", 0.8f, -0.45f);

        // drzwi
        stage.addMapEntity(new Door(0.8f, -0.9f, 0.10f, 0.25f, "door.png", false));
        stage.addMapEntity(new Door(0.8f, 0.6f, 0.10f, 0.25f, "door.png", true));

        // przeszkody
        stage.addMapEntity(new WheelObstacle(-1.0f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(-0.8f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(-0.6f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(-0.4f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(0.0f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(0.2f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(0.4f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(0.6f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(0.8f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));

        stage.addMapEntity(new WheelObstacle(-0.8f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
        stage.addMapEntity(new WheelObstacle(-0.6f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", 1.0f));
        stage.addMapEntity(new WheelObstacle(-0.4f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
        stage.addMapEntity(new WheelObstacle(-0.2f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
        stage.addMapEntity(new WheelObstacle(0.0f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
        stage.addMapEntity(new WheelObstacle(0.2f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
        stage.addMapEntity(new WheelObstacle(0.4f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
        stage.addMapEntity(new WheelObstacle(0.6f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
        stage.addMapEntity(new WheelObstacle(0.8f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));

        // platformy
        stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -0.25f, 0.8f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(0.0f, -0.25f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-0.8f, 0.5f, 2.0f, 0.1f, "platforma.png"));

//        stage.buildHashMap();
//        stage.buildStage();
//        stage.initStage();
        map.addStage(stage);

        /////////////////////////
        // STAGE 3
        stage = new Stage("background/back.png", 0.8f, 0.34f);

        // drzwi
        stage.addMapEntity(new Door(0.8f, 0.6f, 0.10f, 0.25f, "door.png", false));
        stage.addMapEntity(new Door(0.8f, -0.9f, 0.10f, 0.25f, "door.png", true));

        // przeszkody
        stage.addMapEntity(new WheelObstacle(-1f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(-0.7f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(-0.4f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(0.0f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
        stage.addMapEntity(new WheelObstacle(0.3f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
        stage.addMapEntity(new WheelObstacle(0.6f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", -3.0f));

        stage.addMapEntity(new WheelObstacle(-0.35f, 0.37f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 2.0f));
        stage.addMapEntity(new WheelObstacle(0.3f, 0.37f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", 2.0f));

        // platformy
        stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -0.25f, 0.8f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(0.0f, -0.25f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-0.8f, 0.5f, 2.0f, 0.1f, "platforma.png"));

//        stage.buildHashMap();
//        stage.buildStage();
        map.addStage(stage);

        /////////////////////////
        // Credits
        stage = new Stage("background/finalStage.png", 0.0f, -0.5f);
        stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "plank_platform_brick1.png"));

//        stage.buildHashMap();
//        stage.buildStage();
//        stage.initStage();
        map.addStage(stage);
        map.nextStage();

    }

    private void action() {
        map.move();
        map.update();
        map.draw();

        KORKOWY.move();
        KORKOWY.update();
        HEALTHBAR.draw();

        Input.resetMouse();
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        KORKOWY = new Player(-0.7f, -0.8f, 0.08f, 0.18f, "korkowa_postac.png");

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            action();

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers

            // sprawdza czy zaszly jakies eventy
            glfwPollEvents();
        }
    }

    public static Collection<Entity> getPlatforms() {
        return map.getCurrentStage().platforms.values();
    }

    public static Collection<Entity> getMobs() {
        return map.getCurrentStage().mobs.values();
    }

    public static Collection<Entity> getObstacles() {
        return map.getCurrentStage().obstacles.values();
    }

    public static Collection<Entity> getDoors() {
        return map.getCurrentStage().doors.values();
    }

    public static float[] getStart() { return map.getCurrentStage().start; }
}
