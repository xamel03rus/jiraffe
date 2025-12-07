package jiraffe.core.basis;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPosCallback extends GLFWCursorPosCallback {
    @Override
    public void invoke(long window, double x, double y) {
        Input.prevCursorPosition[0] = Input.cursorPosition[0];
        Input.prevCursorPosition[1] = Input.cursorPosition[1];
        Input.cursorPosition[0] = (float) x;
        Input.cursorPosition[1] = (float) y;
    }
}
