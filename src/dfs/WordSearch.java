package dfs;

import java.util.Arrays;

public class WordSearch {
    public static void main(String[] args) {
        WordSearchRunner solution = new WordSearchRunner();
        char[][] board = {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };
        String word = "ABCCED";
        System.out.println(solution.exist(board, word));
    }
}

class WordSearchRunner {
        public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        boolean wordFound = false;
        boolean isVisited[][] = new boolean[rows][cols];
        for (boolean row[] : isVisited) {
            Arrays.fill(row, false);
            // System.out.println(Arrays.toString(row));
        }

        for (int i = 0; i < rows && !wordFound; i++) {
            for (int j = 0; j < cols && !wordFound; j++) {
                if (board[i][j] == word.charAt(0)) {
                    wordFound = wordFound || dfs(i, j, board, word, 0);
                }
            }
        }
        return wordFound;
    }

    private boolean dfs(int i, int j, char[][] board, String word, int wordIndex) {
        if (wordIndex == word.length()) {
            return true;
        }

        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(wordIndex)) {
            // System.out.println(i+" "+j+" "+" "+word.charAt(wordIndex));
            return false;
        }
        // System.out.println(i+" "+j+" "+" "+word.charAt(wordIndex));
        char originalCharacter = board[i][j];
        board[i][j] = '#';

        boolean backTrackResult = dfs(i+1,j,board,word,wordIndex+1)
                                || dfs(i-1,j,board,word,wordIndex+1)
                                || dfs(i,j+1,board,word,wordIndex+1)
                                || dfs(i,j-1,board,word,wordIndex+1);

        board[i][j] = originalCharacter;
        // System.out.println("backTrackResult"+backTrackResult);
        return backTrackResult;
    }
}


