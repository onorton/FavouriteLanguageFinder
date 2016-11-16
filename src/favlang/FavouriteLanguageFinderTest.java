package favlang;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by Ollie on 29/02/2016.
 */
public class FavouriteLanguageFinderTest {

    @Test
    public void GettingFavouriteLanguageEmptyListReturnsNull() {
        Map<String, Integer> languageCounts = new HashMap<>();
        Assert.assertNull(FavouriteLanguageFinder.getFavouriteLanguage(languageCounts));
    }

    @Test(expected = IOException.class)
    public void FindLanguageUserDoestNotExist() throws IOException {
        FavouriteLanguageFinder.findFavouriteLanguage("!");
    }
}