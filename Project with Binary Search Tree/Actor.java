package project6;

/**
 * this class is used to represent actors
 * store the name of an actor
 * @author jiahuihan
 *
 */
public class Actor {

    private String nameOfActor;

    /**
     * Constructs a new Actor object with specified name
     * @param nameOfActor name to be used for this Actor
     * @throws IllegalArgumentException if name of Actor is invalid
     */
    public Actor(String nameOfActor) throws IllegalArgumentException {
        setName(nameOfActor);
    }

    /**
     * Validates and sets the name of actor for this Actor object.
     * @param nameOfActor
     * @throws IllegalArgumentException
     */
    private void setName(String nameOfActor) throws IllegalArgumentException {

        //check if parameter is valid
        if (nameOfActor == null) {
            throw new IllegalArgumentException("Name of actor is null.");
        }

        if (nameOfActor.length() == 0) {
            throw new IllegalArgumentException("Name of actor is empty.");
        }

        this.nameOfActor = nameOfActor;
    }

    /**
     * to have access to the name of actor in the Actor object
     * @return return the name of actor for this Actor object
     */
    public String getName() {
        return this.nameOfActor;
    }

}

