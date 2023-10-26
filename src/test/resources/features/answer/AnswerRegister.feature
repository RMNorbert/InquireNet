Feature: Answer Service
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to add answers
  as needed when on a selected question page,

  @answerRegister
  Scenario Outline: Adding a new answer to a selected question
    Given the user want to answer a selected question
    When the user provide the "<description>" answer to the question
    And the user submit the answer by using the Reply button
    Then "<description>" should be added to the list of answers

    Examples:
    |description|
    |answer     |
