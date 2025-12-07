package jiraffe.core.basis;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.nuklear.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

public class Window {
    private long window;

    /**
     * width[0] , height[1]
     */
    public int[] sizes = new int [2];

    /**
     * window aspect
     */
    protected float aspect;

    /**
     * is window visible
     */
    protected boolean visible;

    /**
     * is window resizable
     */
    protected boolean resizable;

    /**
     * is window decorated;
     */
    protected boolean decorated;

    /**
     * Return window sizes
     * @return int[]
     */
    public int[] getSizes()
    {
        return sizes;
    }

    /**
     * Return window handle ID
     * @return long
     */
    public long getID()
    {
        return window;
    }

    /**
     * Return aspect of window
     * @return float
     */
    public float getAspect()
    {
        return aspect;
    }

    public void up() {
        // Параметры в дефолт
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // hidden
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // resizable
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);

        // Get the resolution
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
        GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
        GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
        GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);

        window = GLFW.glfwCreateWindow(mode.width() / 2, mode.height() / 2, "Window", MemoryUtil.NULL, MemoryUtil.NULL);

        if ( window == MemoryUtil.NULL )
            throw new RuntimeException("Failed to create the window");

        sizes[0] = mode.width();
        sizes[1] = mode.height();

        aspect = (float) sizes[0] / (float) sizes[1];

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, ww, hh) -> {
            this.sizes[0] = ww;
            this.sizes[1] = hh;
            this.aspect = (float) this.sizes[0] / (float) this.sizes[1];
        });
        // Make the window visible
        GLFW.glfwShowWindow(window);

        construct();
    }

    public void down() {
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
    }

    public void listen() {
        GL11.glViewport(0, 0, this.sizes[0], this.sizes[1]);

        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    private NkContext ctx = NkContext.create(); // Create a Nuklear context, it is used everywhere.
    private NkUserFont default_font = NkUserFont.create(); // This is the Nuklear font object used for rendering text.

    private NkBuffer cmds = NkBuffer.create(); // Stores a list of drawing commands that will be passed to OpenGL to render the interface.
    private NkDrawNullTexture null_texture = NkDrawNullTexture.create(); // An empty texture used for drawing.

    public void construct()
    {
        try (MemoryStack stack = stackPush()) {
            NkRect rect = NkRect.mallocStack(stack);

            if (nk_begin(ctx, "Calculator", nk_rect(0, 0, 180, 250, rect), NK_WINDOW_BORDER | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_MOVABLE)) {
                System.out.println("dsdsd");
                nk_layout_row_static(ctx, 35, 80, 1);
                {
                    nk_button_label(ctx, "button");
                }
            }
            nk_end(ctx);
        }
    }

    /**
     * Check window is closed
     * @return
     */
    public boolean isClosed() {
        return GLFW.glfwWindowShouldClose(window);
    }
}
