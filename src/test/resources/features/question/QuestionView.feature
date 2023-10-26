Feature: Question Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to view questions
    as needed when the user is on the forum page.

  @question
  Scenario: User want to see the questions
    Given there are questions in the system
    When the user is on the forum page
    Then the user should receive a list of question information

  @questionSelect
  Scenario Outline: Get a selected question
    Given there is a question the user want to interact with
    When the user requests the "<title>" question by clicking on it
    Then the user should get the question's information by navigate to "<url>"

    Examples:
    |title                  | url        |
    |What does the fox say? | question/1 |
