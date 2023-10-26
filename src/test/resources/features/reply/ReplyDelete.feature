Feature: Reply Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to interact with replies,
    view, add, edit, and delete them as needed.
    when the user is on a selected answer,

  @replyDelete
  Scenario: Delete a reply
    Given there is a reply of the user that the user want to delete
    When the user delete the user's the reply
    Then the reply should be deleted
