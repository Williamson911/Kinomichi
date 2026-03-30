package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;

import java.util.List;

public class AffichageService {

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

    public void afficherBanner() {
        System.out.println("***********************************************");
        System.out.println("* _  ___                       _      _     _ *");
        System.out.println("*| |/ (_)_ __   ___  _ __ ___ (_) ___| |__ (_)*");
        System.out.println("*| ' /| | '_ \\ / _ \\| '_ ` _ \\| |/ __| '_ \\| |*");
        System.out.println("*| . \\| | | | | (_) | | | | | | | (__| | | | |*");
        System.out.println("*|_|\\_\\_|_| |_|\\___/|_| |_| |_|_|\\___|_| |_|_|*");
        System.out.println("***********************************************");
    }
}