package pl.mate00.sleeperfriendlyapp.filebrowser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.mate00.sleeperfriendlyapp.BuildConfig;

/**
 * Created by mamy on 17.04.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class FileBrowserUiHandlerTest {

    private FileBrowserUiHandler subject;

    @Before
    public void setUp() {
//        subject = new FileBrowserUiHandler(Robolectric.application);
    }

    @Test
    public void selectedItem() {
    }
}
