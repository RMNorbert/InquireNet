Feature: Question Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to  edit questions
    as needed when the user is on the forum page.

  @questionUpdate
  Scenario Outline: Try to update a question
    Given there is a question the user want to update
    When the user want to update "<title>" question that is belong to the user
    When the user update the question with "<title>"
    Then the question should be updated with "<title>"

    Examples:
    |title|
    |sample|
