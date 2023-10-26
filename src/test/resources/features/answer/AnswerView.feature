Feature: Answer Service
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to view answers
  as needed when on a selected question page,

  @answer
  Scenario Outline: Viewing answers to a selected question
    Given the user want to view the answers of a question
    When the user click on the "<title>" question
    Then the user should see a list of answers

    Examples:
    |title                 |
    |What does the fox say |
