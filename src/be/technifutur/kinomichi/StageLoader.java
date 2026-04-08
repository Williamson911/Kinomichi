package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class StageLoader {

    private static final String DATA_DIR = "data";

    public StageData charger(Scanner scanner) {
        Path dossier = Paths.get(DATA_DIR);

        // Lister les fichiers JSON disponibles
        List<Path> fichiers;
        try {
            fichiers = Files.list(dossier)
                    .filter(f -> f.toString().endsWith(".json"))
                    .sorted()
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.out.println("⚠ Impossible de lire le dossier data/ : " + e.getMessage());
            return null;
        }

        if (fichiers.isEmpty()) {
            System.out.println("⚠ Aucun fichier JSON trouvé dans data/");
            return null;
        }

        // Afficher la liste
        System.out.println("Fichiers disponibles :");
        for (int i = 0; i < fichiers.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + fichiers.get(i).getFileName());
        }

        // Choisir
        int choix = -1;
        while (choix < 0 || choix >= fichiers.size()) {
            System.out.println("Choisir un numéro :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= fichiers.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        Path chemin = fichiers.get(choix);

        try (java.io.FileReader reader = new java.io.FileReader(chemin.toFile(), java.nio.charset.StandardCharsets.UTF_8)) {
            StageData data = new Gson().fromJson(reader, StageData.class);
            System.out.println("✅ Fichier chargé : " + chemin.getFileName());

            if (!valider(data)) {
                System.out.println("\n⚠ Le fichier contient des erreurs, vérifiez les données.");
                return null;
            }

            System.out.println("✅ Données valides !");
            return data;
        } catch (IOException e) {
            System.out.println("⚠ Erreur de lecture : " + e.getMessage());
            return null;
        }
    }

    public void sauvegarder(StageData data) {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));

            // Nom horodaté pour le versioning
            String horodatage = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String nomVersionné = "inscriptions_" + horodatage + ".json";

            Path cheminVersionné = Paths.get(DATA_DIR, nomVersionné);
            Path cheminPrincipal = Paths.get(DATA_DIR, "inscriptions.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Sauvegarde versionnée
            try (FileWriter writer = new FileWriter(cheminVersionné.toFile(), java.nio.charset.StandardCharsets.UTF_8)) {
                gson.toJson(data, writer);
            }
            System.out.println("✅ Version sauvegardée : " + nomVersionné);

            // Mise à jour du fichier principal
            try (FileWriter writer = new FileWriter(cheminPrincipal.toFile(), java.nio.charset.StandardCharsets.UTF_8)) {
                gson.toJson(data, writer);
            }
            System.out.println("✅ Fichier principal mis à jour : inscriptions.json");

        } catch (IOException e) {
            System.out.println("⚠ Erreur de sauvegarde : " + e.getMessage());
        }
    }
    public boolean valider(StageData data) {
        boolean valide = true;

        // Vérification du nom du stage
        if (data.getNom() == null || data.getNom().isBlank()) {
            System.out.println("⚠ Le nom du stage est manquant.");
            valide = false;
        }

        // Vérification des types
        if (data.getTypes() == null || data.getTypes().isEmpty()) {
            System.out.println("⚠ Aucun type de participant défini.");
            valide = false;
        } else {
            for (TypeParticipant type : data.getTypes()) {
                if (type.getLibellé() == null || type.getLibellé().isBlank()) {
                    System.out.println("⚠ Un type de participant a un libellé manquant.");
                    valide = false;
                }
                if (type.getPrixParPlage() < 0 || type.getPrixSouper() < 0
                        || type.getPrixLogement() < 0 || type.getForfaitTout() < 0) {
                    System.out.println("⚠ Type '" + type.getLibellé() + "' : un prix ne peut pas être négatif.");
                    valide = false;
                }
            }
        }

        // Vérification des plages
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage horaire définie.");
            valide = false;
        } else {
            for (PlageHoraire plage : data.getPlages()) {
                if (plage.getJour() == null || (!plage.getJour().equalsIgnoreCase("Samedi")
                        && !plage.getJour().equalsIgnoreCase("Dimanche"))) {
                    System.out.println("⚠ Plage " + plage.getNumero() + " : jour invalide (Samedi/Dimanche attendu).");
                    valide = false;
                }
                if (plage.getHeureDebut() == null || !plage.getHeureDebut().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    System.out.println("⚠ Plage " + plage.getNumero() + " : heure de début invalide.");
                    valide = false;
                }
                if (plage.getHeureFin() == null || !plage.getHeureFin().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    System.out.println("⚠ Plage " + plage.getNumero() + " : heure de fin invalide.");
                    valide = false;
                }
                if (plage.getAnimateur() == null
                        || plage.getAnimateur().getPrénom().isBlank()
                        || plage.getAnimateur().getNom().isBlank()) {
                    System.out.println("⚠ Plage " + plage.getNumero() + " : animateur manquant ou incomplet.");
                    valide = false;
                }
            }
        }

        // Vérification des participants
        if (data.getParticipants() == null || data.getParticipants().isEmpty()) {
            System.out.println("⚠ Aucun participant dans le fichier.");
            valide = false;
        } else {
            for (Participants p : data.getParticipants()) {
                if (p.getNom() == null || p.getNom().isBlank()) {
                    System.out.println("⚠ Un participant a un nom manquant.");
                    valide = false;
                }
                if (p.getPrénom() == null || p.getPrénom().isBlank()) {
                    System.out.println("⚠ Participant '" + p.getNom() + "' : prénom manquant.");
                    valide = false;
                }
                if (p.getEmail() == null || !p.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    System.out.println("⚠ Participant '" + p.getNom() + "' : email invalide.");
                    valide = false;
                }
                if (p.getType() != null && data.getTypes().stream()
                        .noneMatch(t -> t.getLibellé().equals(p.getType()))) {
                    System.out.println("⚠ Participant '" + p.getNom() + "' : type '" + p.getType() + "' introuvable.");
                    valide = false;
                }
                if (p.getPlages() != null) {
                    for (int numero : p.getPlages()) {
                        boolean existe = data.getPlages().stream()
                                .anyMatch(pl -> pl.getNumero() == numero);
                        if (!existe) {
                            System.out.println("⚠ Participant '" + p.getNom() + "' : plage " + numero + " inexistante.");
                            valide = false;
                        }
                    }
                }
            }
        }

        return valide;
    }

}