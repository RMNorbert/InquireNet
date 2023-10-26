Feature: Question Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to delete questions
    as needed when the user is on the forum page.

  @questionDelete
  Scenario Outline: Delete a selected question
    Given the user want to delete a question that belongs to the user
    When the user delete the "<title>" question
    Then the "<title>" question and its associated answers should be deleted

    Examples:
    |title  |
    |Title1 |
