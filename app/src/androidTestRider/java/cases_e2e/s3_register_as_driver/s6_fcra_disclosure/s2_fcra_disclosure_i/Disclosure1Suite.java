package cases_e2e.s3_register_as_driver.s6_fcra_disclosure.s2_fcra_disclosure_i;

import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rideaustin.BaseE2ETest;
import com.rideaustin.R;
import com.rideaustin.RaActivityRule;
import com.rideaustin.TestCases;
import com.rideaustin.ui.drawer.NavigationDrawerActivity;
import com.rideaustin.utils.NavigationUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import cases.s3_register_as_driver.RegisterAsDriverUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rideaustin.TestConstants.ACTIVE_RIDER_EMAIL;
import static com.rideaustin.TestConstants.ACTIVE_RIDER_PASSWORD;
import static com.rideaustin.helpers.LoginTestHelper.throughLogin;
import static com.rideaustin.utils.MatcherUtils.navigationIcon;
import static com.rideaustin.utils.ViewActionUtils.waitForDisplayed;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Sergey Petrov on 05/09/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Disclosure1Suite extends BaseE2ETest {

    @Rule
    public ActivityTestRule<NavigationDrawerActivity> activityRule = new RaActivityRule<>(NavigationDrawerActivity.class, false, false);

    @Override
    public void setUp() {
        super.setUp();
        Intents.init();
    }

    @Override
    public void tearDown() {
        super.tearDown();
        Intents.release();
    }

    @Test
    @TestCases({"C1274562", "C1274563"})
    public void shouldRequestHelpFromDisclosure1() throws IOException {
        NavigationUtils.startActivity(activityRule);
        throughLogin(ACTIVE_RIDER_EMAIL, ACTIVE_RIDER_PASSWORD);

        // wait for map
        waitForDisplayed(R.id.mapContainer);

        // go to driver registration
        onView(navigationIcon()).perform(click());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.navDriveWithRideApp));

        RegisterAsDriverUtils.throughInitial();
        RegisterAsDriverUtils.throughDriverPhoto(false);
        RegisterAsDriverUtils.throughDriverLicense(true);
        RegisterAsDriverUtils.skipTnc();
        RegisterAsDriverUtils.throughFrontPhoto(false);
        RegisterAsDriverUtils.throughBackPhoto(true);
        RegisterAsDriverUtils.throughInsidePhoto(false);
        RegisterAsDriverUtils.throughTrunkPhoto(true);
        RegisterAsDriverUtils.throughCarInfo();
        RegisterAsDriverUtils.throughInsurance();

        // check title
        onView(allOf(withId(R.id.toolbarTitle), withText(R.string.title_fcra_disclosure))).check(matches(isDisplayed()));
        RegisterAsDriverUtils.checkNeedHelp();

    }

}
