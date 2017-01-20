package favlang;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FavouriteLanguageFinder {

    public static void main(String[] args) {

        String username = getUsername(args);

        try {
            // Tries to find the favourite language of the user with the username
            List<String> favouriteLangs = findFavouriteLanguages(username);
            System.out.println(formatFavouriteLanguages(username, favouriteLangs));

        } catch (UnknownHostException e) {
            System.out.println("Cannot connect to github.");
        } catch (RequestException e) {
            System.out.println("User not found.");
        } catch (IOException e) {
            System.out.println("Cannot obtain favourite language.");
        }
    }

    public static List<String> findFavouriteLanguages(String username) throws IOException {
        Map<String, Integer> languages = getLanguageCounts(username);
        return getFavouriteLanguages(languages);
    }

    public static String formatFavouriteLanguages(String username, List<String> favouriteLangs) {
        switch (favouriteLangs.size()) {
            case 0 : return username + " has no public repositories with programming languages.";
            case 1 : return "The favourite language of " + username + " is " + favouriteLangs.get(0) + ".";
            default: {
                StringBuilder sb = new StringBuilder("The favourite languages of " + username + " are");
                for(String lang : favouriteLangs) {
                    if (favouriteLangs.lastIndexOf(lang) == favouriteLangs.size() - 1) {
                        sb.delete(sb.length()-1, sb.length());
                        sb.append(" and ").append(lang).append('.');
                    } else {
                        sb.append(' ').append(lang).append(',');
                    }
                }

                return sb.toString();
            }
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
     static List<String> getFavouriteLanguages(Map<String, Integer> languages) {
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
