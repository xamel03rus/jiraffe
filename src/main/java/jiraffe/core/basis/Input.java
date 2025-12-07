package jiraffe.core.basis;

public final class Input {

    public static float[] cursorPosition = new float[]{0,0};
    public static float[] prevCursorPosition = new float[]{0,0};

    public static boolean[] keys = new boolean[65536];
    public static boolean[] mouseButtons = new boolean[10];

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean isMouseButtonDown(int button) {
        return mouseButtons[button];
    }

    public static float[] getCursorPosition()
    {
        return new float[]{
                cursorPosition[0],
                cursorPosition[1],
        };
    }

    public static float[] getPrevCursorPosition()
    {
        return new float[]{
                prevCursorPosition[0],
                prevCursorPosition[1],
        };
    }
}