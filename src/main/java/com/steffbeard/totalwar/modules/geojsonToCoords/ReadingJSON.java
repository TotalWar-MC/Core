package com.steffbeard.totalwar.modules.geojsonToCoords;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.FileReader;
import java.io.IOException;


public class ReadingJSON {
    public static void main(String[] args) {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select a geoJSON file");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("geoJSON files", "json");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isDirectory()) {
                System.out.println("You selected the directory: " + jfc.getSelectedFile());
            }
        }

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        Object object;

        try {

            object = jsonParser.parse(new FileReader(jfc.getSelectedFile()));
            JSONObject jsonObject = (JSONObject) object;

            JSONArray features = (JSONArray) jsonObject.get("features");
            System.out.println("features: " + features);
            JSONObject unnestedFeatures = (JSONObject) features.get(0);
            System.out.println("\tUnnested Features: " + unnestedFeatures);
            JSONObject geometry = (JSONObject) unnestedFeatures.get("geometry");
            System.out.println("\tgeometry: " + geometry);
            JSONArray coordinates = (JSONArray) geometry.get("coordinates");
            System.out.println("\tcoordinates: " + coordinates);
            JSONArray unnestedCoordinates = (JSONArray) coordinates.get(0);
            System.out.println("\tunnestedCoordinates: " + unnestedCoordinates);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }
}