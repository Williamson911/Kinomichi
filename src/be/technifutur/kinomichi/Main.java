package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AffichageService affichage = new AffichageService();
        SaisieService saisie = new SaisieService(scanner);
        StageLoader loader = new StageLoader();

        affichage.afficherBanner();
        System.out.println("=== Gestion Stage Kinomichi ===\n");
        System.out.println("1 - Charger un fichier JSON");
        System.out.println("2 - Saisir manuellement");

        StageData data = null;
        switch (scanner.nextLine()) {
            case "1" -> data = loader.charger(scanner);
            case "2" -> data = saisie.saisirManuellement();
            default  -> System.out.println("Choix invalide.");
        }

        if (data == null) {
            scanner.close();
            return;
        }

        affichage.afficherRecapitulatif(data);
        loader.sauvegarder(data);
        scanner.close();
    }
}