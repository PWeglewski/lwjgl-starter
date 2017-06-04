package pl.lodz.p.pag.weglewski.engine.file;

import pl.lodz.p.pag.weglewski.engine.model.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by piotr on 04.06.17.
 */
public class ObjParser {
    public static Model parse(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            Model model = new Model();

            String line = bufferedReader.readLine();
            while (line!=null){
                if (line.startsWith("v")){
                    model.parseVertex(line);
                }

                if(line.startsWith("f")){
                    model.parseFace(line);
                }

                line = bufferedReader.readLine();
            }
            return model;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
