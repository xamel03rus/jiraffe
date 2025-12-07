package jiraffe.core.basis;

import jiraffe.core.geometry.Mesh;
import jiraffe.core.geometry.Transform;
import jiraffe.core.graphic.Material;

import java.util.ArrayList;
import java.util.List;

public class GameObject extends Transform {
    GameObject parent;
    List<GameObject> gameObjects = new ArrayList<>();

    Mesh mesh;
    Material material = new Material();

    public void setMesh(Mesh m) { mesh = m; }
    public Mesh getMesh() { return mesh; }

    public void setMaterial(Material m) { material = m; }
    public Material getMaterial() { return material; }

    public void add(GameObject newGameObject)
    {
        gameObjects.add(newGameObject);
    }

    public void remove(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
    }

    public List<GameObject> getChilds()
    {
        return gameObjects;
    }

    public void setParent(GameObject p)
    {
        parent = p;
    }

    public GameObject getParent()
    {
        return parent;
    }
}
