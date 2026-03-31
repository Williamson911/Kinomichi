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
            System.out.println("\n========================================");
            System.out.println("======== MENU KINOMICHI ================");
            System.out.println("========================================");
            System.out.println("0. Quitter");
            System.out.println("1. Charger un fichier JSON");
            System.out.println("2. Saisir un stage manuellement");
            System.out.println("3. Enregistrer les données");
            System.out.println("----------------------------------------");
            System.out.println("4. Définir les tarifs");
            System.out.println("5. Créer une plage horaire");
            System.out.println("6. Ajouter un participant");
            System.out.println("7. Inscrire un participant à une plage");
            System.out.println("8. Affecter un animateur à une plage");
            System.out.println("----------------------------------------");
            System.out.println("9.  Afficher participants et inscriptions");
            System.out.println("10. Afficher les plages");
            System.out.println("11. Voir les tarifs");
            System.out.println("12. Calculer le prix d'un participant");
            System.out.println("========================================");
            System.out.println("Choix :");

            switch (scanner.nextLine().trim()) {
                case "0" -> {
                    System.out.println("Au revoir !");
                    quitter = true;
                }
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
                case "4" -> {
                    if (verifierData(data)) data.setTypes(saisie.saisirTypes());
                }
                case "5" -> {
                    if (verifierData(data)) data.setPlages(saisie.saisirPlages());
                }
                case "6" -> {
                    if (verifierData(data)) saisie.ajouterParticipant(data);
                }
                case "7" -> {
                    if (verifierData(data)) saisie.inscrireParticipantPlage(data);
                }
                case "8" -> {
                    if (verifierData(data)) saisie.affecterAnimateur(data);
                }
                case "9" -> {
                    if (verifierData(data)) affichage.afficherRecapitulatif(data);
                }
                case "10" -> {
                    if (verifierData(data)) affichage.afficherPlages(data);
                }
                case "11" -> {
                    if (verifierData(data)) affichage.afficherTarifs(data);
                }
                case "12" -> {
                    if (verifierData(data)) affichage.calculerPrixParticipant(data, scanner);
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