package be.technifutur.kinomichi;

import java.util.List;

public class Participants {
    private String nom;
    private String prénom;
    private String téléphone;
    private String email;
    private String club;
    private String type;
    private List<Integer> plages;
    private boolean avecSouper;
    private boolean avecLogement;


    public Participants() {}


    public Participants(String nom, String prénom, String téléphone, String email,
                        String club, String type, List<Integer> plages,
                        boolean avecSouper, boolean avecLogement) {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.email = email;
        this.club = club;
        this.type = type;
        this.plages = plages;
        this.avecSouper = avecSouper;
        this.avecLogement = avecLogement;
    }

    // Getters
    public String getNom()             { return nom; }
    public String getPrénom()          { return prénom; }
    public String getTéléphone()       { return téléphone; }
    public String getEmail()           { return email; }
    public String getClub()            { return club; }
    public String getType()            { return type; }
    public List<Integer> getPlages()   { return plages; }
    public boolean isAvecSouper()      { return avecSouper; }
    public boolean isAvecLogement()    { return avecLogement; }
}