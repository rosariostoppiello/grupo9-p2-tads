package um.edu.uy.entities;

import java.util.List;
import java.util.ArrayList;
public class Actor extends Person {
    private List<CastParticipation> activityActor;

    public Actor () {
        super();
        this.activityActor = new ArrayList<>();
    }

    public Actor(String personId, String name, String creditId) {
        super(personId, name, creditId);
        this.activityActor = new ArrayList<>();
    }

    public List<CastParticipation> getActivityActor() {
        return activityActor;
    }

    public void setActivityActor(List<CastParticipation> activityActor) {
        this.activityActor = activityActor;
    }

    public void addCastParticipation(CastParticipation participation) {
        this.activityActor.add(participation);
    }

}
