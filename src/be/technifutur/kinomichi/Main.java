package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        afficherBanner();

        Scanner scanner = new Scanner(System.in);
        StageData data = null;

        System.out.println("=== Gestion Stage Kinomichi ===\n");
        System.out.println("1 - Charger un fichier JSON");
        System.out.println("2 - Saisir manuellement");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> data = chargerJson(scanner);
            case "2" -> data = saisirManuellement(scanner);
            default  -> {
                System.out.println("Choix invalide.");
                return;
            }
        }

        if (data == null) return;

        afficherRecapitulatif(data);
        scanner.close();
    }


    static StageData chargerJson(Scanner scanner) {
        System.out.println("Nom du fichier JSON (dans le dossier data/) :");
        String nomFichier = scanner.nextLine();
        Path chemin = Paths.get("data", nomFichier);

        if (!Files.exists(chemin)) {
            System.out.println("⚠ Fichier introuvable : " + chemin.toAbsolutePath());
            return null;
        }

        try (java.io.FileReader reader = new java.io.FileReader(chemin.toFile())) {
            StageData data = new Gson().fromJson(reader, StageData.class);
            System.out.println("✅ Fichier chargé !");
            return data;
        } catch (IOException e) {
            System.out.println("⚠ Erreur de lecture : " + e.getMessage());
            return null;
        }
    }



    static StageData saisirManuellement(Scanner scanner) {
        StageData data = new StageData();

        System.out.println("Nom du stage :");
        data.setNom(scanner.nextLine());


        data.setTypes(saisirTypes(scanner));


        data.setPlages(saisirPlages(scanner));


        data.setParticipants(saisirParticipants(scanner, data));

        return data;
    }

    static List<TypeParticipant> saisirTypes(Scanner scanner) {
        List<TypeParticipant> types = new ArrayList<>();
        System.out.println("\n-- Définition des tarifs --");

        String continuer = "o";
        while (continuer.equalsIgnoreCase("o")) {
            System.out.println("Libellé du type (ex: Plein tarif) :");
            String libellé = scanner.nextLine();
            System.out.println("Prix par plage :");
            double prixPlage = Double.parseDouble(scanner.nextLine());
            System.out.println("Prix souper :");
            double prixSouper = Double.parseDouble(scanner.nextLine());
            System.out.println("Prix logement :");
            double prixLogement = Double.parseDouble(scanner.nextLine());
            System.out.println("Forfait tout compris :");
            double forfait = Double.parseDouble(scanner.nextLine());

            types.add(new TypeParticipant(libellé, prixPlage, prixSouper, prixLogement, forfait));

            System.out.println("Ajouter un autre type ? (o/n)");
            continuer = scanner.nextLine();
        }
        return types;
    }

    static List<PlageHoraire> saisirPlages(Scanner scanner) {
        List<PlageHoraire> plages = new ArrayList<>();
        System.out.println("\n-- Définition des plages horaires --");

        String continuer = "o";
        int numero = 1;
        while (continuer.equalsIgnoreCase("o")) {
            System.out.println("Jour (Samedi/Dimanche) :");
            String jour = scanner.nextLine();
            System.out.println("Heure début (ex: 09:00) :");
            String heureDebut = scanner.nextLine();
            System.out.println("Heure fin (ex: 10:30) :");
            String heureFin = scanner.nextLine();
            System.out.println("Animateur :");
            String animateur = scanner.nextLine();

            plages.add(new PlageHoraire(numero++, jour, heureDebut, heureFin, animateur));

            System.out.println("Ajouter une autre plage ? (o/n)");
            continuer = scanner.nextLine();
        }
        return plages;
    }

    static List<Participants> saisirParticipants(Scanner scanner, StageData data) {
        List<Participants> participants = new ArrayList<>();
        System.out.println("\n-- Saisie des participants --");

        String continuer = "o";
        while (continuer.equalsIgnoreCase("o")) {
            System.out.println("Nom :");
            String nom = scanner.nextLine();
            System.out.println("Prénom :");
            String prénom = scanner.nextLine();
            System.out.println("Téléphone :");
            String téléphone = scanner.nextLine();
            System.out.println("Email :");
            String email = scanner.nextLine();
            System.out.println("Club :");
            String club = scanner.nextLine();

            // Type (optionnel)
            System.out.println("Type de participant ? (o/n)");
            String avecType = scanner.nextLine();
            String type = null;
            if (avecType.equalsIgnoreCase("o")) {
                System.out.println("Types disponibles :");
                data.getTypes().forEach(t -> System.out.println("  - " + t.getLibellé()));
                System.out.println("Choisir un type :");
                type = scanner.nextLine();
            }

            System.out.println("Numéros de plages (séparés par des virgules, ou vide) :");
            String plagesInput = scanner.nextLine();
            List<Integer> plages = new ArrayList<>();
            if (!plagesInput.isBlank()) {
                for (String s : plagesInput.split(",")) {
                    plages.add(Integer.parseInt(s.trim()));
                }
            }

            // Souper uniquement possible sans plage
            System.out.println("Avec souper ? (o/n)");
            boolean avecSouper = scanner.nextLine().equalsIgnoreCase("o");

            System.out.println("Avec logement ? (o/n)");
            boolean avecLogement = scanner.nextLine().equalsIgnoreCase("o");

            participants.add(new Participants(nom, prénom, téléphone, email, club,
                    type, plages, avecSouper, avecLogement));

            System.out.println("Ajouter un autre participant ? (o/n)");
            continuer = scanner.nextLine();
        }
        return participants;
    }


    static void afficherRecapitulatif(StageData data) {

    }

    static void afficherBanner() {
        System.out.println("***********************************************");
        System.out.println("* _  ___                       _      _     _ *");
        System.out.println("*| |/ (_)_ __   ___  _ __ ___ (_) ___| |__ (_)*");
        System.out.println("*| ' /| | '_ \\ / _ \\| '_ ` _ \\| |/ __| '_ \\| |*");
        System.out.println("*| . \\| | | | | (_) | | | | | | | (__| | | | |*");
        System.out.println("*|_|\\_\\_|_| |_|\\___/|_| |_| |_|_|\\___|_| |_|_|*");
        System.out.println("***********************************************");
    }
}