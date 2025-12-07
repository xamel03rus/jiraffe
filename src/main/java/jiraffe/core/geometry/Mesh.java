package jiraffe.core.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Mesh {

    private int vao;
    private int index;
    private int vertex;
    private int tvertex;
    private int nvertex;

    private int renderType;

    protected Vector3f[] vertices;
    protected int[] indexes;
    protected Vector2f[] textureCoords;
    protected Vector3f[] vnormals;

    public Mesh() {
        renderType = GL_STATIC_DRAW;
    }
    public Mesh(Vector3f[] newVertices, Vector2f[] newTextureCoords, Vector3f[] newNormals, int[] newIndexes) {
        renderType = GL_STATIC_DRAW;

        vertices = newVertices;
        textureCoords = newTextureCoords;
        indexes = newIndexes;
        vnormals = newNormals;
    }

    // getters buffers id
    public int getVao()
    {
        return vao;
    }
    public int getVboVertices()
    {
        return vertex;
    }

    public int getVboIndexes()
    {
        return index;
    }
    public int getVboTextureCoords()
    {
        return tvertex;
    }
    public int getVboNormals()
    {
        return nvertex;
    }
    public int getRenderType() {return renderType;}

    // getters data
    public Vector3f[] getVertices()
    {
        return vertices;
    }
    public int[] getIndexes()
    {
        return indexes;
    }
    public Vector2f[] getTextureCoords()
    {
        return textureCoords;
    }
    public Vector3f[] getNormals()
    {
        return vnormals;
    }

    // setters buffer ids
    public void setVao(int id)
    {
        vao = id;
    }
    public void setVboVertices(int id)
    {
        vertex = id;
    }
    public void setVboIndexes(int id)
    {
        index = id;
    }
    public void setVboTextureCoords(int id)
    {
        tvertex = id;
    }
    public void setVboNormals(int vn)
    {
        nvertex = vn;
    }

    public void setRenderType(int renderType) {
        this.renderType = renderType;
    }

    // setters data
    public void setVertices(Vector3f[] vert)
    {
        vertices = vert;
    }
    public void setIndexes(int[] indexs)
    {
        indexes = indexs;
    }
    public void setTextureCoords(Vector2f [] textCoords)
    {
        textureCoords = textCoords;
    }
    public void setNormals(Vector3f[] n)
    {
        vnormals = n;
    }
    // getters buffers id
}
