package jiraffe.core.basis;

import java.util.ArrayList;

public class Scene {

    private final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<GameObject> getGameObjectList() {
        return gameObjects;
    }

    public void acting() {

    }
}
