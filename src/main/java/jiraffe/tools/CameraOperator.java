package jiraffe.tools;

import jiraffe.core.basis.Camera;
import java.util.HashMap;
import java.util.Map;

public class CameraOperator {

    private Camera currentCamera;
    private static CameraOperator instance;
    private Map<String, Camera> cameras = new HashMap<>();

    public static CameraOperator getInstance()
    {
        if(instance == null)
            instance = new CameraOperator();

        return instance;
    }

    public void addCamera(String name, Camera camera)
    {
        if(cameras.size() == 0)
            currentCamera = camera;

        cameras.put(name, camera);
    }

    public void setCurrentCamera(String name)
    {
        if(!cameras.containsKey(name))
            System.out.println("error: cant find camera");

        currentCamera = cameras.get(name);
    }

    public Camera getCurrentCamera()
    {
        return currentCamera;
    }
}
