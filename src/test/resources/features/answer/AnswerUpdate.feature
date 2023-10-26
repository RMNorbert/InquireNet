Feature: Answer Service
  A user of the InquireNet question and answer platform ,
  want to ensure that it is possible to update answers
  as needed when on a selected question page,

  @answerUpdate
  Scenario Outline: Editing an existing answer in a selected question
    Given the user want to edit the user's answer for a selected question
    When the user search for the answer to edit from the list of answers
    And the user click on the update button on the "<description>" answer
    And the user edit the content with "<updateDesc>" of the answer and submit it
    Then the answer should be updated with the content "<updateDesc>"

    Examples:
    |description|updateDesc |
    |desc       |description|
