Feature: Question Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to add questions
    as needed when the user is on the forum page.

  @questionRegister
  Scenario Outline: Try to add a new question
    Given the user want to add a new question
    When the user use the create new question button
    And the user add the new question with "<title>" and "<content>"
    Then the question with "<title>" should be added successfully

    Examples:
      | title  | content   |
      | Title1 | Content1  |
