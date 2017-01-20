package favlang;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.*;
public class FavouriteLanguageFinder {

    public static void main(String[] args) {

        String username = getUsername(args);

        try {
            // Tries to find the favourite language of the user with the username
            List<String> favouriteLangs = findFavouriteLanguages(username);
            switch (favouriteLangs.size()) {
                case 0 : System.out.println(username + " has no public repositories with programming languages.");break;
                case 1 : System.out.println("The favourite language of " + username + " is " + favouriteLangs.get(0) + ".");break;
                default: {
                    StringBuilder sb = new StringBuilder("The favourite languages of " + username + " are:");
                    for(String lang : favouriteLangs) {
                        if (favouriteLangs.lastIndexOf(lang) == favouriteLangs.size() - 1) {
                            sb.delete(sb.length()-1, sb.length()-1);
                            sb.append(" and ").append(lang).append('.');
                        } else {
                            sb.append(' ').append(lang).append(',');
                        }
                    }

                    System.out.println(sb.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("This user does not exist on GitHub.");
        }
    }

    private static String getUsername(String[] args) {
        // Allows user to pass username to query in command line
        if (args.length == 1) {
            return args[0];
        } else {
            // Otherwise, prompts user to enter username
            return promptUsername();
        }

    }
    private static String promptUsername() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the username to query: ");
        return reader.nextLine();
    }

    public static List<String> findFavouriteLanguages(String username) throws IOException {
        Map<String, Integer> languages = getLanguageCounts(username);
        return getFavouriteLanguage(languages);
    }

    /**
     *
     * @param username
     * @return A map of all of the languages associated with the repositories of username, along with frequencies
     * @throws IOException
     */
    private static Map<String, Integer> getLanguageCounts(String username) throws IOException {
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
    static List<String> getFavouriteLanguage(Map<String, Integer> languages) {
        List<String> favouriteLangs = new LinkedList<>();
        int maxCount = 0;
        for(Map.Entry<String, Integer> langEntry : languages.entrySet()) {
            if (langEntry.getValue() > maxCount) {
                maxCount = langEntry.getValue();
            }
        }
        for(Map.Entry<String, Integer> langEntry : languages.entrySet()) {
            if (langEntry.getValue() == maxCount) {
                favouriteLangs.add(langEntry.getKey());
            }
        }

        return favouriteLangs;
    }
}
