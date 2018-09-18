Feature:
  As a end user
  In order to cancel a booking
  I would like to be able to delete it from the website

  Background:
    Given I am on the hotel booking page
    When I enter details and book
      | firstName | Pete       |
      | lastName  | Mitchee      |
      | price     | 99.99      |
      | deposit   | false      |
      | checkin   | 2018-09-09 |
      | checkout  | 2018-09-10 |

  Scenario: Booking can be deleted
    When I delete the my hotel booking
    Then booking is deleted