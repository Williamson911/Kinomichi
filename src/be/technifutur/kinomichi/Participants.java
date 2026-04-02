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

    public String getNom()             { return nom; }
    public String getPrénom()          { return prénom; }
    public String getTéléphone()       { return téléphone; }
    public String getEmail()           { return email; }
    public String getClub()            { return club; }
    public String getType()            { return type; }
    public List<Integer> getPlages()   { return plages; }
    public boolean isAvecSouper()      { return avecSouper; }
    public boolean isAvecLogement()    { return avecLogement; }
    public void setNom(String nom)                  { this.nom = nom; }
    public void setPrénom(String prénom)            { this.prénom = prénom; }
    public void setTéléphone(String téléphone)      { this.téléphone = téléphone; }
    public void setEmail(String email)              { this.email = email; }
    public void setClub(String club)               { this.club = club; }
    public void setType(String type)               { this.type = type; }
    public void setPlages(List<Integer> plages)    { this.plages = plages; }
    public void setAvecSouper(boolean avecSouper)  { this.avecSouper = avecSouper; }
    public void setAvecLogement(boolean avecLogement) { this.avecLogement = avecLogement; }
}