Feature: Answer Service
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to vote on answers
  as needed when on a selected question page,

  @answerUpdate
  Scenario Outline: Attempting to vote on answer in a question that belongs to the user
    Given the user want to vote on an answer in the user's question
    When the user want to vote on an answer in the user's question from the list of answers
    And the user click on one of the vote button on the "<description>" answer
    Then the user should see that the answer card background changes

    Examples:
    |description|
    |desc       |
