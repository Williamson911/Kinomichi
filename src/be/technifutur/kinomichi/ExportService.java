package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

public class ExportService {

    private final CalculService calcul = new CalculService();

    public void exporterRapport(StageData data, String cheminFichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(cheminFichier))) {

            writer.println("=======================================");
            writer.println("        RAPPORT STAGE KINOMICHI");
            writer.println("=======================================\n");

            writer.println("Nom du stage : " + data.getNom());
            writer.println();

            // ----- TARIFS -----
            writer.println("----- TARIFS -----");
            if (data.getTypes() != null) {
                data.getTypes().forEach(t -> writer.println(
                        "- " + t.getLibellé()
                                + " | Plage: " + String.format(Locale.US, "%.2f", t.getPrixParPlage())
                                + " | Souper: " + String.format(Locale.US, "%.2f", t.getPrixSouper())
                                + " | Logement: " + String.format(Locale.US, "%.2f", t.getPrixLogement())
                                + " | Forfait: " + String.format(Locale.US, "%.2f", t.getForfaitTout())
                ));
            }
            writer.println();

            // ----- PLAGES HORAIRES -----
            writer.println("----- PLAGES HORAIRES -----");
            if (data.getPlages() != null) {
                data.getPlages().forEach(p -> writer.println(
                        "Plage " + p.getNumero() + " | "
                                + p.getJour() + " "
                                + p.getHeureDebut() + " - " + p.getHeureFin()
                                + " | Animateur: " + p.getAnimateur()
                ));
            }
            writer.println();

            // ----- PARTICIPANTS -----
            writer.println("----- PARTICIPANTS -----");
            if (data.getParticipants() != null) {
                data.getParticipants().stream()
                        .sorted(Comparator.comparing(Participants::getNom))
                        .forEach(p -> {
                            writer.println("---------------------------------------");
                            writer.println(p.getPrénom() + " " + p.getNom());
                            writer.println("Club      : " + p.getClub());
                            writer.println("Téléphone : " + p.getTéléphone());
                            writer.println("Email     : " + p.getEmail());
                            writer.println("Type      : " + (p.getType() != null ? p.getType() : "Non renseigné"));

                            // Plages lisibles
                            String plages = p.getPlages().isEmpty()
                                    ? "Souper uniquement"
                                    : p.getPlages().stream().map(Object::toString).collect(Collectors.joining(", "));
                            writer.println("Plages    : " + plages);

                            writer.println("Souper    : " + (p.isAvecSouper() ? "oui" : "non"));
                            writer.println("Logement  : " + (p.isAvecLogement() ? "oui" : "non"));

                            double prix = calcul.calculerPrixParticipant(p, data);
                            writer.println("TOTAL (€) : " + String.format(Locale.US, "%.2f", prix));
                        });
            }

            writer.println("\n=======================================");
            writer.println("          FIN DU RAPPORT");
            writer.println("=======================================");

            System.out.println("✅ Rapport exporté : " + cheminFichier);

        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'export : " + e.getMessage());
        }
    }

    public void exporterCSV(StageData data, String cheminFichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(cheminFichier))) {

            // En-tête CSV
            writer.println("Nom;Prénom;Club;Téléphone;Email;Type;Plages;Souper;Logement;Total");

            if (data.getParticipants() != null) {
                data.getParticipants().stream()
                        .sorted(Comparator.comparing(Participants::getNom))
                        .forEach(p -> {

                            String plages = p.getPlages().isEmpty()
                                    ? "Souper uniquement"
                                    : p.getPlages().stream().map(Object::toString).collect(Collectors.joining(", "));

                            double prix = calcul.calculerPrixParticipant(p, data);

                            writer.println(
                                    p.getNom() + ";" +
                                            p.getPrénom() + ";" +
                                            p.getClub() + ";" +
                                            p.getTéléphone() + ";" +
                                            p.getEmail() + ";" +
                                            (p.getType() != null ? p.getType() : "") + ";" +
                                            plages + ";" +
                                            (p.isAvecSouper() ? "oui" : "non") + ";" +
                                            (p.isAvecLogement() ? "oui" : "non") + ";" +
                                            String.format(Locale.US, "%.2f", prix)
                            );
                        });
            }

            System.out.println("✅ Export CSV réussi : " + cheminFichier);

        } catch (IOException e) {
            System.out.println("❌ Erreur export CSV : " + e.getMessage());
        }
    }
}