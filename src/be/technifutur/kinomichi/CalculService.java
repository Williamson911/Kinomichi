package be.technifutur.kinomichi;

import be.technifutur.kinomichi.data.StageData;

public class CalculService {
    public double calculerPrixParticipant(Participants p, StageData data) {
        if (p.getType() == null) return 0;

        TypeParticipant type = data.getTypes().stream()
                .filter(t -> t.getLibellé().equalsIgnoreCase(p.getType()))
                .findFirst()
                .orElse(null);

        if (type == null) return 0;

        double total = 0;


        total += p.getPlages().size() * type.getPrixParPlage();


        if (p.isAvecSouper()) {
            total += type.getPrixSouper();
        }


        if (p.isAvecLogement()) {
            total += type.getPrixLogement();
        }


        if (type.getForfaitTout() > 0) {
            total = type.getForfaitTout();
        }

        return total;
    }
}
