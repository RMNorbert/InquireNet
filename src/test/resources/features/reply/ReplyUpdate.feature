Feature: Reply Service
    A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to interact with replies,
    view, add, edit, and delete them as needed.
    when the user is on a selected answer,

  @replyUpdate
  Scenario Outline: Try to update a reply
    Given there is a reply of the user that the user want to update
    When the user want to update the user's reply
    And the user is the owner of the reply
    When the user update the reply with <text>
    Then the reply should be updated with the text <text>

    Examples:
      | text |
      | updateText  |
