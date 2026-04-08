package be.technifutur.kinomichi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VersionManager {

    private static final Path CHEMIN_VERSION = Paths.get("data", "version.txt");
    private String version;

    public VersionManager() {
        this.version = calculerVersion();
        sauvegarder();
    }

    private String calculerVersion() {
        String dateAujourdhui = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        try {
            if (Files.exists(CHEMIN_VERSION)) {
                String contenu = Files.readString(CHEMIN_VERSION).trim();
                String[] parties = contenu.split("\\.");

                // Format attendu : yyyy.MM.dd.incrementation
                if (parties.length == 4) {
                    String dateVersion = parties[0] + "." + parties[1] + "." + parties[2];
                    int incrementation = Integer.parseInt(parties[3]);

                    if (dateVersion.equals(dateAujourdhui)) {
                        // Même jour → on incrémente
                        return dateAujourdhui + "." + (incrementation + 1);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("⚠ Impossible de lire la version, réinitialisée.");
        }

        // Nouveau jour ou premier lancement
        return dateAujourdhui + ".1";
    }

    private void sauvegarder() {
        try {
            Files.createDirectories(CHEMIN_VERSION.getParent());
            Files.writeString(CHEMIN_VERSION, version);
        } catch (IOException e) {
            System.out.println("⚠ Impossible de sauvegarder la version.");
        }
    }

    public String getVersion() {
        return version;
    }
}