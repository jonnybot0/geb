import geb.junit5.GebReportingTest
import org.junit.jupiter.api.Test

class GebHomePageTest extends GebReportingTest {

    @Test
    void canGetToTheCurrentBookOfGeb() {
        to GebHomePage

        manualsMenu.open()

        //first link is for the current manual
        assert manualsMenu.links[0].text().startsWith("current")

        manualsMenu.links[0].click()

        at TheBookOfGebPage
    }

}
