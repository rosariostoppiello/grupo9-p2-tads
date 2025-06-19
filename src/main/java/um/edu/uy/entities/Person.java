package um.edu.uy.entities;

public abstract class Person {
    protected String personId;
    protected String name;
    protected String creditId;

    public Person () {
        this.personId = null;
        this.name = null;
        this.creditId = null;
    }

    public Person (String personId, String name, String creditId) {
        this.personId = personId;
        this.name = name;
        this. creditId = creditId;

    }

    public String getPersonId() {
        return personId;
    }

    public void SetPersoonId(String personId) {

        this.personId = personId;
    }

    public String getName () {

        return name;
    }

    public void SetName (String name) {

        this.name = name;
    }

    public String getCreditId () {
        return creditId;
    }

    public void setCreditId (String creditId) {

        this.creditId = creditId;
    }
}
