package jiraffe.core.basis;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardCallback extends GLFWKeyCallback {

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        Input.keys[key] = action != GLFW.GLFW_RELEASE;
    }

}
