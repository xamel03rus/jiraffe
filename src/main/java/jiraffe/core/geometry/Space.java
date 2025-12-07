package jiraffe.core.geometry;

import jiraffe.core.basis.Camera;
import jiraffe.core.basis.GameObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public enum Space {

    INSTANCE;

    public static Space getInstance()
    {
        return INSTANCE;
    }

    private Matrix4f projectionSpace = new Matrix4f();
    private Matrix4f modelSpace = new Matrix4f();
    private Matrix4f viewSpace = new Matrix4f();

    private Vector3f allPosition = new Vector3f();
    private Vector3f allRotation = new Vector3f();
    private float allScale = 1;

    //getters
    public final Matrix4f getProjectionSpace() {
        return projectionSpace;
    }

    public Matrix4f getModelSpace() {
        return modelSpace;
    }

    public Matrix4f getViewSpace()
    {
        return viewSpace;
    }

    // setters
    public void setViewSpace(Camera camera) {
        viewSpace.identity()
                .translate(camera.transform.getPosition())
                .rotateXYZ(camera.transform.getRotation());
    }

    public void setProjectionSpace(float fov, float aspect, float near, float far) {
        projectionSpace = new Matrix4f().perspective(fov, aspect, near, far);
    }

    public void setModelSpace(GameObject object) {
        allPosition = new Vector3f();
        allRotation = new Vector3f();
        allScale = 1;

        getAllTransforms(object);

        modelSpace.identity()
                .translate(allPosition)
                .rotateXYZ(allRotation)
                .scale(allScale);
    }

    public void getAllTransforms(GameObject object)
    {
        GameObject parent = object.getParent();

        allPosition.add(object.getPosition());
        allRotation.add(object.getRotation());
        allScale *= object.getScale();

        if(parent != null) {
            getAllTransforms(parent);
        }
    }
}