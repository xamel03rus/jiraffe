package jiraffe.core.utils;

import jiraffe.core.basis.GameObject;
import jiraffe.core.geometry.Mesh;
import jiraffe.core.graphic.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Importer {

    public static GameObject parse(String resourcePath, ImportConfig config) throws Exception {
        GameObject newGameObject = new GameObject();
        newGameObject.setPosition(new Vector3f(0, 0, 1f));

        AIScene aiScene = Assimp.aiImportFile(resourcePath,
                Assimp.aiProcess_JoinIdenticalVertices |
                      Assimp.aiProcess_Triangulate |
                      Assimp.aiProcess_FixInfacingNormals
        );
        if (aiScene == null) {
            throw new Exception("Error loading model");
        }

        int numMaterials = aiScene.mNumMaterials();
        PointerBuffer aiMaterials = aiScene.mMaterials();
        List<Material> materials = new ArrayList<>();
/*        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
            processMaterial(aiMaterial, materials, texturesDir);
        }*/

        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        Mesh[] meshes = new Mesh[numMeshes];
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));

            GameObject subObject = new GameObject();
            subObject.setMesh(processMesh(aiMesh, materials, config));
            subObject.setParent(newGameObject);

            newGameObject.add(subObject);
        }

        return newGameObject;
    }

    private static Mesh processMesh(AIMesh aiMesh, List<Material> materials, ImportConfig config) {
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        processVertices(aiMesh, vertices, config);
        processNormals(aiMesh, normals);
        processTextCoords(aiMesh, textures);
        processIndices(aiMesh, indices);

        Mesh mesh = new Mesh(
            Utils.listVector3fToArray(vertices),
            Utils.listVector2fToArray(textures),
            Utils.listVector3fToArray(normals),
            Utils.listIntToArray(indices)
        );
        Material material;
        int materialIdx = aiMesh.mMaterialIndex();
        if (materialIdx >= 0 && materialIdx < materials.size()) {
            material = materials.get(materialIdx);
        } else {
            material = new Material();
        }
        //mesh.setMaterial(material);

        return mesh;
    }

    private static void processVertices(AIMesh aiMesh, List<Vector3f> vertices, ImportConfig config) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z()).mul(config.scale));
        }
    }

    private static void processNormals(AIMesh aiMesh, List<Vector3f> normals) {
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        while (aiNormals != null && aiNormals.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();

            normals.add(new Vector3f(aiNormal.x(), aiNormal.y(), aiNormal.z()));
        }
    }

    private static void processTextCoords(AIMesh aiMesh, List<Vector2f> textures) {
        AIVector3D.Buffer textCoords = aiMesh.mTextureCoords(0);
        int numTextCoords = textCoords != null ? textCoords.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textCoord = textCoords.get();
            textures.add(new Vector2f(textCoord.x(), textCoord.y()));
        }
    }

    private static void processIndices(AIMesh aiMesh, List<Integer> indices) {
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }
    }

}
