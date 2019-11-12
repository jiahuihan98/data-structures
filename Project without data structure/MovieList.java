

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a MovieList object which extend ArrayList<Object>.
 * It has two methods which allow iterate through the list and find the corresponding Movie objects.
 *
 * @author Jiahui Han
 */
public class MovieList extends ArrayList<Movie> {

    /**
     *default constructor
     */
    public MovieList() {
        super();
    }

    /**
     * method that iterate through the existing list to find the movie object whose title contains the keyword
     * @param keyword input that we want to find in the list, case insensitive
     * @return a MovieList with all movies which has title containing the keyword
     */
    public MovieList getMatchingTitles (String keyword) {
        if (this == null || this.size() == 0){return null;}
        if (keyword == null || keyword.length() == 0){return null;}
        MovieList resultList = new MovieList();
        if (keyword == null || keyword.length() == 0) {
            return null;
        }
        for (Movie o : this) {
            String movieTitle = o.getTitle();
            if (movieTitle.toLowerCase().contains(keyword.toLowerCase()))
                resultList.add(o);
        }
        //sort the result list
        Collections.sort(resultList);
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    /**
     * method that iterate through the existing list to find the movie object whose actor contains the keyword
     * @param keyword input that we want to find in the list, case insensitive
     * @return a MovieList with all movies which has actor name containing the keyword
     */
    public MovieList getMatchingActor (String keyword) {
        if (this == null || this.size() == 0){return null;}
        if (keyword == null || keyword.length() == 0){return null;}
        MovieList resultList = new MovieList();
        if (keyword == null || keyword.length() == 0) {
            return null;
        }
        for (Movie o : this ) {
            ArrayList<Actor> movieActor = o.getActor();
            if (movieActor == null)
                continue;
            for (int i = 0; i < movieActor.size(); i++) {
                if (movieActor.get(i) != null){
                    if (movieActor.get(i).getName().toLowerCase().
                            contains(keyword.toLowerCase()) )
                        resultList.add(o);
                }

            }
        }
        //sort the result list
        Collections.sort(resultList);
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList;

    }

    /**
     * override the toString method to return the title of the movie
     * @return the title of the movie
     */
    @Override
    public String toString() {
        String result = "";
        for (Movie o: this) {
            result += o.getTitle() + "; ";
        }
        return result;
    }

}
