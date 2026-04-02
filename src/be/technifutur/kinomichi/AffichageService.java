package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;

import java.util.List;
import java.util.Scanner;

public class AffichageService {

    private final Scanner scanner;

    public AffichageService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void afficherBanner() {
        System.out.println("***********************************************");
        System.out.println("* _  ___                       _      _     _ *");
        System.out.println("*| |/ (_)_ __   ___  _ __ ___ (_) ___| |__ (_)*");
        System.out.println("*| ' /| | '_ \\ / _ \\| '_ ` _ \\| |/ __| '_ \\| |*");
        System.out.println("*| . \\| | | | | (_) | | | | | | | (__| | | | |*");
        System.out.println("*|_|\\_\\_|_| |_|\\___/|_| |_| |_|_|\\___|_| |_|_|*");
        System.out.println("***********************************************");
    }

    public void afficherRecapitulatif(StageData data) {
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
            System.out.println("   Plages    : " + (p.getPlages().isEmpty() ? "Souper uniquement" : p.getPlages()));
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
        afficherRecapPlages(data);
    }

    private void afficherRecapPlages(StageData data) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Récapitulatif par plage              ║");
        System.out.println("╚══════════════════════════════════════╝");

        for (PlageHoraire plage : data.getPlages()) {
            List<Participants> inscrits = data.getParticipants().stream()
                    .filter(p -> p.getPlages().contains(plage.getNumero()))
                    .collect(java.util.stream.Collectors.toList());

            System.out.println("\n📅 Plage " + plage.getNumero() + " — " + plage.getJour());
            System.out.println("   Horaire   : " + plage.getHeureDebut() + " → " + plage.getHeureFin());
            System.out.println("   Animateur : " + plage.getAnimateur());
            System.out.println("   Inscrits  : " + inscrits.size());

            if (inscrits.isEmpty()) {
                System.out.println("   Participants : aucun");
            } else {
                System.out.println("   Participants :");
                for (Participants p : inscrits) {
                    System.out.println("      - " + p.getPrénom() + " " + p.getNom()
                            + " (" + (p.getType() != null ? p.getType() : "type non renseigné") + ")");
                }
            }

            System.out.println("──────────────────────────────────────");
        }
    }

    public void afficherPlages(StageData data) {
        if (data.getPlages() == null || data.getPlages().isEmpty()) {
            System.out.println("⚠ Aucune plage définie.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Plages horaires                      ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("\n📅 Samedi");
        System.out.println("──────────────────────────────────────");
        data.getPlages().stream()
                .filter(p -> p.getJour().equalsIgnoreCase("Samedi"))
                .forEach(p -> {
                    long nbInscrits = data.getParticipants() == null ? 0 :
                            data.getParticipants().stream()
                            .filter(pa -> pa.getPlages().contains(p.getNumero()))
                            .count();
                    System.out.println("  Plage " + p.getNumero()
                            + " | " + p.getHeureDebut() + " → " + p.getHeureFin()
                            + " | Animateur : " + (p.getAnimateur() != null
                            ? p.getAnimateur().getPrénom() + " " + p.getAnimateur().getNom()
                            : "aucun")
                            + " | Inscrits : " + nbInscrits);
                });

        System.out.println("\n📅 Dimanche");
        System.out.println("──────────────────────────────────────");
        data.getPlages().stream()
                .filter(p -> p.getJour().equalsIgnoreCase("Dimanche"))
                .forEach(p -> {
                    long nbInscrits = data.getParticipants() == null ? 0 :
                            data.getParticipants().stream()
                            .filter(pa -> pa.getPlages().contains(p.getNumero()))
                            .count();
                    System.out.println("  Plage " + p.getNumero()
                            + " | " + p.getHeureDebut() + " → " + p.getHeureFin()
                            + " | Animateur : " + p.getAnimateur()
                            + " | Inscrits : " + nbInscrits);
                });
    }

    public void afficherTarifs(StageData data) {
        if (data.getTypes() == null || data.getTypes().isEmpty()) {
            System.out.println("⚠ Aucun tarif défini.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Tarifs                               ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println(String.format("%-20s %-10s %-10s %-10s %-10s",
                "Type", "Plage", "Souper", "Logement", "Forfait"));
        System.out.println("──────────────────────────────────────────────────────");

        for (TypeParticipant type : data.getTypes()) {
            System.out.println(String.format("%-20s %-10s %-10s %-10s %-10s",
                    type.getLibellé(),
                    type.getPrixParPlage() + " €",
                    type.getPrixSouper() + " €",
                    type.getPrixLogement() + " €",
                    type.getForfaitTout() + " €"));
        }
    }

    public void calculerPrixParticipant(StageData data, Scanner scanner) {
        if (data.getParticipants() == null || data.getParticipants().isEmpty()) {
            System.out.println("⚠ Aucun participant enregistré.");
            return;
        }

        System.out.println("Participants disponibles :");
        List<Participants> participants = data.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            System.out.println("  " + (i + 1) + " - "
                    + participants.get(i).getPrénom() + " "
                    + participants.get(i).getNom());
        }

        int choix = -1;
        while (choix < 0 || choix >= participants.size()) {
            System.out.println("Choisir un numéro de participant :");
            try {
                choix = Integer.parseInt(this.scanner.nextLine()) - 1;
                if (choix < 0 || choix >= participants.size()) {
                    System.out.println("⚠ Numéro invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un numéro valide.");
                choix = -1;
            }
        }

        Participants p = participants.get(choix);


        TypeParticipant typeP = data.getTypes() == null ? null :
                data.getTypes().stream()
                .filter(t -> t.getLibellé().equals(p.getType()))
                .findFirst()
                .orElse(null);

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Calcul du prix                       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("👤 " + p.getPrénom() + " " + p.getNom());
        System.out.println("   Type     : " + (p.getType() != null ? p.getType() : "Non renseigné"));
        System.out.println("   Plages   : " + (p.getPlages().isEmpty() ? "Souper uniquement" : p.getPlages()));
        System.out.println("   Souper   : " + (p.isAvecSouper()   ? "oui" : "non"));
        System.out.println("   Logement : " + (p.isAvecLogement() ? "oui" : "non"));
        System.out.println("──────────────────────────────────────");

        // Si pas de type, proposer d'en choisir un temporairement
        if (typeP == null) {
            System.out.println("⚠ Pas de type renseigné pour ce participant.");
            System.out.println("Choisir un type pour le calcul ? (o/n)");
            if (this.scanner.nextLine().equalsIgnoreCase("o")) {
                List<TypeParticipant> types = data.getTypes();
                for (int i = 0; i < types.size(); i++) {
                    System.out.println("  " + (i + 1) + " - " + types.get(i).getLibellé());
                }
                int choixType = -1;
                while (choixType < 0 || choixType >= types.size()) {
                    System.out.println("Choisir un numéro :");
                    try {
                        choixType = Integer.parseInt(this.scanner.nextLine()) - 1;
                        if (choixType < 0 || choixType >= types.size()) {
                            System.out.println("⚠ Numéro invalide.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Entrez un numéro valide.");
                        choixType = -1;
                    }
                }
                typeP = types.get(choixType);
                System.out.println("(Calcul basé sur le tarif : " + typeP.getLibellé() + ")");
            } else {
                System.out.println("⚠ Calcul impossible sans type.");
                return;
            }
        }

        int nbPlages = p.getPlages().size();
        double coutPlages   = nbPlages * typeP.getPrixParPlage();
        double coutSouper   = p.isAvecSouper()   ? typeP.getPrixSouper()   : 0;
        double coutLogement = p.isAvecLogement() ? typeP.getPrixLogement() : 0;
        double sansForfait  = coutPlages + coutSouper + coutLogement;
        double total        = typeP.calculerTotal(nbPlages, p.isAvecSouper(), p.isAvecLogement());

        System.out.println("   " + nbPlages + " plage(s) × " + typeP.getPrixParPlage() + " € = " + coutPlages + " €");
        if (p.isAvecSouper())   System.out.println("   Souper                          = " + coutSouper + " €");
        if (p.isAvecLogement()) System.out.println("   Logement                        = " + coutLogement + " €");
        System.out.println("──────────────────────────────────────");

        if (nbPlages > 0 && total < sansForfait) {
            System.out.println("   Sans forfait : " + sansForfait + " €");
            System.out.println("   ✅ Forfait appliqué : " + total + " €");
        } else {
            System.out.println("   Total dû : " + total + " €");
        }
        System.out.println("\nAssigner ce type au participant de façon permanente ? (o/n)");
        if (scanner.nextLine().equalsIgnoreCase("o")) {
            p.setType(typeP.getLibellé());
            System.out.println("✅ Type '" + typeP.getLibellé() + "' assigné à "
                    + p.getPrénom() + " " + p.getNom() + ".");
            System.out.println("N'oubliez pas de sauvegarder (option 3).");
        }
    }
}