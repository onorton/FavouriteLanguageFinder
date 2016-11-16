package favlang;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ollie on 29/02/2016.
 */
public class FavouriteLanguageFinderTest {

    private Map<String, Integer> languageCounts = new HashMap<>();

    @Test
    public void GettingFavouriteLanguageEmptyListReturnsNull() {
        Assert.assertNull(FavouriteLanguageFinder.getFavouriteLanguage(languageCounts));
    }

    @Test
    public void GettingFavouriteLanguageSingleItemReturnsLanguage() {
        languageCounts.put("java", 2);
        Assert.assertEquals("java", FavouriteLanguageFinder.getFavouriteLanguage(languageCounts));
    }

    @Test
    public void GettingFavouriteLanguageMultipleReturnsHighestCount() {
        languageCounts.put("java", 10);
        languageCounts.put("C++", 2);
        Assert.assertEquals("java", FavouriteLanguageFinder.getFavouriteLanguage(languageCounts));
    }

    @Test
    public void GettingFavouriteLanguageMultipleHighestReturnsOneWithHighest() {
        languageCounts.put("C", 10);
        languageCounts.put("Java", 10);
        languageCounts.put("Ruby", 10);
        languageCounts.put("C++", 2);
        String favLang = FavouriteLanguageFinder.getFavouriteLanguage(languageCounts);
        int highestCount = languageCounts.get(favLang);
        Assert.assertEquals(10, highestCount);
        Assert.assertTrue(favLang.equals("C") || favLang.equals("Java") || favLang.equals("Ruby"));
    }

    @Test(expected = IOException.class)
    public void FindLanguageUserDoestNotExist() throws IOException {
        FavouriteLanguageFinder.findFavouriteLanguage("!");
    }
}