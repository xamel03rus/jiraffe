package jiraffe.core.basis;

import jiraffe.core.geometry.Transform;

public class Camera {

    public Transform transform = new Transform();

    private float FOV = (float) Math.toRadians(60.0f);

    public void setFOV(float fov)
    {
        FOV = fov;
    }

    public float getFOV()
    {
        return FOV;
    }

}
