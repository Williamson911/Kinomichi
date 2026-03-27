package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import com.google.gson.Gson;

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


            boolean existe = types.stream()
                    .anyMatch(t -> t.getLibellé().equalsIgnoreCase(libellé));
            if (existe) {
                System.out.println("⚠ Ce type existe déjà, ignoré.");
            } else {
                // ... reste de la saisie des prix
                types.add(new TypeParticipant(libellé, prixPlage, prixSouper, prixLogement, forfait));
            }

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

            System.out.println("Type de participant ? (o/n)");
            String avecType = scanner.nextLine();
            String type = null;
            if (avecType.equalsIgnoreCase("o")) {
                System.out.println("Types disponibles :");
                List<TypeParticipant> types = data.getTypes();
                for (int i = 0; i < types.size(); i++) {
                    System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
                }
                System.out.println("Choisir un numéro :");
                int choixType = Integer.parseInt(scanner.nextLine()) - 1;
                if (choixType >= 0 && choixType < types.size()) {
                    type = types.get(choixType).getLibellé();
                } else {
                    System.out.println("⚠ Choix invalide, type non renseigné.");
                }
            }

            System.out.println("Numéros de plages (séparés par des virgules, ou vide) :");
            String plagesInput = scanner.nextLine();
            List<Integer> plages = new ArrayList<>();
            if (!plagesInput.isBlank()) {
                for (String s : plagesInput.split(",")) {
                    plages.add(Integer.parseInt(s.trim()));
                }
            }

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
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Stage : " + data.getNom());
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Nombre d'inscrits : " + data.getParticipants().size());
        System.out.println("──────────────────────────────────────");

        double totalGeneral = 0;

        for (Participants p : data.getParticipants()) {
            TypeParticipant typeP = data.getTypes().stream()
                    .filter(t -> t.getLibellé().equals(p.getType()))
                    .findFirst()
                    .orElse(null);

            System.out.println("\n👤 " + p.getPrénom() + " " + p.getNom());
            System.out.println("   Club      : " + p.getClub());
            System.out.println("   Email     : " + p.getEmail());
            System.out.println("   Téléphone : " + p.getTéléphone());
            System.out.println("   Type      : " + (p.getType() != null ? p.getType() : "Non renseigné"));
            System.out.println("   Plages    : " + (p.getPlages().isEmpty() ? "Aucune" : p.getPlages()));
            System.out.println("   Souper    : " + (p.isAvecSouper()   ? "oui" : "non"));
            System.out.println("   Logement  : " + (p.isAvecLogement() ? "oui" : "non"));

            if (typeP != null) {
                double total = typeP.calculerTotal(
                        p.getPlages().size(),
                        p.isAvecSouper(),
                        p.isAvecLogement()
                );
                System.out.println("   Total dû  : " + total + " €");
                totalGeneral += total;
            } else {
                System.out.println("   Total dû  : non calculable (type non renseigné)");
            }

            System.out.println("──────────────────────────────────────");
        }

        System.out.println("\n💰 Total général : " + totalGeneral + " €");

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Récapitulatif par plage              ║");
        System.out.println("╚══════════════════════════════════════╝");

        for (PlageHoraire plage : data.getPlages()) {
            long nbInscrits = data.getParticipants().stream()
                    .filter(p -> p.getPlages().contains(plage.getNumero()))
                    .count();

            System.out.println("\n📅 Plage " + plage.getNumero() + " — " + plage.getJour());
            System.out.println("   Horaire   : " + plage.getHeureDebut() + " → " + plage.getHeureFin());
            System.out.println("   Animateur : " + plage.getAnimateur());
            System.out.println("   Inscrits  : " + nbInscrits);
            System.out.println("──────────────────────────────────────");
        }
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