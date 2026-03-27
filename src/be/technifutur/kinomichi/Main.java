package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;

import java.util.Optional;

public class Main {
    static void main(String args[]) {
        System.out.println("***********************************************\n" +
                "* _  ___                       _      _     _ *\n" +
                "*| |/ (_)_ __   ___  _ __ ___ (_) ___| |__ (_)*\n" +
                "*| ' /| | '_ \\ / _ \\| '_ ` _ \\| |/ __| '_ \\| |*\n" +
                "*| . \\| | | | | (_) | | | | | | | (__| | | | |*\n" +
                "*|_|\\_\\_|_| |_|\\___/|_| |_| |_|_|\\___|_| |_|_|*\n" +
                "***********************************************");


        Optional<StageData> optData = StageLoader.charger("Stage.json");

        if (optData.isEmpty()) {
            System.out.println("Impossible de charger le stage.");
            return;
        }

        StageData data = optData.get();

        System.out.println("╔══════════════════════════════════════╗");
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
            System.out.println("   Type      : " + p.getType());
            System.out.println("   Plages    : " + p.getPlages());
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
                System.out.println("   ⚠ Type inconnu : " + p.getType());
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

}