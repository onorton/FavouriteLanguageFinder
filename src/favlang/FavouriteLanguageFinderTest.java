package favlang;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ollie on 29/02/2016.
 */
public class FavouriteLanguageFinderTest {

    private Map<String, Integer> languageCounts = new HashMap<>();

    @Test
    public void GettingFavouriteLanguageEmptyListReturnsEmptyList() {
        Assert.assertTrue(FavouriteLanguageFinder.getFavouriteLanguage(languageCounts).isEmpty());
    }

    @Test
    public void GettingFavouriteLanguageSingleItemReturnsLanguage() {
        languageCounts.put("java", 2);
        Assert.assertEquals("java", FavouriteLanguageFinder.getFavouriteLanguage(languageCounts).get(0));
    }

    @Test
    public void GettingFavouriteLanguageMultipleReturnsHighestCount() {
        languageCounts.put("java", 10);
        languageCounts.put("C++", 2);
        Assert.assertEquals("java", FavouriteLanguageFinder.getFavouriteLanguage(languageCounts).get(0));
    }

    @Test
    public void GettingFavouriteLanguageMultipleHighestReturnsAll() {
        languageCounts.put("C", 10);
        languageCounts.put("Java", 10);
        languageCounts.put("Ruby", 10);
        languageCounts.put("C++", 2);
        List<String> favLangs = FavouriteLanguageFinder.getFavouriteLanguage(languageCounts);
        Assert.assertEquals(3, favLangs.size());
        int highestCount = languageCounts.get(favLangs.get(0));
        Assert.assertEquals(10, highestCount);
        Assert.assertTrue(favLangs.contains("C"));
        Assert.assertTrue(favLangs.contains("Java"));
        Assert.assertTrue(favLangs.contains("Ruby"));

    }

    @Test
    public void FindLanguageUserConsistent() throws IOException {
        List<String> favLangs = FavouriteLanguageFinder.findFavouriteLanguages("onorton");
        List<String> favLangs2 = FavouriteLanguageFinder.findFavouriteLanguages("onorton");
        Assert.assertEquals(favLangs, favLangs2);

    }

    @Test(expected = IOException.class)
    public void FindLanguageUserDoestNotExist() throws IOException {
        FavouriteLanguageFinder.findFavouriteLanguages("!");
    }
}