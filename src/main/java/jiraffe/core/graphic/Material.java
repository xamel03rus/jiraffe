package jiraffe.core.graphic;

import org.joml.Vector4f;

public class Material {

    private Shader shader;
    private Vector4f color = new Vector4f(1f, 0f, 0f, 1f);

    public Material()
    {
        shader = Render.getInstance().getShader("default");
    }

    public void setShader(Shader sh)
    {
        shader = sh;
    }

    public Shader getShader()
    {
        return shader;
    }

    public void setColor(Vector4f c)
    {
        color = c;
    }

    public Vector4f getColor() { return color;}

}
