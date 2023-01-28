Feature: Application items

Scenario: Register a new sound
  Given I have an item without sounds
  When I POST it to the /items endpoint
  Then I receive a 201 status code