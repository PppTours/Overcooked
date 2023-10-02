# How to Build a Map
## Files needed
- A txt file providing the map's information :
  - The map's name
  - The map's difficulty
  - The score needed to win
  - The map's type
- A png file providing a preview of the map
- A csv file providing the map's layout

## List of map type :
- BURGER
- CHICKEN
- NOODLES
- PIZZA
- SALAD
- SOUP

## Map layout
- For each tile, the csv file must contain a number corresponding to the cell's 
type, tile will be described after.
- After the layout, we could find : 
  - The X pos of the first player
  - The Y pos of the first player
  - The X pos of the second player
  - The Y pos of the second player
  - The type of the map

## Tile types
- 0 : Empty
- 1: Table
  - 1b : Table with a plate
  - 1a : Table without a plate
- 2 : Cutting table
- 3 : GasCooker
  - 3a : GasCooker with a Pot
  - 3b : GasCooker with a Pan
- 4 : Sink
  - 4a : Sink directed to 
  - 4b : Sink directed to
  - 4c : Sink directed to
  - 4d : Sink directed to
- 5 : dryer
  - 5a : dryer directed to
  - 5b : dryer directed to
  - 5c : dryer directed to
  - 5d : dryer directed to
- 6 : Ingredient Container :
    - 6a : Tomato container
    - 6b : Salad container
    - 6c : Onion container
    - 6d : Mushroom container
    - 6e : Meat container
    - 6f : Cheese container
    - 6g : Pasta container
    - 6h : Chorizo container
    - 6i : Pizza Dough container
    - 6j : Bread container
    - 6k : Rice container
    - 6l : Potato container
- 7 : Counter
  - 7a : Counter directed to
  - 7b : Counter directed to
  - 7c : Counter directed to
  - 7d : Counter directed to
- 8 : Return of dirty plates
- 9 : Oven
- 10 : Wall
- 11 : Trash
