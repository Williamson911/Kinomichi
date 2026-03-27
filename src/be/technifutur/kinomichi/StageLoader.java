package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class StageLoader {

    private static final String DATA_DIR = "data";

    public StageData charger(Scanner scanner) {
        System.out.println("Nom du fichier JSON (dans le dossier data/) :");
        String nomFichier = scanner.nextLine();
        Path chemin = Paths.get(DATA_DIR, nomFichier);

        if (!Files.exists(chemin)) {
            System.out.println("⚠ Fichier introuvable : " + chemin.toAbsolutePath());
            return null;
        }

        try (java.io.FileReader reader = new java.io.FileReader(chemin.toFile())) {
            StageData data = new Gson().fromJson(reader, StageData.class);
            System.out.println("✅ Fichier chargé : " + chemin.toAbsolutePath());
            return data;
        } catch (IOException e) {
            System.out.println("⚠ Erreur de lecture : " + e.getMessage());
            return null;
        }
    }

    public void sauvegarder(StageData data) {
        Path chemin = Paths.get(DATA_DIR, "inscriptions.json");

        try {
            Files.createDirectories(chemin.getParent());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(chemin.toFile());
            gson.toJson(data, writer);
            writer.close();
            System.out.println("✅ Données sauvegardées dans : " + chemin.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠ Erreur de sauvegarde : " + e.getMessage());
        }
    }
}