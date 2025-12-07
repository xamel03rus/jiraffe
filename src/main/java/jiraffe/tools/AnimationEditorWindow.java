package jiraffe.tools;

import jiraffe.core.basis.*;
import jiraffe.core.utils.ImportConfig;
import jiraffe.core.utils.Importer;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class AnimationEditorWindow extends Window {

    Scene scene = new Scene();

/*    @Override
    public void construct()
    {
        Engine.getInstance().addScene(scene);

        Camera camera = new Camera();
        CameraOperator.getInstance().addCamera("main",  camera);
    }*/

    @Override
    public void listen()
    {
        scene.acting();
        super.listen();

        if(Input.isKeyDown(GLFW.GLFW_KEY_F1)) {
            Input.keys[GLFW.GLFW_KEY_F1] = false;

            String filename = TinyFileDialogs.tinyfd_openFileDialog("Open File(s)", "", null, null, true);
            this.Import(filename);
        }

        if(Input.getCursorPosition()[0] > sizes[0] - 10)
            CameraOperator.getInstance().getCurrentCamera().transform.setPosition(
                    CameraOperator.getInstance().getCurrentCamera().transform.getPosition().add(new Vector3f(0, .1f, 0))
            );
    }

    public void Import(String filename)
    {
        ImportConfig config = new ImportConfig();
        config.scale = 0.0025f;

        try {
            scene.addGameObject(Importer.parse(filename, config));

            System.out.println("Model loaded");
        } catch (Exception exception) {
            System.out.println("Cant import model");
        }
    }

}
