package be.technifutur.kinomichi.data;

import be.technifutur.kinomichi.Participants;
import be.technifutur.kinomichi.PlageHoraire;
import be.technifutur.kinomichi.TypeParticipant;

import java.util.List;

public class StageData {
    private String nom;
    private List<TypeParticipant> types;
    private List<PlageHoraire> plages;
    private List<Participants> participants;

    // Constructeur vide nécessaire pour Gson et la saisie manuelle
    public StageData() {}

    // Getters
    public String getNom()                       { return nom; }
    public List<TypeParticipant> getTypes()      { return types; }
    public List<PlageHoraire> getPlages()        { return plages; }
    public List<Participants> getParticipants()  { return participants; }

    // Setters
    public void setNom(String nom)                           { this.nom = nom; }
    public void setTypes(List<TypeParticipant> types)        { this.types = types; }
    public void setPlages(List<PlageHoraire> plages)         { this.plages = plages; }
    public void setParticipants(List<Participants> participants) { this.participants = participants; }
}