package jiraffe.core.basis;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonCallback  extends GLFWMouseButtonCallback {

    @Override
    public void invoke(long window, int button, int action, int mod) {

        Input.mouseButtons[button] = action == GLFW.GLFW_PRESS;

    }

}
