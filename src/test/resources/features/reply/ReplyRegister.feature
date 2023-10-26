Feature: Reply Service
  A user of the InquireNet question and answer platform ,
    want to ensure that it is possible to interact with replies,
    view, add, edit, and delete them as needed.
    when the user is on a selected answer,

  @replyRegister
  Scenario Outline: Try to add a new reply
    Given the user want to add a new reply to the second answer
    When the user add the new reply to with the <description>
    Then the reply <description> should be added successfully

    Examples:
      | description  |
      | Reply1       |
