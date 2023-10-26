Feature: User Authentication
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to login on InquireNet.

  @userLoginFail
  Scenario Outline: Authenticate with invalid password
    Given the user want to authenticate with an invalid password:
    When the user attempts to authenticate with invalid "<username>" and "<password>"
    Then the authentication should fail with the invalid password
    And the user should stay on the login page

    Examples:
      | username | password  |
      | username | password  |

  @userLogin
  Scenario Outline: Authenticate with valid credentials
    Given the user is on the login page and want to login
    When the user enters "<username>" and "<password>"
    And user click on login
    Then the user should navigated to the forum page

    Examples:
    | username | password |
    | user | password |


