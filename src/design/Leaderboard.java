/*
1244. Design a Leaderboard class, which has 3 functions:

addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.
top(K): Return the score sum of the top K players.
reset(playerId): Reset the score of the player with the given id to 0 (in other words erase it from the leaderboard). It is guaranteed that the player was added to the leaderboard before calling this function.
Initially, the leaderboard is empty.

 

Example 1:

Input: 
["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
Output: 
[null,null,null,null,null,null,73,null,null,null,141]

Explanation: 
Leaderboard leaderboard = new Leaderboard ();
leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
leaderboard.top(1);           // returns 73;
leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
leaderboard.top(3);           // returns 141 = 51 + 51 + 39;
 

Constraints:

1 <= playerId, K <= 10000
It's guaranteed that K is less than or equal to the current number of players.
1 <= score <= 100
There will be at most 1000 function calls.
*/
package design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Leaderboard {
    HashMap<Integer,Integer> scoreMap;
    PriorityQueue<Player> pq;
    public Leaderboard() {
        scoreMap = new HashMap<Integer,Integer>();
        pq = new PriorityQueue<>((Player a, Player b)->{
            return b.getScore()-a.getScore();
        });
    }
    
    public void addScore(int playerId, int score) {
        int _score = 0;
        if(scoreMap.containsKey(playerId)){
            _score = scoreMap.get(playerId);
            pq.remove(new Player(playerId, _score));
        }
        pq.add(new Player(playerId, score+_score));
        scoreMap.put(playerId, score+_score);
    }
    
    public int top(int K) {
        int sum = 0;
        ArrayList<Player> list = new ArrayList<Player>();
        System.out.println("calculating top K");
        while(K-- > 0){
            Player player = pq.poll();
            sum += player.getScore();
            list.add(player);
            System.out.println(player.getPlayerId()+" "+player.getScore());
        }
        for(int i=0;i<list.size();i++){
            pq.add(list.get(i));
        }
        return sum;
    }
    
    public void reset(int playerId) {
        int score = scoreMap.get(playerId);
        pq.remove(new Player(playerId, score));
        scoreMap.remove(playerId);
    }
}
class Player{
    int playerId;
    int score;
    Player(int playerId, int score){
        this.playerId = playerId;
        this.score = score;
    }
    int getPlayerId(){
        return playerId;
    }
    int getScore(){
        return score;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerId;
        result = prime * result + score;
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
        Player other = (Player) obj;
        if (playerId != other.playerId)
            return false;
        if (score != other.score)
            return false;
        return true;
    }
}
