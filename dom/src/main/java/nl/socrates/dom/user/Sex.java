package nl.socrates.dom.user;

public enum Sex {
    MALE("man"),
    FEMALE("vrouw");
    
    private String title;
    
    Sex(String title) {
        this.title = title;
    }    

    public String title() {
        return title;
    }
}
