package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AffichageService affichage = new AffichageService(scanner);
        SaisieService saisie = new SaisieService(scanner);
        StageLoader loader = new StageLoader();

        affichage.afficherBanner();

        StageData data = null;
        boolean quitter = false;

        while (!quitter) {
            System.out.println("========================================");
            System.out.println("          MENU KINOMICHI");
            System.out.println("========================================");
            System.out.println("Stage actuel : " + (data != null ? data.getNom() : "aucun"));
            System.out.println();

            System.out.println("[Fichier]");
            System.out.println("1. Charger un fichier JSON");
            System.out.println("2. Saisir un stage manuellement");
            System.out.println("3. Enregistrer les données");

            System.out.println("\n[Tarifs]");
            System.out.println("4. Définir les tarifs");
            System.out.println("5. Modifier tarif");
            System.out.println("6. Supprimer tarif");

            System.out.println("\n[Plages]");
            System.out.println("7. Créer une plage horaire");
            System.out.println("8. Modifier une plage");
            System.out.println("9. Supprimer une plage");
            System.out.println("10. Inscrire un participant à une plage");
            System.out.println("11. Affecter un animateur à une plage");

            System.out.println("\n[Participants]");
            System.out.println("12. Ajouter un participant");
            System.out.println("13. Modifier un participant");
            System.out.println("14. Supprimer un participant");
            System.out.println("15. Afficher participants et inscriptions");
            System.out.println("16. Calculer le prix d'un participant");

            System.out.println("\n[Export]");
            System.out.println("17. Exporter un rapport texte/CSV");

            System.out.println("\n0. Quitter");
            System.out.println("========================================");
            System.out.print("Choix : ");

            switch (scanner.nextLine().trim()) {
                case "0" -> {
                    System.out.println("Au revoir !");
                    quitter = true;
                }

                // [Fichier]
                case "1" -> {
                    data = loader.charger(scanner);
                    if (data != null) System.out.println("✅ Stage chargé : " + data.getNom());
                }
                case "2" -> {
                    data = saisie.saisirManuellement();
                    if (data != null) System.out.println("✅ Stage saisi : " + data.getNom());
                }
                case "3" -> {
                    if (verifierData(data)) loader.sauvegarder(data);
                }

                // [Tarifs]
                case "4" -> {
                    if (verifierData(data)) data.setTypes(saisie.saisirTypes());
                }
                case "5" -> {
                    if (verifierData(data)) saisie.modifierType(data);
                }
                case "6" -> {
                    if (verifierData(data)) saisie.supprimerType(data);
                }

                // [Plages]
                case "7" -> {
                    if (verifierData(data)) data.setPlages(saisie.saisirPlages());
                }
                case "8" -> {
                    if (verifierData(data)) saisie.modifierPlage(data);
                }
                case "9" -> {
                    if (verifierData(data)) saisie.supprimerPlage(data);
                }
                case "10" -> {
                    if (verifierData(data)) saisie.inscrireParticipantPlage(data);
                }
                case "11" -> {
                    if (verifierData(data)) saisie.affecterAnimateur(data);
                }

                // [Participants]
                case "12" -> {
                    if (verifierData(data)) saisie.ajouterParticipant(data);
                }
                case "13" -> {
                    if (verifierData(data)) saisie.modifierParticipant(data);
                }
                case "14" -> {
                    if (verifierData(data)) saisie.supprimerParticipant(data);
                }
                case "15" -> {
                    if (verifierData(data)) affichage.afficherRecapitulatif(data);
                }
                case "16" -> {
                    if (verifierData(data)) affichage.calculerPrixParticipant(data, scanner);
                }

                // [Export]
                case "17" -> {
                    if (verifierData(data)) {
                        System.out.println("Nom du fichier (ex: rapport.txt ou rapport.csv) :");
                        String chemin = "data/" + scanner.nextLine().trim();

                        ExportService export = new ExportService();
                        if (chemin.endsWith(".csv")) {
                            export.exporterCSV(data, chemin);
                        } else {
                            export.exporterRapport(data, chemin);
                        }
                    }
                }

                default -> System.out.println("⚠ Choix invalide.");
            }
        }

        scanner.close();
    }

    static boolean verifierData(StageData data) {
        if (data == null) {
            System.out.println("⚠ Aucun stage chargé. Utilisez l'option 1 ou 2.");
            return false;
        }
        return true;
    }
}