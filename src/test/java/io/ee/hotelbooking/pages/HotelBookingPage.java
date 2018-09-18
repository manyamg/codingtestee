package io.ee.hotelbooking.pages;

import io.ee.hotelbooking.model.HotelBooking;
import io.ee.hotelbooking.utils.ExtendedWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

public class HotelBookingPage extends BasePage {

    private static final int EXCLUDE_FIRST_ROW_PLUS_ARRAY_INDEX = 2;

    @FindBy(id = "firstname")
    private WebElement firstName;

    @FindBy(id = "lastname")
    private WebElement lastName;

    @FindBy(id = "totalprice")
    private WebElement totalPrice;

    @FindBy(id = "depositpaid")
    private WebElement depositDropdown;

    @FindBy(id = "checkin")
    private WebElement checkinDate;

    @FindBy(id = "checkout")
    private WebElement checkoutDate;

    @FindBy(css = "input[value=\" Save \"]")
    private WebElement saveBooking;
    private int bookingCount;

    public HotelBookingPage(WebDriver driver) {
        super(driver);
    }

    private int getCurrentBookingCount() {
        return driver.findElements(cssSelector("#bookings>.row")).size();
    }

    private HotelBookingPage enterFirstName(String name) {
        firstName.sendKeys(name);
        return this;
    }

    private HotelBookingPage enterSurname(String surname) {
        lastName.sendKeys(surname);
        return this;
    }

    private HotelBookingPage enterPrice(String price) {
        totalPrice.sendKeys(price);
        return this;
    }

    private HotelBookingPage selectDepositPaid(String paid) {
        new Select(depositDropdown).selectByVisibleText(paid);
        return this;
    }

    private HotelBookingPage selectCheckin(String checkin) {
        checkinDate.sendKeys(checkin);
        return this;
    }

    private HotelBookingPage selectCheckout(String checkout) {
        checkoutDate.sendKeys(checkout);
        return this;
    }

    private void clickSave() {
        int currentBookingCount = getCurrentBookingCount();
        saveBooking.click();
        new ExtendedWait(driver, 30).forNewBookingToAppear(currentBookingCount);
    }

    public void deleteThatBooking(HotelBooking hotelBooking) {
        Integer currentBookingCount = getCurrentBookingCount();
//        this.setBookingCount(currentBookingCount);
        this.deleteBookingFor(hotelBooking);
        new ExtendedWait(driver, 30).forDeletedBookingToDisappear(currentBookingCount);
    }

    private void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    public void enterBookingDetailsAndSave(HotelBooking hotelBooking) {
        new HotelBookingPage(this.driver)
            .enterFirstName(hotelBooking.getFirstName())
            .enterSurname(hotelBooking.getLastName())
            .enterPrice(String.valueOf(hotelBooking.getPrice()))
            .selectDepositPaid(hotelBooking.getDeposit())
            .selectCheckin(hotelBooking.getCheckin())
            .selectCheckout(hotelBooking.getCheckout())
            .clickSave();
    }

    public List<HotelBooking> getAllTheBookings() {
        int totalBooking = driver.findElement(id("bookings"))
            .findElements(className("row")).size();

        return IntStream.rangeClosed(EXCLUDE_FIRST_ROW_PLUS_ARRAY_INDEX, totalBooking)
            .mapToObj(this::toBookingObjectForElement)
            .collect(Collectors.toList());
    }

    private HotelBooking toBookingObjectForElement(int elementInddex) {
        return new HotelBooking(
            driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-2:nth-child(1)")).getText(),
            driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-2:nth-child(2)")).getText(),
            new BigDecimal(driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-1>p")).getText()),
            driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-2:nth-child(4)")).getText(),
            driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-2:nth-child(5)")).getText(),
            driver.findElement(cssSelector("#bookings>.row:nth-child("+elementInddex+")>.col-md-2:nth-child(6)")).getText());
    }

    private void deleteBookingFor(HotelBooking booking) {
        int rowToDelete = getIndexMatching(booking) + EXCLUDE_FIRST_ROW_PLUS_ARRAY_INDEX;
        driver.findElement(By.cssSelector("#bookings>.row:nth-child("+rowToDelete+")>.col-md-1:nth-child(7)>input")).click();
    }

    private int getIndexMatching(HotelBooking booking) {
        List<HotelBooking> allTheBookings = getAllTheBookings();

        return IntStream.rangeClosed(0, allTheBookings.size())
            .filter(index -> allTheBookings.get(index).equals(booking))
            .findFirst()
            .orElseThrow((() -> new RuntimeException("Booking not found")));
    }
}

