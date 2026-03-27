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

    public String getNom()                          { return nom; }
    public List<PlageHoraire> getPlages()           { return plages; }
    public List<TypeParticipant> getTypes()         { return types; }
    public List<Participants> getParticipants()     { return participants; }
}