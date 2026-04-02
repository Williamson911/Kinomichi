package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SaisieService {

    private final Scanner scanner;

    public SaisieService(Scanner scanner) {
        this.scanner = scanner;
    }

    public StageData saisirManuellement() {
        StageData data = new StageData();

        System.out.println("Nom du stage :");
        data.setNom(scanner.nextLine());
        data.setTypes(saisirTypes());
        data.setPlages(saisirPlages());
        data.setParticipants(saisirParticipants(data));

        return data;
    }

    List<TypeParticipant> saisirTypes() {
        List<TypeParticipant> types = new ArrayList<>();
        System.out.println("\n-- Définition des tarifs --");

        String continuer = "o";
        while (continuer.equalsIgnoreCase("o")) {
            System.out.println("Libellé du type (ex: Plein tarif) :");
            String libellé = scanner.nextLine();

            boolean existe = types.stream()
                    .anyMatch(t -> t.getLibellé().equalsIgnoreCase(libellé));
            if (existe) {
                System.out.println("⚠ Ce type existe déjà, ignoré.");
                continue;
            }

            System.out.println("Prix par plage :");
            double prixPlage = parseDouble(scanner.nextLine());
            System.out.println("Prix souper :");
            double prixSouper = parseDouble(scanner.nextLine());
            System.out.println("Prix logement :");
            double prixLogement = parseDouble(scanner.nextLine());
            System.out.println("Forfait tout compris :");
            double forfait = parseDouble(scanner.nextLine());

            types.add(new TypeParticipant(libellé, prixPlage, prixSouper, prixLogement, forfait));

            System.out.println("Ajouter un autre type ? (o/n)");
            continuer = scanner.nextLine();
        }
        return types;
    }

    List<PlageHoraire> saisirPlages() {
        List<PlageHoraire> plages = new ArrayList<>();
        System.out.println("\n-- Définition des plages horaires --");

        String continuer = "o";
        int numero = 1;
        while (continuer.equalsIgnoreCase("o")) {


            String jour = "";
            while (!jour.equalsIgnoreCase("Samedi") && !jour.equalsIgnoreCase("Dimanche")) {
                System.out.println("Jour (Samedi/Dimanche) :");
                jour = scanner.nextLine().trim();
                if (!jour.equalsIgnoreCase("Samedi") && !jour.equalsIgnoreCase("Dimanche")) {
                    System.out.println("⚠ Jour invalide, entrez 'Samedi' ou 'Dimanche'.");
                }
            }
            jour = Character.toUpperCase(jour.charAt(0)) + jour.substring(1).toLowerCase();


            String heureDebut = saisirHeure("Heure début");
            String heureFin   = saisirHeure("Heure fin");


            String animateur = saisirAvecValidation("Animateur :");

            plages.add(new PlageHoraire(numero++, jour, heureDebut, heureFin, animateur));

            System.out.println("Ajouter une autre plage ? (o/n)");
            continuer = scanner.nextLine();
        }
        return plages;
    }
    private String saisirAvecValidation(String message) {
        return saisirAvecValidation(message, "");
    }

    private String saisirAvecValidation(String message, String valeurActuelle) {
        String valeur = "";
        String affichage = valeurActuelle.isBlank() ? message : message + " [actuel : " + valeurActuelle + "]";
        while (valeur.isBlank()) {
            System.out.println(affichage + " :");
            valeur = scanner.nextLine().trim();
            if (valeur.isBlank()) {
                System.out.println("⚠ Ce champ ne peut pas être vide.");
            }
        }
        return valeur;
    }

    private String saisirEmail(String message) {
        return saisirEmail(message, "");
    }

    private String saisirEmail(String message, String valeurActuelle) {
        String email = "";
        String affichage = valeurActuelle.isBlank() ? message : message + " [actuel : " + valeurActuelle + "]";
        while (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println(affichage + " :");
            email = scanner.nextLine().trim();
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("⚠ Email invalide (ex: nom@domaine.be).");
            }
        }
        return email;
    }

    private String saisirTelephone(String message) {
        return saisirTelephone(message, "");
    }

    private String saisirTelephone(String message, String valeurActuelle) {
        String telephone = "";
        String affichage = valeurActuelle.isBlank() ? message : message + " [actuel : " + valeurActuelle + "]";
        while (!telephone.matches("^[0-9+\\s()-]{7,15}$")) {
            System.out.println(affichage + " :");
            telephone = scanner.nextLine().trim();
            if (!telephone.matches("^[0-9+\\s()-]{7,15}$")) {
                System.out.println("⚠ Téléphone invalide (ex: 0477123456).");
            }
        }
        return telephone;
    }

    private String saisirHeure(String message) {
        return saisirHeure(message, "");
    }

    private String saisirHeure(String message, String valeurActuelle) {
        String heure = "";
        String affichage = valeurActuelle.isBlank() ? message : message + " [actuel : " + valeurActuelle + "]";
        while (!heure.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            System.out.println(affichage + " (format HH:mm, ex: 09:00) :");
            heure = scanner.nextLine().trim();
            if (!heure.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                System.out.println("⚠ Format invalide, utilisez HH:mm (ex: 09:00).");
            }
        }
        return heure;
    }

    private List<Participants> saisirParticipants(StageData data) {
        List<Participants> participants = new ArrayList<>();
        System.out.println("\n-- Saisie des participants --");

        String continuer = "o";
        while (continuer.equalsIgnoreCase("o")) {

            String nom       = saisirAvecValidation("Nom :");
            String prénom    = saisirAvecValidation("Prénom :");
            String téléphone = saisirTelephone("Téléphone :");
            String email     = saisirEmail("Email :");
            String club      = saisirAvecValidation("Club :");


            System.out.println("Type de participant ? (o/n)");
            String type = null;
            if (scanner.nextLine().equalsIgnoreCase("o")) {
                List<TypeParticipant> types = data.getTypes();
                for (int i = 0; i < types.size(); i++) {
                    System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
                }
                int choix = -1;
                while (choix < 0 || choix >= types.size()) {
                    System.out.println("Choisir un numéro :");
                    try {
                        choix = Integer.parseInt(scanner.nextLine()) - 1;
                        if (choix < 0 || choix >= types.size()) {
                            System.out.println("⚠ Numéro invalide.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Entrez un numéro valide.");
                    }
                }
                type = types.get(choix).getLibellé();
            }


            List<PlageHoraire> plagesDispo = data.getPlages();
            System.out.println("Plages disponibles :");
            for (PlageHoraire plage : plagesDispo) {
                System.out.println("  " + plage.getNumero() + " - " + plage.getJour()
                        + " " + plage.getHeureDebut() + " → " + plage.getHeureFin()
                        + " (" + plage.getAnimateur() + ")");
            }

            System.out.println("Numéros de plages (séparés par des virgules, ou vide si souper uniquement) :");
            String plagesInput = scanner.nextLine().trim();
            List<Integer> plages = new ArrayList<>();

            if (!plagesInput.isBlank()) {
                boolean plagesValides = false;
                while (!plagesValides) {
                    plagesValides = true;
                    plages.clear();
                    for (String s : plagesInput.split(",")) {
                        try {
                            int numero = Integer.parseInt(s.trim());
                            boolean existe = plagesDispo.stream()
                                    .anyMatch(p -> p.getNumero() == numero);
                            if (existe) {
                                plages.add(numero);
                            } else {
                                System.out.println("⚠ Plage " + numero + " inexistante, ignorée.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠ '" + s.trim() + "' n'est pas un numéro valide, ignoré.");
                        }
                    }
                    if (plages.isEmpty() && !plagesInput.isBlank()) {
                        System.out.println("⚠ Aucune plage valide. Réessayez ou laissez vide pour souper uniquement :");
                        plagesInput = scanner.nextLine().trim();
                        plagesValides = plagesInput.isBlank();
                    }
                }
            }

            System.out.println("Avec souper ? (o/n)");
            boolean avecSouper = scanner.nextLine().equalsIgnoreCase("o");
            System.out.println("Avec logement ? (o/n)");
            boolean avecLogement = scanner.nextLine().equalsIgnoreCase("o");

            Participants p = confirmerParticipant(nom, prénom, téléphone, email, club,
                    type, plages, avecSouper, avecLogement, data);
            participants.add(p);

            System.out.println("Ajouter un autre participant ? (o/n)");
            continuer = scanner.nextLine();
        }
        return participants;
    }
    private double parseDouble(String input) {
        if (input == null || input.isBlank()) return 0;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠ Valeur invalide, 0 utilisé par défaut.");
            return 0;
        }
    }
    private Participants confirmerParticipant(String nom, String prénom, String téléphone,
                                              String email, String club, String type, List<Integer> plages,
                                              boolean avecSouper, boolean avecLogement, StageData data) {

        while (true) {
            System.out.println("\n-- Récapitulatif participant --");
            System.out.println("  1 - Nom       : " + nom);
            System.out.println("  2 - Prénom    : " + prénom);
            System.out.println("  3 - Téléphone : " + téléphone);
            System.out.println("  4 - Email     : " + email);
            System.out.println("  5 - Club      : " + club);
            System.out.println("  6 - Type      : " + (type != null ? type : "Non renseigné"));
            System.out.println("  7 - Plages    : " + (plages.isEmpty() ? "Souper uniquement" : plages));
            System.out.println("  8 - Souper    : " + (avecSouper   ? "oui" : "non"));
            System.out.println("  9 - Logement  : " + (avecLogement ? "oui" : "non"));
            System.out.println("\n  0 - Valider");
            System.out.println("Numéro du champ à corriger (ou 0 pour valider) :");

            String choix = scanner.nextLine().trim();
            switch (choix) {
                case "0" -> { return new Participants(nom, prénom, téléphone, email, club,
                        type, plages, avecSouper, avecLogement); }
                case "1" -> nom       = saisirAvecValidation("Nouveau nom", nom);
                case "2" -> prénom    = saisirAvecValidation("Nouveau prénom", prénom);
                case "3" -> téléphone = saisirTelephone("Nouveau téléphone", téléphone);
                case "4" -> email     = saisirEmail("Nouvel email", email);
                case "5" -> club      = saisirAvecValidation("Nouveau club", club);
                case "6" -> {
                    List<TypeParticipant> types = data.getTypes();

                    for (int i = 0; i < types.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
                    }
                    System.out.println("  0 - Aucun type");

                    while (true) {
                        System.out.println("Choisir un numéro :");

                        try {
                            int saisie = Integer.parseInt(scanner.nextLine());

                            if (saisie == 0) {
                                type = null;
                                break;
                            }

                            int index = saisie - 1;

                            if (index >= 0 && index < types.size()) {
                                type = types.get(index).getLibellé();
                                break;
                            } else {
                                System.out.println("⚠ Numéro invalide.");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("⚠ Entrez un numéro valide.");
                        }
                    }
                }
                case "7" -> {
                    List<PlageHoraire> plagesDispo = data.getPlages();
                    for (PlageHoraire plage : plagesDispo) {
                        System.out.println("  " + plage.getNumero() + " - " + plage.getJour()
                                + " " + plage.getHeureDebut() + " → " + plage.getHeureFin()
                                + " (" + plage.getAnimateur() + ")");
                    }
                    System.out.println("Numéros de plages (séparés par des virgules, ou vide) :");
                    String plagesInput = scanner.nextLine().trim();
                    plages = new ArrayList<>();
                    if (!plagesInput.isBlank()) {
                        for (String s : plagesInput.split(",")) {
                            try {
                                int numero = Integer.parseInt(s.trim());
                                boolean existe = plagesDispo.stream()
                                        .anyMatch(p -> p.getNumero() == numero);
                                if (existe) {
                                    plages.add(numero);
                                } else {
                                    System.out.println("⚠ Plage " + numero + " inexistante, ignorée.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("⚠ '" + s.trim() + "' invalide, ignoré.");
                            }
                        }
                    }
                }
                case "8" -> {
                    System.out.println("Avec souper ? (o/n)");
                    avecSouper = scanner.nextLine().equalsIgnoreCase("o");
                }
                case "9" -> {
                    System.out.println("Avec logement ? (o/n)");
                    avecLogement = scanner.nextLine().equalsIgnoreCase("o");
                }
                default -> System.out.println("⚠ Choix invalide.");
            }
        }

    }
    public void ajouterParticipant(StageData data) {
        if (data.getTypes() == null || data.getTypes().isEmpty()) {
            System.out.println("⚠ Définissez d'abord les tarifs (option 4).");
            return;
        }
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Définissez d'abord les plages (option 5).");
            return;
        }

        if (data.getParticipants() == null) {
            data.setParticipants(new ArrayList<>());
        }

        String nom       = saisirAvecValidation("Nom");
        String prénom    = saisirAvecValidation("Prénom");
        String téléphone = saisirTelephone("Téléphone");
        String email     = saisirEmail("Email");
        String club      = saisirAvecValidation("Club");

        // Type (optionnel)
        System.out.println("Type de participant ? (o/n)");
        String type = null;
        if (scanner.nextLine().equalsIgnoreCase("o")) {
            List<TypeParticipant> types = data.getTypes();
            for (int i = 0; i < types.size(); i++) {
                System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
            }
            int choix = -1;
            while (choix < 0 || choix >= types.size()) {
                System.out.println("Choisir un numéro :");
                try {
                    choix = Integer.parseInt(scanner.nextLine()) - 1;
                    if (choix < 0 || choix >= types.size()) {
                        System.out.println("⚠ Numéro invalide.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Entrez un numéro valide.");
                    choix = -1;
                }
            }
            type = types.get(choix).getLibellé();
        }

        // Plages
        List<PlageHoraire> plagesDispo = data.getPlages();
        System.out.println("Plages disponibles :");
        for (PlageHoraire plage : plagesDispo) {
            System.out.println("  " + plage.getNumero() + " - " + plage.getJour()
                    + " " + plage.getHeureDebut() + " → " + plage.getHeureFin()
                    + " (" + plage.getAnimateur() + ")");
        }

        System.out.println("Numéros de plages (séparés par des virgules, ou vide si souper uniquement) :");
        String plagesInput = scanner.nextLine().trim();
        List<Integer> plages = new ArrayList<>();

        if (!plagesInput.isBlank()) {
            for (String s : plagesInput.split(",")) {
                try {
                    int numero = Integer.parseInt(s.trim());
                    boolean existe = plagesDispo.stream()
                            .anyMatch(p -> p.getNumero() == numero);
                    if (existe) {
                        plages.add(numero);
                    } else {
                        System.out.println("⚠ Plage " + numero + " inexistante, ignorée.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠ '" + s.trim() + "' invalide, ignoré.");
                }
            }
        }

        System.out.println("Avec souper ? (o/n)");
        boolean avecSouper = scanner.nextLine().equalsIgnoreCase("o");
        System.out.println("Avec logement ? (o/n)");
        boolean avecLogement = scanner.nextLine().equalsIgnoreCase("o");

        Participants p = confirmerParticipant(nom, prénom, téléphone, email, club,
                type, plages, avecSouper, avecLogement, data);

        data.getParticipants().add(p);
        System.out.println("✅ Participant " + p.getPrénom() + " " + p.getNom() + " ajouté !");
    }
    public void inscrireParticipantPlage(StageData data) {
        if (data.getParticipants() == null || data.getParticipants().isEmpty()) {
            System.out.println("⚠ Aucun participant enregistré.");
            return;
        }
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage définie.");
            return;
        }

        // Choisir le participant
        System.out.println("Participants disponibles :");
        List<Participants> participants = data.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            System.out.println("  " + (i + 1) + " - "
                    + participants.get(i).getPrénom() + " "
                    + participants.get(i).getNom());
        }

        int choixP = -1;
        while (choixP < 0 || choixP >= participants.size()) {
            System.out.println("Choisir un numéro de participant :");
            try {
                choixP = Integer.parseInt(scanner.nextLine()) - 1;
                if (choixP < 0 || choixP >= participants.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choixP = -1;
            }
        }

        Participants participant = participants.get(choixP);

        // Afficher les plages disponibles (celles où il n'est pas encore inscrit)
        List<PlageHoraire> plagesDispo = data.getPlages().stream()
                .filter(pl -> !participant.getPlages().contains(pl.getNumero()))
                .collect(java.util.stream.Collectors.toList());

        if (plagesDispo.isEmpty()) {
            System.out.println("⚠ " + participant.getPrénom() + " " + participant.getNom()
                    + " est déjà inscrit à toutes les plages.");
            return;
        }

        System.out.println("Plages disponibles pour "
                + participant.getPrénom() + " " + participant.getNom() + " :");
        for (PlageHoraire plage : plagesDispo) {
            System.out.println("  " + plage.getNumero() + " - " + plage.getJour()
                    + " " + plage.getHeureDebut() + " → " + plage.getHeureFin()
                    + " (" + plage.getAnimateur() + ")");
        }

        System.out.println("Numéros de plages à ajouter (séparés par des virgules) :");
        String plagesInput = scanner.nextLine().trim();

        if (plagesInput.isBlank()) {
            System.out.println("⚠ Aucune plage saisie.");
            return;
        }

        int ajouts = 0;
        for (String s : plagesInput.split(",")) {
            try {
                int numero = Integer.parseInt(s.trim());
                boolean existe = plagesDispo.stream()
                        .anyMatch(pl -> pl.getNumero() == numero);
                if (existe && !participant.getPlages().contains(numero)) {
                    participant.getPlages().add(numero);
                    ajouts++;
                    System.out.println("✅ Inscrit à la plage " + numero + ".");
                } else if (participant.getPlages().contains(numero)) {
                    System.out.println("⚠ Déjà inscrit à la plage " + numero + ".");
                } else {
                    System.out.println("⚠ Plage " + numero + " inexistante, ignorée.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ '" + s.trim() + "' invalide, ignoré.");
            }
        }

        if (ajouts > 0) {
            System.out.println("✅ " + ajouts + " inscription(s) ajoutée(s) pour "
                    + participant.getPrénom() + " " + participant.getNom() + ".");
        }
    }
    public void affecterAnimateur(StageData data) {
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage définie.");
            return;
        }

        // Afficher les plages
        System.out.println("Plages disponibles :");
        List<PlageHoraire> plages = data.getPlages();
        for (int i = 0; i < plages.size(); i++) {
            System.out.println("  " + (i + 1) + " - Plage " + plages.get(i).getNumero()
                    + " | " + plages.get(i).getJour()
                    + " " + plages.get(i).getHeureDebut() + " → " + plages.get(i).getHeureFin()
                    + " | Animateur actuel : " + plages.get(i).getAnimateur());
        }

        // Choisir la plage
        int choixPlage = -1;
        while (choixPlage < 0 || choixPlage >= plages.size()) {
            System.out.println("Choisir un numéro de plage :");
            try {
                choixPlage = Integer.parseInt(scanner.nextLine()) - 1;
                if (choixPlage < 0 || choixPlage >= plages.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choixPlage = -1;
            }
        }

        PlageHoraire plage = plages.get(choixPlage);
        String nouvelAnimateur = saisirAvecValidation("Nouvel animateur", plage.getAnimateur());
        plage.setAnimateur(nouvelAnimateur);

        System.out.println("✅ Animateur de la plage " + plage.getNumero()
                + " mis à jour : " + nouvelAnimateur);
    }
    public void supprimerParticipant(StageData data) {
        if (data.getParticipants() == null || data.getParticipants().isEmpty()) {
            System.out.println("⚠ Aucun participant enregistré.");
            return;
        }

        System.out.println("Participants :");
        List<Participants> participants = data.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            System.out.println("  " + (i + 1) + " - "
                    + participants.get(i).getPrénom() + " "
                    + participants.get(i).getNom());
        }

        int choix = -1;
        while (choix < 0 || choix >= participants.size()) {
            System.out.println("Choisir un numéro de participant à supprimer :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= participants.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        Participants p = participants.get(choix);
        System.out.println("Confirmer la suppression de "
                + p.getPrénom() + " " + p.getNom() + " ? (o/n)");

        if (scanner.nextLine().equalsIgnoreCase("o")) {
            participants.remove(choix);
            System.out.println("✅ Participant supprimé. N'oubliez pas de sauvegarder (option 3).");
        } else {
            System.out.println("Suppression annulée.");
        }
    }
    public void modifierParticipant(StageData data) {
        if (data.getParticipants() == null || data.getParticipants().isEmpty()) {
            System.out.println("⚠ Aucun participant enregistré.");
            return;
        }

        System.out.println("Participants :");
        List<Participants> participants = data.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            System.out.println("  " + (i + 1) + " - "
                    + participants.get(i).getPrénom() + " "
                    + participants.get(i).getNom());
        }

        int choix = -1;
        while (choix < 0 || choix >= participants.size()) {
            System.out.println("Choisir un numéro de participant à modifier :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= participants.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        Participants p = participants.get(choix);

        // Réutiliser confirmerParticipant avec les valeurs actuelles
        Participants modifié = confirmerParticipant(
                p.getNom(), p.getPrénom(), p.getTéléphone(), p.getEmail(),
                p.getClub(), p.getType(), p.getPlages(),
                p.isAvecSouper(), p.isAvecLogement(), data);

        participants.set(choix, modifié);
        System.out.println("✅ Participant modifié. N'oubliez pas de sauvegarder (option 3).");
    }
    public void supprimerPlage(StageData data) {
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage définie.");
            return;
        }

        System.out.println("Plages :");
        List<PlageHoraire> plages = data.getPlages();
        for (int i = 0; i < plages.size(); i++) {
            System.out.println("  " + (i + 1) + " - Plage " + plages.get(i).getNumero()
                    + " | " + plages.get(i).getJour()
                    + " " + plages.get(i).getHeureDebut() + " → " + plages.get(i).getHeureFin()
                    + " (" + plages.get(i).getAnimateur() + ")");
        }

        int choix = -1;
        while (choix < 0 || choix >= plages.size()) {
            System.out.println("Choisir un numéro de plage à supprimer :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= plages.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        PlageHoraire plage = plages.get(choix);

        // Vérifier si des participants sont inscrits à cette plage
        long nbInscrits = data.getParticipants() == null ? 0 :
                data.getParticipants().stream()
                .filter(p -> p.getPlages().contains(plage.getNumero()))
                .count();

        if (nbInscrits > 0) {
            System.out.println("⚠ " + nbInscrits + " participant(s) inscrit(s) à cette plage.");
            System.out.println("Supprimer quand même ? (o/n)");
            if (!scanner.nextLine().equalsIgnoreCase("o")) {
                System.out.println("Suppression annulée.");
                return;
            }
            // Retirer la plage des inscriptions des participants
            data.getParticipants().forEach(p -> p.getPlages().remove(Integer.valueOf(plage.getNumero())));
            System.out.println("✅ Plage retirée des inscriptions des participants.");
        } else {
            System.out.println("Confirmer la suppression de la plage "
                    + plage.getNumero() + " ? (o/n)");
            if (!scanner.nextLine().equalsIgnoreCase("o")) {
                System.out.println("Suppression annulée.");
                return;
            }
        }

        plages.remove(choix);
        System.out.println("✅ Plage supprimée. N'oubliez pas de sauvegarder (option 3).");
    }
    public void modifierPlage(StageData data) {
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage définie.");
            return;
        }

        System.out.println("Plages :");
        List<PlageHoraire> plages = data.getPlages();
        for (int i = 0; i < plages.size(); i++) {
            System.out.println("  " + (i + 1) + " - Plage " + plages.get(i).getNumero()
                    + " | " + plages.get(i).getJour()
                    + " " + plages.get(i).getHeureDebut() + " → " + plages.get(i).getHeureFin()
                    + " (" + plages.get(i).getAnimateur() + ")");
        }

        int choix = -1;
        while (choix < 0 || choix >= plages.size()) {
            System.out.println("Choisir un numéro de plage à modifier :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= plages.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        PlageHoraire plage = plages.get(choix);

        while (true) {
            System.out.println("\n-- Récapitulatif plage --");
            System.out.println("  1 - Jour       : " + plage.getJour());
            System.out.println("  2 - Heure début: " + plage.getHeureDebut());
            System.out.println("  3 - Heure fin  : " + plage.getHeureFin());
            System.out.println("  4 - Animateur  : " + plage.getAnimateur());
            System.out.println("  0 - Valider");
            System.out.println("Numéro du champ à corriger (ou 0 pour valider) :");

            switch (scanner.nextLine().trim()) {
                case "0" -> {
                    System.out.println("✅ Plage modifiée. N'oubliez pas de sauvegarder (option 3).");
                    return;
                }
                case "1" -> {
                    String jour = "";
                    while (!jour.equalsIgnoreCase("Samedi") && !jour.equalsIgnoreCase("Dimanche")) {
                        System.out.println("Jour (Samedi/Dimanche) [actuel : " + plage.getJour() + "] :");
                        jour = scanner.nextLine().trim();
                        if (!jour.equalsIgnoreCase("Samedi") && !jour.equalsIgnoreCase("Dimanche")) {
                            System.out.println("⚠ Jour invalide.");
                        }
                    }
                    plage.setJour(Character.toUpperCase(jour.charAt(0)) + jour.substring(1).toLowerCase());
                }
                case "2" -> plage.setHeureDebut(saisirHeure("Heure début", plage.getHeureDebut()));
                case "3" -> plage.setHeureFin(saisirHeure("Heure fin", plage.getHeureFin()));
                case "4" -> plage.setAnimateur(saisirAvecValidation("Animateur", plage.getAnimateur()));
                default  -> System.out.println("⚠ Choix invalide.");
            }
        }
    }
    public void modifierType(StageData data) {
        if (data.getTypes() == null || data.getTypes().isEmpty()) {
            System.out.println("⚠ Aucun type défini.");
            return;
        }

        List<TypeParticipant> types = data.getTypes();

        System.out.println("Types existants :");
        for (int i = 0; i < types.size(); i++) {
            TypeParticipant t = types.get(i);
            System.out.println("  " + (i + 1) + " - " + t.getLibellé()
                    + " | Plage: " + t.getPrixParPlage()
                    + " | Souper: " + t.getPrixSouper()
                    + " | Logement: " + t.getPrixLogement()
                    + " | Forfait: " + t.getForfaitTout());
        }

        int choix = -1;
        while (choix < 0 || choix >= types.size()) {
            System.out.println("Choisir un type à modifier :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= types.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
            }
        }

        TypeParticipant type = types.get(choix);

        // Modification champ par champ
        while (true) {
            System.out.println("\n-- Modification du type --");
            System.out.println("  1 - Libellé      : " + type.getLibellé());
            System.out.println("  2 - Prix plage   : " + type.getPrixParPlage());
            System.out.println("  3 - Prix souper  : " + type.getPrixSouper());
            System.out.println("  4 - Prix logement: " + type.getPrixLogement());
            System.out.println("  5 - Forfait      : " + type.getForfaitTout());
            System.out.println("  0 - Valider");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "0" -> {
                    System.out.println("✅ Type modifié.");
                    return;
                }
                case "1" -> {
                    System.out.println("Nouveau libellé :");
                    String nouveau = scanner.nextLine().trim();

                    boolean existe = types.stream()
                            .anyMatch(t -> t != type && t.getLibellé().equalsIgnoreCase(nouveau));

                    if (existe) {
                        System.out.println("⚠ Ce libellé existe déjà.");
                    } else {
                        type.setLibellé(nouveau);
                    }
                }
                case "2" -> type.setPrixParPlage(parseDouble(scanner.nextLine()));
                case "3" -> type.setPrixSouper(parseDouble(scanner.nextLine()));
                case "4" -> type.setPrixLogement(parseDouble(scanner.nextLine()));
                case "5" -> type.setForfaitTout(parseDouble(scanner.nextLine()));
                default -> System.out.println("⚠ Choix invalide.");
            }
        }
    }
    public void supprimerType(StageData data) {
        if (data.getTypes() == null || data.getTypes().isEmpty()) {
            System.out.println("⚠ Aucun type défini.");
            return;
        }

        List<TypeParticipant> types = data.getTypes();

        System.out.println("Types existants :");
        for (int i = 0; i < types.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
        }

        int choix = -1;
        while (choix < 0 || choix >= types.size()) {
            System.out.println("Choisir un type à supprimer :");
            try {
                choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix < 0 || choix >= types.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
            }
        }

        TypeParticipant type = types.get(choix);

        // 🔥 Vérifier si utilisé par des participants
        long nbUtilisations = data.getParticipants() == null ? 0 :
                data.getParticipants().stream()
                .filter(p -> type.getLibellé().equalsIgnoreCase(p.getType()))
                .count();

        if (nbUtilisations > 0) {
            System.out.println("⚠ Ce type est utilisé par " + nbUtilisations + " participant(s).");
            System.out.println("Supprimer quand même ? (o/n)");

            if (!scanner.nextLine().equalsIgnoreCase("o")) {
                System.out.println("Suppression annulée.");
                return;
            }

            // 🔥 Nettoyage des participants
            data.getParticipants().forEach(p -> {
                if (type.getLibellé().equalsIgnoreCase(p.getType())) {
                    p.setType(null);
                }
            });

            System.out.println("✅ Type retiré des participants concernés.");
        }

        types.remove(choix);

        System.out.println("✅ Type supprimé. N'oubliez pas de sauvegarder (option 3).");
    }
}
