Feature:
  As a end user
  In order to check in the hotel
  I would like to book a room

  Scenario: The customer can book a room on a specified date
    Given I am on the hotel booking page
    When I enter details and book
    | firstName | Mark       |
    | lastName  | Ravan      |
    | price     | 89.99      |
    | deposit   | false      |
    | checkin   | 2018-09-09 |
    | checkout  | 2018-09-10 |
    Then the booking is successful
