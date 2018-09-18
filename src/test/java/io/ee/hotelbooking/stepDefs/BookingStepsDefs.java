package io.ee.hotelbooking.stepDefs;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.util.Map;

import io.ee.hotelbooking.model.HotelBooking;
import io.ee.hotelbooking.pages.HotelBookingPage;
import io.ee.hotelbooking.utils.ConfigurationLoader;
import io.ee.hotelbooking.utils.WebDriverBuilder;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingStepsDefs {

    private static Map<String,String> config =  ConfigurationLoader.config();

    private static final String URL = config.get("appUrl");
    private WebDriver driver;
    private HotelBookingPage hotelBookingPage;
    HotelBooking hotelBooking;

    public BookingStepsDefs() {
        this.driver = WebDriverBuilder.getDriver();
        this.hotelBookingPage = new HotelBookingPage(driver);
    }

    @Given("^I am on the hotel booking page$")
    public void onTheHotelBookingPage() {
        driver.get(URL);
    }


    @Then("^the booking is successful")
    public void theBookingIsSuccessful() {
        assertThat(hotelBookingPage.getAllTheBookings()).contains(hotelBooking);
    }


    @When("^I enter details and book$")
    public void IenterDetailsAndBook(DataTable table) {
        Map<String, String> bookingDataInMap = table.asMap(String.class, String.class);
        hotelBooking = new HotelBooking(
            bookingDataInMap.get("firstName"),
            bookingDataInMap.get("lastName"),
            new BigDecimal(bookingDataInMap.get("price")),
            bookingDataInMap.get("deposit"),
            bookingDataInMap.get("checkin"),
            bookingDataInMap.get("checkout"));
        hotelBookingPage.enterBookingDetailsAndSave(hotelBooking);

    }

    @When("^I delete the my hotel booking$")
    public void clickDeleteButton() {
        hotelBookingPage.deleteThatBooking(hotelBooking);
    }

    @Then("^booking is deleted$")
    public void bookingIsDeleted() {
        assertThat(hotelBookingPage.getAllTheBookings()).doesNotContain(hotelBooking);
    }
}

