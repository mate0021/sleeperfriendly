package pl.mate00.sleeperfriendlyapp.filebrowser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by mamy on 17.04.15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
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
