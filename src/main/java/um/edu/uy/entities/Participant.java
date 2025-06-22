package um.edu.uy.entities;

import um.edu.uy.importer.CreditsLoader;

import java.util.ArrayList;
import java.util.List;

public class Participant extends Person implements Comparable<Participant> {
    private List<CrewParticipation> crewActivity;

    public Participant() {
        super();
        this.crewActivity = new ArrayList<>();
    }
    public Participant(String personId, String name, String creditId) {
        super(personId, name, creditId);
        this.crewActivity = new ArrayList<>();
    }

    public List<CrewParticipation> getCrewActivity() {
        return crewActivity;
    }

    public void setCrewActivity (List<CrewParticipation> crewActivity) {
        this.crewActivity = crewActivity;

    }

    public void addCrewParticipation(CrewParticipation participation) {
        this.crewActivity.add(participation);
    }

    @Override
    public int compareTo(Participant o) {
        return this.personId.compareTo(o.personId);
    }
}
