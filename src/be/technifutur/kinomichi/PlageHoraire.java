package be.technifutur.kinomichi;

public class PlageHoraire {
    private int numero;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String animateur;


    public PlageHoraire() {}


    public PlageHoraire(int numero, String jour, String heureDebut,
                        String heureFin, String animateur) {
        this.numero = numero;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.animateur = animateur;
    }


    public int getNumero()        { return numero; }
    public String getJour()       { return jour; }
    public String getHeureDebut() { return heureDebut; }
    public String getHeureFin()   { return heureFin; }
    public String getAnimateur()  { return animateur; }

    public void setAnimateur(String animateur) { this.animateur = animateur; }
    public void setJour(String jour)            { this.jour = jour; }
    public void setHeureDebut(String heureDebut){ this.heureDebut = heureDebut; }
    public void setHeureFin(String heureFin)    { this.heureFin = heureFin; }
}