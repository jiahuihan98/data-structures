package project3;

import java.util.ArrayList;

/**
 * The class represent a Movie object containing title, year of release, locations,
 * director, writer and actors.
 * Two Movie objects can be compared and printed out.
 *
 * @author Jiahui Han
 */
public class Movie implements Comparable<Object>{

    private String title;
    private int year;
    private ArrayList<Location> listOfSFLocations = new ArrayList<>();
    private String director;
    private String writer;
    private ArrayList<Actor> listOfActors = new ArrayList<>();

    /**
     * Simple constructor with only title and year as parameters.
     * @param title title of the movie
     * @param year year that the movie released
     */
    public Movie(String title, int year) {
        setTitle(title);
        setYear(year);
    }

    /**
     * complex constructor with multiple parameters.
     * @param title title of the movie, cannot be be null or empty
     * @param year year that the movie released, cannot be null or empty and from 1900 - 2020
     * @param director director of the movie, can be anything
     * @param writer writer of the movie, can be anything
     * @param actor1 first actor of the movie, cannot be null or empty
     * @param actor2 second actor of the movie, need not to exist
     * @param actor3 third actor of the movie, need not to exist
     * @throws IllegalArgumentException if input parameters are not valid
     */
    public Movie(String title, int year, String director, String writer,
                 Actor actor1, Actor actor2, Actor actor3) throws IllegalArgumentException {
        setTitle(title);
        setYear(year);
        setDirector(director);
        setWriter(writer);
        if (actor1 == null) {
            throw new IllegalArgumentException("Actor 1 is null.");
        }
        listOfActors.add(actor1);
        listOfActors.add(actor2);
        listOfActors.add(actor3);

    }

    /**
     * set the movie title if valid
     * @param title title of the movie
     * @throws IllegalArgumentException if the input is null or empty
     */
    private void setTitle(String title) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title is null.");
        }
        if (title.length() == 0) {
            throw new IllegalArgumentException("Title is empty.");
        }else{
            this.title = title;
        }
    }

    /**
     * set the year of release to the movie if valid
     * @param year year that the movie released
     * @throws IllegalArgumentException if the year is not in the range
     */
    private void setYear(int year) throws IllegalArgumentException {
        if (year> 2020 || year < 1900){
            throw new IllegalArgumentException("Invalid input for year");
        }else{
            this.year = year;
        }
    }

    /**
     * set the director of the movie
     * @param director
     */
    private void setDirector(String director) {
        this.director = director;
    }

    /**
     * set the writer of the movie
     * @param writer
     */
    private void setWriter(String writer) {
        this.writer = writer;
    }

    /**
     * return the movie title
     * @return the movie title
     */
    public String getTitle( ) {
        return this.title;
    }

    /**
     * return the year that the movie released
     * @return the year that the movie released
     */
    public int getYear( ) {
        return this.year;
    }

    /**
     * return the director of the movie
     * @return the director of the movie
     */
    public String getDirector( ) {
        return this.director;
    }

    /**
     * return the writer of the movie
     * @return the writer of the movie
     */
    public String getWriter( ) {
        return this.writer;
    }

    /**
     * return the actor list of the movie
     * @return an ArrayList of object Actors of the movie
     */
    public ArrayList<Actor> getActor() {
        return this.listOfActors;
    }

    /**
     * return the location list of the movie
     * @return an ArrayList of object Location of the movie
     */
    public ArrayList<Location> getLocation() {
        return this.listOfSFLocations;
    }

    /**
     * add location to the location list of the movie
     * @param loc the new input Location object
     * @throws IllegalArgumentException if the input is null
     */
    public void addLocation(Location loc) throws IllegalArgumentException {
        if (loc == null) {
            throw new IllegalArgumentException("location is null.");
        }
        listOfSFLocations.add(loc);
    }

    /**
     * implement compareTo to compare between two movie firstly by year, then by title
     * @param o the Movie object that we are comparing with
     * @return integer 0 if two are equal, 1 if the Movie being compared if smaller, -1 id bigger
     */
    public int compareTo(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Movie is null.");
        }
        if (!(o instanceof Movie)) {
            throw new IllegalArgumentException("Object is not a movie.");
        }
        Movie other = (Movie) o;
        if (this.year > other.getYear()) {
            return 1;
        }
        if (this.year < other.getYear()) {
            return -1;
        }
        else {
            if (this.title.compareToIgnoreCase(other.getTitle()) > 0) {
                return 1;
            }
            else if (this.title.compareToIgnoreCase(other.getTitle()) < 0) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * override the equals method to compare the two Movie objects to see if they are equal
     * @param o the input that we are comparing with, unnecessary a Movie objecg
     * @return boolean value true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Movie))
            return false;

        //cast the object into Movie for comparison
        Movie other = (Movie) o;
        if (this.title.compareToIgnoreCase(other.getTitle()) == 0) {
            if (this.year == other.getYear()) {
                return true;
            }
        }
        return false;
    }

    /**
     * override the toString method of the Movie object
     * @return a string formatted according to requirements
     */
    @Override
    public String toString() {
        String result = "";
        result += this.title;
        result += " (" + this.year + ")" + "\n";
        result += "------------------------------------\n";
        result += "director            :" + this.director +"\n";
        result += "writer              :" + this.writer + "\n";
        String tmp = "";
        for (int i = 0; i < listOfActors.size(); i++) {
            if (listOfActors.get(i) != null) {
                tmp += listOfActors.get(i).getName() + ", ";
            }
        }
        result += "starring            :" + tmp +"\n";
        result += "filmed on location at: \n";
        for (int i = 0; i < listOfSFLocations.size(); i++) {
            if (listOfSFLocations.get(i) != null) {
                result +=  "    "+ listOfSFLocations.get(i).getName();
                if (listOfSFLocations.get(i).getFunFact() != null && !listOfSFLocations.get(i).getFunFact().equals("")) {
                    result += "  (" + listOfSFLocations.get(i).getFunFact() + ")" + "\n";
                }
                else {
                    result += "\n";
                }
            }
        }
        return result;
    }

}
