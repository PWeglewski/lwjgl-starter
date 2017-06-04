package pl.lodz.p.pag.weglewski.engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lodz.p.pag.weglewski.engine.config.Closeable;
import pl.lodz.p.pag.weglewski.engine.config.Initializable;
import pl.lodz.p.pag.weglewski.engine.config.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by piotr on 04.05.17.
 */
public class Engine {
    static final Logger LOG = LoggerFactory.getLogger(Engine.class);
    List<Initializable> initializables = new ArrayList<>();
    List<Closeable> closeables = new ArrayList<>();

    private Configuration configuration;
    private Window window;

    public void run() {
        LOG.debug("Hello LWJGL " + Version.getVersion() + "!");

        initialize();
        loop();
        shutdown();
    }

    private void initialize() {
        // Load configuration from application.yml
        this.configuration = Configuration.ConfigurationLoader.load();
        window = new Window(configuration);
        initializables.add(window);

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        initializables.forEach(Initializable::init);
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window.getHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


            glfwSwapBuffers(window.getHandle()); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public void shutdown() {
        closeables.forEach(Closeable::close);
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
