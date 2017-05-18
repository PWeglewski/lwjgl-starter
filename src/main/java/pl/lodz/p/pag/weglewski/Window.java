package pl.lodz.p.pag.weglewski;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import pl.lodz.p.pag.weglewski.config.Configuration;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by piotr on 04.05.17.
 */
public class Window implements Initializable, Closeable {
    // The handle handle
    private long handle;

    private String title;
    private int width;
    private int height;
    private boolean resizeable;
    private Vector4f clearColor;

    public Window(Configuration configuration) {
        this.title = configuration.getMainWindowTitle();
        this.width = configuration.getMainWindowWidth();
        this.height = configuration.getMainWindowHeight();
        this.resizeable = configuration.isMainWindowResizeable();
        this.clearColor = new Vector4f(configuration.getMainWindowClearColorR(),
                configuration.getMainWindowClearColorG(),
                configuration.getMainWindowClearColorB(),
                configuration.getMainWindowClearColorA());
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isResizeable() {
        return resizeable;
    }

    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    @Override
    public void close() {
        // Free the handle callbacks and destroy the handle
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
    }

    @Override
    public void init() {

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current handle hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the handle will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, isResizeable() ? GLFW_TRUE : GLFW_FALSE); // the handle will be resizable

        // Create the handle
        handle = glfwCreateWindow(getWidth(), getHeight(), getTitle(), NULL, NULL);
        if (handle == NULL)
            throw new RuntimeException("Failed to create the GLFW handle");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the handle size passed to glfwCreateWindow
            glfwGetWindowSize(handle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the handle
            glfwSetWindowPos(
                    handle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(handle);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the handle visible
        glfwShowWindow(handle);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
    }

    public long getHandle() {
        return handle;
    }

    public void setHandle(long handle) {
        this.handle = handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
