package pl.lodz.p.pag.weglewski;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lodz.p.pag.weglewski.engine.Engine;
import pl.lodz.p.pag.weglewski.engine.file.ObjParser;
import pl.lodz.p.pag.weglewski.engine.model.Model;

public class LwjglStarter {
    static final Logger LOG = LoggerFactory.getLogger(Engine.class);

    public static void main(String[] args) {
        Model model = ObjParser.parse("src/main/resources/models/sphere.obj");
        new Engine().run();
    }
}