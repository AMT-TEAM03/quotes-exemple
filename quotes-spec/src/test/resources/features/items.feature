Feature: Application items

Scenario: Register a new item with id:1500 and get it
  Given I have an item with the id:1500
  When I POST it to the items endpoint
  Then I receive a 201 status code
  When I fetch all the items
  Then I expect id:1500 to be part of the reponse

#Scenario: