package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class StageLoader {

    private static final String DATA_DIR = "data";

    public static Optional<StageData> charger(String nomFichier) {
        Path chemin = Paths.get(DATA_DIR, nomFichier);

        if (!Files.exists(chemin)) {
            System.out.println("⚠ Fichier introuvable : " + chemin.toAbsolutePath());
            return Optional.empty();
        }

        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(chemin.toFile());
            StageData data = gson.fromJson(reader, StageData.class);
            reader.close();
            System.out.println("✅ Fichier chargé : " + chemin.toAbsolutePath());
            return Optional.of(data);
        } catch (IOException e) {
            System.out.println("⚠ Erreur de lecture : " + e.getMessage());
            return Optional.empty();
        }
    }
}