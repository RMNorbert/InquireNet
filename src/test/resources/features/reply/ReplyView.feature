Feature: Reply Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to interact with replies,
    view, add, edit, and delete them as needed.
    when the user is on a selected answer,

  @reply
  Scenario: Get a reply
    Given there is a reply the user want to interact with
    When the user requests the reply by clicking on it
    Then the user should receive the reply's information
