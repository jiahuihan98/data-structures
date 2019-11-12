
/**
 * this is used to represent the filming locations and fun facts that may be associated
 * store the name of the location itself and the optional fun fact
 * @author jiahuihan
 *
 */
public class Location {

    private String nameOfLocation;
    private String funFact;

    /**
     * Constructs a new Location object with specified name and optional fun fact
     * @param nameOfLocation name to be used for this Location
     * @param funFact fun fact to be linked with this Location
     * @throws IllegalArgumentException if name of the Location is invalid
     */
    public Location(String nameOfLocation, String funFact) throws IllegalArgumentException {
        setName(nameOfLocation);
        setFunFact(funFact);
    }

    /**
     * Validates and sets the name of location for this Location object.
     * @param nameOfLocation
     * @throws IllegalArgumentException if the name is null or empty
     */
    private void setName(String nameOfLocation) throws IllegalArgumentException {

        //check if parameter is valid
        if (nameOfLocation == null) {
            throw new IllegalArgumentException("Name of location is null.");
        }

        if (nameOfLocation.length() == 0) {
            throw new IllegalArgumentException("Name of location is empty.");
        }

        this.nameOfLocation = nameOfLocation;
    }

    /**
     * set fun fact to this Location object
     * @param funFact
     */
    private void setFunFact(String funFact) {
        this.funFact = funFact;
    }

    /**
     * to have access to the name of location
     * @return the name of this location
     */
    public String getName() {
        return this.nameOfLocation;
    }

    /**
     * to have access to the name of location
     * @return fun fact of this Location
     */
    public String getFunFact( ) {
        return this.funFact;
    }
}