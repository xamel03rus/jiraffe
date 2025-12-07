package jiraffe.core.basis;

import jiraffe.core.graphic.Render;
import jiraffe.tools.AnimationEditorWindow;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public final class Engine {

    private static final ArrayList<Scene> scenes = new ArrayList<>();
    private static final ArrayList<Window> windows = new ArrayList<>();
    private static Window currentWindow;

    public static void setCurrentWindow(Window window)
    {
        currentWindow = window;
    }

    public Window getCurrentWindow()
    {
        return currentWindow;
    }

    public static void addWindow(Window window)
    {
        if(windows.size() == 0)
            currentWindow = window;

        windows.add(window);
    }
    private static Engine instance = new Engine();

    public static Engine getInstance()
    {
        if(instance == null)
            instance = new Engine();

        return instance;
    }

    public static Window getWindow(long windowHint)
    {
        for (Window currWindow : windows) {
            if (currWindow.getID() == windowHint)
                return currWindow;
        }

        return null;
    }

    public void addScene(Scene scene)
    {
        scenes.add(scene);
    }

    public void start()
    {
        init();
        loop();
        stop();
    }

    public void init()
    {
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !GLFW.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        createMainWindow();

        GLFW.glfwSetKeyCallback(windows.get(0).getID(), new KeyboardCallback());
        GLFW.glfwSetCursorPosCallback(windows.get(0).getID(),new CursorPosCallback());
        GLFW.glfwSetCursorEnterCallback(windows.get(0).getID(), new CursorEnterCallback());
        GLFW.glfwSetMouseButtonCallback(windows.get(0).getID(), new MouseButtonCallback());

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        // Enable v-sync 0 or 1
        GLFW.glfwSwapInterval(0);

        Render.getInstance().setPolygonMode(0);
    }

    public void loop()
    {
        while (!windows.get(0).isClosed()) {
            Render.getInstance().clearBuffers();

            for(Scene scene : scenes) {
                Render.getInstance().renderScene(scene);
                scene.acting();
            }

            windows.get(0).listen();

            Render.getInstance().clear();
        }
    }

    public void stop()
    {
        for(Window currWindow : windows)
            currWindow.down();

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public void createMainWindow()
    {
        AnimationEditorWindow window = new AnimationEditorWindow();
        window.up();

        addWindow(window);
    }
}
