Feature: User Authentication
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to register on InquireNet.

  @userRegisterFail
  Scenario Outline: Register with an existing username
    Given the user is on the register page and want to register with an existing username:
    When the user try to registers with the existing "<username>" and "<password>"
    Then the registration should fail
    And the user should stay on the register page

    Examples:
      | username | password    |
      | user | password    |

  @userRegister
  Scenario Outline: Register a new user
    Given a user is on the register page and want to register:
    When the user try to register by entering "<username>" and "<password>"
    Then the registration should be successful
    And the user should navigated to the login page

    Examples:
      | username | password    |
      | usersname | passwords    |
