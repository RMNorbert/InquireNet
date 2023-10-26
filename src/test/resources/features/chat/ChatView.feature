Feature: Chat Service
  A user of the InquireNet question and answer platform ,
  the user want to ensure that it is possible to view previous chats
  as needed when the user is on the chat page.

  @chat
  Scenario: Get all chats for a user
    Given the user want to get all previous chats that belongs to the user
    When the user clicks on the Chat with AI button
    Then the user should receive a list of chat information
