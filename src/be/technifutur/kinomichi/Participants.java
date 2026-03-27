package be.technifutur.kinomichi;

public class Participants {

    private String nom;
    private String prénom;
    private String téléphone;
    private String email;
    private String club;

    public Participants(String nom, String prénom, String téléphone, String email, String club) {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.email = email;
        this.club = club;
    }

    public String getNom()        { return nom; }
    public String getPrénom()     { return prénom; }
    public String getTéléphone()  { return téléphone; }
    public String getEmail()      { return email; }
    public String getClub()       { return club; }

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