package pl.lodz.p.pag.weglewski.engine.model;

import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 04.06.17.
 */
public class Model {
    List<Vector3f> vertices = new ArrayList<>();
    List<Vector3i> faces = new ArrayList<>();

    public void parseVertex(String line) {
        String[] splittedLine = line.split(" ");

        vertices.add(new Vector3f(Float.parseFloat(splittedLine[1]), Float.parseFloat(splittedLine[2]), Float.parseFloat(splittedLine[3])));
    }

    public void parseFace(String line) {
        String[] splittedLine = line.split(" ");

        faces.add(new Vector3i(Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3])));
    }
}
