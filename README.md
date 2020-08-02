# Hotel manager
WAMD 202 Java Final project

## Overview
CLI base management system for CRUD reservation, print check-in/out.

## Specification
### Entities
- Hotel has many floors
- Floors has many rooms
- Room has many reservations, but **CANNOT** has many reservation on same date.
- Floors has fixed prices by type: _F2: Single: $xx, Double: $xx..._

### Functions and Flow
- User can make a request as customer
  - Input name, check in date, stay nights, number of person, budget
  - System recommend the cheapest plan possible
  - If there's no available room, system returns error message
  - If customer accept the plan, system create reservation and return reservation id
- User can get all reservations as manager
- User can delete the customer's information with reservation id
- User can print all reservation that check-in/out of today.

### Limitation
- Management system automatically recommend the cheapest room can be provided.
- Room's prices are fixed

## Member (Alphabetical)
- Ajay
- Akari
- Wonjae
