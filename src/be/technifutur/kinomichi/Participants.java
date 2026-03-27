package be.technifutur.kinomichi;

import java.util.List;

public class Participants {
    private String nom;
    private String prénom;
    private String téléphone;
    private String email;
    private String club;
    private String type;        // correspond au libellé dans types[]
    private List<Integer> plages;
    private boolean avecSouper;
    private boolean avecLogement;

    // Getters
    public String getNom()              { return nom; }
    public String getPrénom()           { return prénom; }
    public String getTéléphone()        { return téléphone; }
    public String getEmail()            { return email; }
    public String getClub()             { return club; }
    public String getType()             { return type; }
    public List<Integer> getPlages()    { return plages; }
    public boolean isAvecSouper()       { return avecSouper; }
    public boolean isAvecLogement()     { return avecLogement; }

    public void setNom(String nom)            { this.nom = nom; }
    public void setPrénom(String prénom)      { this.prénom = prénom; }
    public void setTéléphone(String téléphone){ this.téléphone = téléphone; }
    public void setEmail(String email)        { this.email = email; }
    public void setClub(String club)          { this.club = club; }

    @Override
    public String toString() {
        return prénom + " " + nom + " | Club: " + club +
                " | Email: " + email + " | Tél: " + téléphone;
    }
}