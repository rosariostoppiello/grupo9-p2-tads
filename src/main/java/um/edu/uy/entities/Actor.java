package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class Actor extends Person implements Comparable<Actor> {
    private MyList<String> activityActor;

    public Actor(String personId, String name) {
        super(personId, name);
        this.activityActor = new MyLinkedListImpl<>();
    }

    public void addMovieId(String movieId) {
        if (movieId != null && !activityActor.elementoSeEncuentra(movieId)) {
            activityActor.addLast(movieId);
        }
    }

    @Override
    public int compareTo(Actor o) {
        return this.personId.compareTo(o.personId);
    }

    public MyList<String> getActivityActor() {
        return activityActor;
    }

    public void setActivityActor(MyList<String> activityActor) {
        this.activityActor = activityActor;
    }
}
