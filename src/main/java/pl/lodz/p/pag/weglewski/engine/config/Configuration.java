package pl.lodz.p.pag.weglewski.engine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by piotr on 04.05.17.
 */
public class Configuration {
    static final String CONFIGURATION_FILE_NAME = "application.yml";

    static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private String mainWindowTitle;
    private int mainWindowWidth;
    private int mainWindowHeight;
    private boolean mainWindowResizeable;
    private float mainWindowClearColorR;
    private float mainWindowClearColorG;
    private float mainWindowClearColorB;
    private float mainWindowClearColorA;

    public float getMainWindowClearColorR() {
        return mainWindowClearColorR;
    }

    public void setMainWindowClearColorR(float mainWindowClearColorR) {
        this.mainWindowClearColorR = mainWindowClearColorR;
    }

    public float getMainWindowClearColorG() {
        return mainWindowClearColorG;
    }

    public void setMainWindowClearColorG(float mainWindowClearColorG) {
        this.mainWindowClearColorG = mainWindowClearColorG;
    }

    public float getMainWindowClearColorB() {
        return mainWindowClearColorB;
    }

    public void setMainWindowClearColorB(float mainWindowClearColorB) {
        this.mainWindowClearColorB = mainWindowClearColorB;
    }

    public float getMainWindowClearColorA() {
        return mainWindowClearColorA;
    }

    public void setMainWindowClearColorA(float mainWindowClearColorA) {
        this.mainWindowClearColorA = mainWindowClearColorA;
    }

    public int getMainWindowWidth() {
        return mainWindowWidth;
    }

    public void setMainWindowWidth(int mainWindowWidth) {
        this.mainWindowWidth = mainWindowWidth;
    }

    public int getMainWindowHeight() {
        return mainWindowHeight;
    }

    public void setMainWindowHeight(int mainWindowHeight) {
        this.mainWindowHeight = mainWindowHeight;
    }

    public boolean isMainWindowResizeable() {
        return mainWindowResizeable;
    }

    public void setMainWindowResizeable(boolean mainWindowResizeable) {
        this.mainWindowResizeable = mainWindowResizeable;
    }

    public String getMainWindowTitle() {
        return mainWindowTitle;
    }

    public void setMainWindowTitle(String mainWindowTitle) {
        this.mainWindowTitle = mainWindowTitle;
    }

    public static class ConfigurationLoader {
        public static Configuration load() {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                return mapper.readValue(new File("src/main/resources/config/" + CONFIGURATION_FILE_NAME), Configuration.class);
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
            return null;
        }
    }

}
