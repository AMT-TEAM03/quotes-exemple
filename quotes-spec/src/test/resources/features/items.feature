Feature: Application items

Scenario: Register a new item with id:1500 and get it
  Given I have an item with the id:1500 and the name:TestItem
  When I POST it to the items endpoint
  Then I receive a 201 status code
  When I fetch all the items
  Then I expect id:4 name:TestItem to be part of the response

Scenario: Update an existing item with PUT
  Given I have an item with the id:1 and the name:coucou
  When I PUT it to the items endpoint
  Then I receive a 200 status code
  When I fetch all the items
  Then I expect id:1 name:coucou to be part of the response

Scenario: Create an item with PUT
  Given I have an item with the id:1400 and the name:Youpi
  When I PUT it to the items endpoint
  Then I receive a 201 status code
  When I fetch all the items
  Then I expect id:5 name:Youpi to be part of the response

Scenario: Update an item with PATCH
  Given I have an item with the id:1 and the name:OhHello
  When I PATCH it to the items endpoint
  Then I receive a 200 status code
  When I fetch all the items
  Then I expect id:1 name:OhHello to be part of the response

Scenario: I delete the item 1 and expect it to be gone
  When I delete 1
  Then I receive a 200 status code
  When I fetch all the items
  Then I expect id:1 to be deleted