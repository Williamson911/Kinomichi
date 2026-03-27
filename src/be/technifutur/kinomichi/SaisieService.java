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

    private List<TypeParticipant> saisirTypes() {
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

    private List<PlageHoraire> saisirPlages() {
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

    private List<Participants> saisirParticipants(StageData data) {
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
            String type = null;
            if (scanner.nextLine().equalsIgnoreCase("o")) {
                List<TypeParticipant> types = data.getTypes();
                for (int i = 0; i < types.size(); i++) {
                    System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
                }
                System.out.println("Choisir un numéro :");
                int choix = Integer.parseInt(scanner.nextLine()) - 1;
                if (choix >= 0 && choix < types.size()) {
                    type = types.get(choix).getLibellé();
                } else {
                    System.out.println("⚠ Choix invalide, type non renseigné.");
                }
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
            String plagesInput = scanner.nextLine();
            List<Integer> plages = new ArrayList<>();

            if (!plagesInput.isBlank()) {
                for (String s : plagesInput.split(",")) {
                    int numero = Integer.parseInt(s.trim());
                    boolean existe = plagesDispo.stream()
                            .anyMatch(p -> p.getNumero() == numero);
                    if (existe) {
                        plages.add(numero);
                    } else {
                        System.out.println("⚠ Plage " + numero + " inexistante, ignorée.");
                    }
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
}