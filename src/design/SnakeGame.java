/*
353. Design a Snake game that is played on a device with screen size height x width. Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.

You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.

Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the snake eats the first piece of food.

When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body occupies after moving (i.e. a snake of length 4 cannot run into itself).

Implement the SnakeGame class:

SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and the positions of the food.
int move(String direction) Returns the score of the game after applying one direction move by the snake. If the game is over, return -1.

Input
["SnakeGame", "move", "move", "move", "move", "move", "move"]
[[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
Output
[null, 0, 0, 1, 1, 2, -1]

Explanation
SnakeGame snakeGame = new SnakeGame(3, 2, [[1, 2], [0, 1]]);
snakeGame.move("R"); // return 0
snakeGame.move("D"); // return 0
snakeGame.move("R"); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
snakeGame.move("U"); // return 1
snakeGame.move("L"); // return 2, snake eats the second food. No more food appears.
snakeGame.move("U"); // return -1, game over because snake collides with border
 

Constraints:

1 <= width, height <= 104
1 <= food.length <= 50
food[i].length == 2
0 <= ri < height
0 <= ci < width
direction.length == 1
direction is 'U', 'D', 'L', or 'R'.
At most 104 calls will be made to move.
*/
package design;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class SnakeGame {

    private int width;
    private int height;
    private LinkedList<Cell> foodCells;
    Snake snake;
    Map<String,Direction> directionMap;
    Cell currentFoodCell;
    public SnakeGame(int width, int height, int[][] food) {
        // width : column
        // height : row
        this.width = width;
        this.height = height;
        // adding food information
        foodCells = new LinkedList<>();
        for(int i=0;i<food.length;i++){
            int row = food[i][0];
            int col = food[i][1];
            Cell foodCell = new Cell(row, col);
            foodCells.add(foodCell);
        }
        snake = new Snake();
        directionMap = new HashMap<>();
        directionMap.put("U",new Direction(-1, 0));
        directionMap.put("D",new Direction(1, 0));
        directionMap.put("R",new Direction(0, 1));
        directionMap.put("L",new Direction(0, -1));

        currentFoodCell = new Cell(-1,-1);
    }
    
    public int move(String direction) {
        Direction newDirection = directionMap.get(direction);
        Cell snakeHead = snake.getSnakeHead();
        Cell nextCell = new Cell(snakeHead.getX()+newDirection.getX(),snakeHead.getY()+newDirection.getY());

        if(!nextCell.isValidCell(height, width)){
            return -1;
        }
        if(!foodCells.isEmpty() && currentFoodCell.equals(new Cell(-1,-1))){
            currentFoodCell = foodCells.pollFirst();
        }
        int moveResult = snake.move(nextCell,currentFoodCell.equals(nextCell));

        if(currentFoodCell.equals(nextCell))
        {
            currentFoodCell = new Cell(-1,-1);
        }

        return moveResult;
    }
}

class Snake{
    private int length;
    private ArrayDeque<Cell> snakeOccupiedCellsList;
    private Set<Cell> snakeOccupiedCellsSet;
    Snake(){
        length = 0;
        snakeOccupiedCellsList = new ArrayDeque<>();
        snakeOccupiedCellsSet = new HashSet<>();
        Cell initialCell = new Cell(0, 0);
        snakeOccupiedCellsList.addLast(initialCell);
        snakeOccupiedCellsSet.add(initialCell);
    }

    public Cell getSnakeHead(){
        return snakeOccupiedCellsList.getLast();
    }

    public Set<Cell> getSnakeOccupiedCells(){
        return snakeOccupiedCellsSet;
    }

    // successful move returns new snake size
    public int move(Cell nextCell, boolean isFoodCell){
        // either this is a foodCell, or this is a emptyCell
        if(isFoodCell){
            length++;
        }else{
            // snake moved
            Cell vacatedCell = snakeOccupiedCellsList.pollFirst();
            snakeOccupiedCellsSet.remove(vacatedCell);
        }

        // if there is a collision
        if(snakeOccupiedCellsSet.contains(nextCell))
            return -1;

        snakeOccupiedCellsList.addLast(nextCell);
        snakeOccupiedCellsSet.add(nextCell);

        return length;
    }
}

class Direction{
    private int x;
    private int y;
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
}

class Cell{
    int x;
    int y;
    Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    boolean isValidCell(int row, int col){
        return (x>=0 && x<row && y>=0 && y<col);
    }
    @Override
    public String toString() {
        return "Cell [x=" + x + ", y=" + y + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }   
}
