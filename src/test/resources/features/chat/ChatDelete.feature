Feature: Chat Service
  A user of the InquireNet question and answer platform ,
  the user want to ensure that it is possible to delete chats
  as needed when the user is on the chat page.

  @chatDelete
  Scenario Outline: Delete a chat by title
    Given the user want to delete a chat
    When the user delete the "<title>" chat
    Then the chat with the title "<title>" should be deleted

    Examples:
      | title  |
      | Title1 |
