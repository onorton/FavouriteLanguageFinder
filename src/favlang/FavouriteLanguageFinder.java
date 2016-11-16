package favlang;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.*;
public class FavouriteLanguageFinder {

    public static void main(String[] args) {

        String username;
        // Allows user to pass username to query in command line
        if (args.length == 1) {
            username = args[0];
        } else {
            // Otherwise, prompts user to enter username
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter the username to query.");
            username = reader.nextLine();
        }

        try {
            // Tries to find the favourite language of the user with the username
            String favouriteLang = findFavouriteLanguage(username);
            if (favouriteLang == null) {
                System.out.println(username + " has no public repositories with programming languages.");
            } else {
                System.out.println("The favourite language of " + username + " is probably " + favouriteLang + ".");
            }
        } catch (IOException e) {
            System.out.println("This user does not exist on GitHub.");
        }
    }


    public static String findFavouriteLanguage(String username) throws IOException {
        Map<String, Integer> languages = getLanguages(username);
        return getFavouriteLanguage(languages);
    }

    /**
     *
     * @param username
     * @return A map of all of the languages associated with the repositories of username, along with frequencies
     * @throws IOException
     */
    private static Map<String, Integer> getLanguages(String username) throws IOException {
        RepositoryService service = new RepositoryService();
        List<Repository> repositories = service.getRepositories(username);
        Map<String, Integer> languages = new HashMap<>();
        for(Repository repository: repositories) {
            String language = repository.getLanguage();
            Integer langCount = languages.get(language);
            if(langCount != null) {
                languages.replace(language, langCount+1);
            } else {
                languages.put(language, 1);
            }
        }
        return languages;
    }

    /**
     * @param languages a map of languages along with the frequency in terms of repositories
     * @return the most common language based off of the map
     */
    static String getFavouriteLanguage(Map<String, Integer> languages) {
        String favouriteLang = null;
        int maxCount = 0;
        for(Map.Entry<String, Integer> langEntry : languages.entrySet()) {
            if (langEntry.getValue() > maxCount) {
                favouriteLang = langEntry.getKey();
                maxCount = langEntry.getValue();
            }
        }
        return favouriteLang;
    }
}
