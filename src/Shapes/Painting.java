package Shapes;
import java.util.ArrayList;

public class Painting {

    private ArrayList<Shapes> list = new ArrayList<Shapes>();
    private String title, artist, nationality, description;
    private String year;

    public Painting() {
    }

    public Painting(ArrayList<Shapes> list) {
        this.list = list;
    }

    public ArrayList<Shapes> getList() {
        return list;
    }

    public void setList(ArrayList<Shapes> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String string) {
        this.year = string;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", nationality='" + nationality + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                '}';
    }
}