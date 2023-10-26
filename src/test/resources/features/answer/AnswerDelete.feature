Feature: Answer Service
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to delete answers,
  as needed when on a selected question page,

  @answerDelete
  Scenario Outline: Deleting an answer in a selected question
    Given the user want to delete the user's answer for the selected question
    When the user click on the delete button on the "<description>" answer
    Then the "<description>" answer should be removed from the list

    Examples:
    |description|
    |desc       |
