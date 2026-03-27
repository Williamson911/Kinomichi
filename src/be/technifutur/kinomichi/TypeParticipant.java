package be.technifutur.kinomichi;

public class TypeParticipant {

    private String libellé;
    private double prixParPlage;
    private double prixSouper;
    private double prixLogement;
    private double forfaitTout;


    public TypeParticipant(String libellé, double prixParPlage, double prixSouper,
                           double prixLogement, double forfaitTout) {
        this.libellé = libellé;
        this.prixParPlage = prixParPlage;
        this.prixSouper = prixSouper;
        this.prixLogement = prixLogement;
        this.forfaitTout = forfaitTout;
    }


    public String getLibellé()        { return libellé; }
    public double getPrixParPlage()   { return prixParPlage; }
    public double getPrixSouper()     { return prixSouper; }
    public double getPrixLogement()   { return prixLogement; }
    public double getForfaitTout()    { return forfaitTout; }


    public void setLibellé(String libellé)           { this.libellé = libellé; }
    public void setPrixParPlage(double prixParPlage) { this.prixParPlage = prixParPlage; }
    public void setPrixSouper(double prixSouper)     { this.prixSouper = prixSouper; }
    public void setPrixLogement(double prixLogement) { this.prixLogement = prixLogement; }
    public void setForfaitTout(double forfaitTout)   { this.forfaitTout = forfaitTout; }

    @Override
    public String toString() {
        return libellé + " | Plage: " + prixParPlage + "€ | Souper: " + prixSouper +
                "€ | Logement: " + prixLogement + "€ | Forfait: " + forfaitTout + "€";
    }
    public double calculerTotal(int nbPlages, boolean avecSouper, boolean avecLogement) {
        double sansForfait = (nbPlages * prixParPlage)
                + (avecSouper   ? prixSouper   : 0)
                + (avecLogement ? prixLogement : 0);


        if (nbPlages == 0) return sansForfait;

        return Math.min(sansForfait, forfaitTout);
    }
}