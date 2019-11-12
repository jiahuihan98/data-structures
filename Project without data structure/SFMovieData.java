

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 *This class is the program performing search by title or actor.
 *The program is interactive.
 *The program is executed the name of the input file which contains name, year, locations, fun fact,
 *actors, writer, director.
 *The file name serves as the the program's single command line argument.
 *The data in this file serves as a database of all the movies
 *In the interactive part, the user enters part of a movie title or actor name.
 *The program responds by printing the description of the movie
 *
 *@author Jiahui Han
 *
 */
public class SFMovieData {

    /**
     * The main() method of this program.
     * @param args array of Strings provided on the command line when the program is started;
     * the first string should be the name of the input file containing the list of movies.
     */
    public static void main(String[] args) {
        if (args.length == 0 ) {
            System.err.println("Usage Error: the program expects file name as an argument.\n");
            System.exit(1);
        }

        //verify that command line argument contains a name of an existing file
        File movieFile = new File(args[0]);
        if (!movieFile.exists()){
            System.err.println("Error: the file "+movieFile.getAbsolutePath()+" does not exist.\n");
            System.exit(1);
        }
        if (!movieFile.canRead()){
            System.err.println("Error: the file "+movieFile.getAbsolutePath()+
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        //open the file for reading
        Scanner inMovie = null;

        try {
            inMovie = new Scanner (movieFile ) ;
        } catch (FileNotFoundException e) {
            System.err.println("Error: the file "+movieFile.getAbsolutePath()+
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        MovieList movieList = new MovieList();
        while (inMovie.hasNextLine()) {
            try {
                ArrayList<String> perLine = splitCSVLine(inMovie.nextLine());
                // 0 for title, 1 for year, 2 for location, 3 for fun fact, 6 for director
                // 7 for writer, 8 9 10 for actors
                if (perLine.size() != 11) { //considered invalid
                    continue;
                }
                Movie m = new Movie(perLine.get(0), Integer.parseInt(perLine.get(1)));
                //check for the first actor
                try {
                    Actor actor1 = new Actor(perLine.get(8));
                    Actor actor2 = null;
                    Actor actor3 = null;
                    if (perLine.get(9) != null && perLine.get(9) != "") {
                        actor2 = new Actor(perLine.get(9));
                    }
                    if (perLine.get(10) != null && perLine.get(10) != "") {
                        actor3 = new Actor(perLine.get(10));
                    }
                    m = new Movie(perLine.get(0), Integer.parseInt(perLine.get(1)), perLine.get(6),
                            perLine.get(7), actor1, actor2, actor3);
                } catch (IllegalArgumentException e) {
                    System.exit(-1); //should never happen
                }

                //if the movie is already in the list, we want to update the location information if needed
                if (movieList.contains(m)){
                    for (Movie c: movieList){
                        if (c.equals(m)){
                            //check if there is new location information
                            if (perLine.get(2) != null && perLine.get(2).length() != 0) {
                                c.addLocation(new Location(perLine.get(2), perLine.get(3)));
                                break;
                            }
                        }
                    }
                }else {
                    //if the movie is not already in the list, add it
                    if (perLine.get(2) != null && perLine.get(2).length() != 0) {
                        m.addLocation(new Location(perLine.get(2), perLine.get(3)));
                    }
                    movieList.add(m);
                }
            }catch (IllegalArgumentException e) {
                continue;
            }
        }

        //interactive part
        Scanner userInput  = new Scanner (System.in );
        String userValue = "";

        System.out.print("Search the database by matching keywords to titles or actor names."
                + "To search for matching titles, enter"
                + "  title KEYWORD"
                + "To search for matching actor names, enter"
                + "  actor KEYWORD"
                + "To finish the program, enter"
                + "  quit \n\n\n\n");


        do {
            System.out.println("Enter your search query:");
            userValue = userInput.nextLine();
            //split the user input so that we can further check
            String[] userValueSplit = userValue.split(" ");
            if (userValue.equalsIgnoreCase("quit")) {
                System.exit(1);
            }
            //check if the input contains title or actor
            if (!((userValueSplit[0].equalsIgnoreCase("title")) || (userValueSplit[0].equalsIgnoreCase("actor")))) {
                System.out.println("This is not a valid query. Try again.");
                continue;
            }
            if (userValueSplit[0].equalsIgnoreCase("title")) {
                //remove the title or actor keyword in the original input by adding others together
                String query = "";
                for (int i = 1; i < userValueSplit.length - 1;i++){
                    query += userValueSplit[i]+" ";
                }
                //add the last word without a blank
                query += userValueSplit[userValueSplit.length-1];
                MovieList resultList = movieList.getMatchingTitles(query);

                //end this single iteration if no movie is found by this keyword
                if (resultList.size() == 0){
                    System.out.println("No matches found. Try again\n");
                    continue;
                }
                for (Movie m: resultList) {
                    System.out.println(m.toString());
                }
                continue;
            }
            if (userValueSplit[0].equalsIgnoreCase("actor")) {
                String query = "";
                for (int i = 1; i < userValueSplit.length - 1;i++){
                    query += userValueSplit[i]+" ";
                }
                query += userValueSplit[userValueSplit.length-1];

                MovieList resultList = movieList.getMatchingActor(query);

                //end this single iteration if no movie is found by this keyword
                if (resultList.size() == 0){
                    System.out.println("No matches found. Try again\n");
                    continue;
                }
                for (Movie m: resultList) {
                    System.out.println(m.toString());
                }
                continue;
            }

        }while (!(userValue.equalsIgnoreCase("quit")));
    }


    /**
     * Splits the given line of a CSV file according to commas and double quotes
     * (double quotes are used to surround multi-word entries so that they may contain commas)
     * @author Joanna Klukowska
     * @param textLine  a line of text to be passed
     * @return an ArrayList object containing all individual entries found on that line
     */
    public static ArrayList<String> splitCSVLine(String textLine){

        ArrayList<String> entries = new ArrayList<String>();
        int lineLength = textLine.length();
        StringBuffer nextWord = new StringBuffer();
        char nextChar;
        boolean insideQuotes = false;
        boolean insideEntry= false;

        // iterate over all characters in the textLine
        for (int i = 0; i < lineLength; i++) {
            nextChar = textLine.charAt(i);

            // handle smart quotes as well as regular quotes
            if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

                // change insideQuotes flag when nextChar is a quote
                if (insideQuotes) {
                    insideQuotes = false;
                    insideEntry = false;
                }else {
                    insideQuotes = true;
                    insideEntry = true;
                }
            } else if (Character.isWhitespace(nextChar)) {
                if ( insideQuotes || insideEntry ) {
                    // add it to the current entry
                    nextWord.append( nextChar );
                }else { // skip all spaces between entries
                    continue;
                }
            } else if ( nextChar == ',') {
                if (insideQuotes){ // comma inside an entry
                    nextWord.append(nextChar);
                } else { // end of entry found
                    insideEntry = false;
                    entries.add(nextWord.toString());
                    nextWord = new StringBuffer();
                }
            } else {
                // add all other characters to the nextWord
                nextWord.append(nextChar);
                insideEntry = true;
            }

        }
        // add the last word ( assuming not empty )
        // trim the white space before adding to the list
        if (!nextWord.toString().equals("")) {
            entries.add(nextWord.toString().trim());
        }

        return entries;
    }

}
