package be.technifutur.kinomichi;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Nom");
        String nom = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Prénom");
        String prénom = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Club");
        String club = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Email");
        String email = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Numéro de téléphone");
        String téléphone = scanner.nextLine();
        System.out.println("Nom: " + nom + " | Prénom: " + prénom +
                " | Club: " + club + " | Email: " + email +
                " | Téléphone: " + téléphone);

        scanner.close();
        Participants p = new Participants(nom, prénom, téléphone, email, club);
        System.out.println(p);
    }

}
