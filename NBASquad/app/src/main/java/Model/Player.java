package Model;

public class Player {
    private int id;
    private String name;
    private String team;
    private int points;
    private String image;

    /*Getters & Setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
    /*Getters & Setters*/
}

