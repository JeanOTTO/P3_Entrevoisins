
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private Neighbour neighbourName;
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
    * Test vérifiant que lorsqu’on clique sur un élément de la liste, l’écran de
    * étails est bien lancé
    */
    @Test
    public void myNeighboursList_clickOnNeighbour_shouldOpenProfile() {
        // Simuler le clic sur un élément de la liste
        onView(withId(R.id.list_neighbours)).perform(click());

        // Vérifier que l'écran de profil est bien lancé
        onView(ViewMatchers.withId(R.id.profile_layout))
                .check(matches(isDisplayed()));
    }


    /**
    * Test vérifiant qu’au démarrage de ce nouvel écran, le TextView indiquant
    * le nom de l’utilisateur en question est bien rempli
    */
    @Test
    public void myNeighboursList_userNameTextViewFilled() {
        // Recuperer le deuxieme élément de ma liste de voisins
        neighbourName = DI.getNeighbourApiService().getNeighbours().get(1);

        // Cliquer sur le deuxième élément de la liste "list_neighbours"
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Vérifier que nous avons basculé vers la vue "profile-layout"
        onView(withId(R.id.profile_layout)).check(matches(isDisplayed()));

        // Vérifier que le TextView "tvName" a la même valeur que "neighbourName.getName()"
        onView(withId(R.id.tvName)).check(matches(withText(neighbourName.getName())));
    }

    /**
     * Test vérifiant que l’onglet Favoris n’affiche que les voisins marqués comme
     * favoris.
     */
    @Test
    public void myFavoritesList_onlyFavoriteNeighbours() {
        // Clique sur un neighbour dans la liste
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        // Clique sur le bouton d'ajout en favori
        onView(withId(R.id.fabFav))
                .perform(click());

        // Retourne en arrière
        onView(isRoot()).perform(pressBack());

        // Vérifie le nombre d'éléments dans la liste des favoris "list_fav_neighbours"
        onView(withId(R.id.list_fav_neighbours))
                .check(withItemCount(1));
    }


}