package jiraffe.core.geometry;

import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public Transform()
    {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = 1f;
    }

    /**
     *
     * @param p - position of transform
     */
    public void setPosition(Vector3f p)
    {
        position = p;
    }

    /**
     *
     * @return Vector3f
     */
    public Vector3f getPosition()
    {
        return position;
    }

    /**
     *
     * @param r - rotation of transform
     */
    public void setRotation(Vector3f r)
    {
        rotation = r;
    }

    /**
     *
     * @return Vector3f
     */
    public Vector3f getRotation()
    {
        return rotation;
    }

    /**
     *
     * @param s - transform scale
     */
    public void setScale(float s)
    {
        scale = s;
    }

    public float getScale()
    {
        return scale;
    }
}
