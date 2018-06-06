@retailerServices
Feature: Retailer Services

  Background:
    Given the base url
  |http://www.volkswagen.co.uk/api/retailer/1.7|


  Scenario Outline: User can list the services offered by a retailer
    When I retrieve the services of a retailer with retailer id
      | Retailer Id  |
      | <retailerId> |
    Then the status code is 200
    And I can print the services offered by the retailer
      | Retailer Id  |
      | <retailerId> |

    Examples:
      | retailerId |
      | 00032      |
      | 00826      |


  Scenario Outline: User can list a brief introduction of a retailer
    When I retrieve a brief introduction of all the retailers
    Then the status code is 200
    And I can print a brief introduction of a retailer
      | Retailer Name  |
      | <retailerName> |

    Examples:
      | retailerName       |
      | Canterbury Volkswagen |
