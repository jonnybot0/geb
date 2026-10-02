import geb.spock.GebSpec

class GebHomePageSpec extends GebSpec {

    def "can get to the current Book of Geb"() {
        when:
        to GebHomePage

        and:
        manualsMenu.open()

        then:
        manualsMenu.links[0].text().startsWith("current")

        when:
        manualsMenu.links[0].click()

        then:
        at TheBookOfGebPage
    }
}
