/**
 * Created by Lookas33 on 23/05/16.
 */

import android.os.Build;
import android.widget.TextView;

import com.project.app.pilpoil.Activity.AboutActivity;
import com.project.app.pilpoil.BuildConfig;
import com.project.app.pilpoil.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AboutActivityTest {
    private AboutActivity activity;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(AboutActivity.class);
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void validateTextViewContent() {
        TextView txtViewCoor = (TextView) activity.findViewById(R.id.textView2);
        assertNotNull("TextView could not be found", txtViewCoor);
        assertTrue("TextView contains incorrect text",
                "Coordonnees".equals(txtViewCoor.getText().toString()));
    }

}
