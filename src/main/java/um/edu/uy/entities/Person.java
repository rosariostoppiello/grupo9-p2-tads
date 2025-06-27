package um.edu.uy.entities;

public abstract class Person {
    protected String personId;
    protected String name;

    public Person(String personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void SetPersoonId(String personId) {

        this.personId = personId;
    }

    public String getName() {

        return name;
    }

    public void SetName(String name) {

        this.name = name;
    }
}
