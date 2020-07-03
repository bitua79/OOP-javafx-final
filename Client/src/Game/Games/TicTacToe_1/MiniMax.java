package Game.Games.TicTacToe_1;

public class MiniMax {

    static int miniMax(Cell[][] board, int depth, Boolean isMax) {
        //depth Is To Choose Earliest Move To Win And
        //Latest Move To Lose

        // If Maximizer Player Has Won
        // return Player Evaluated Score
        if (Board.boardSituation(board) == 2)
            return 10 - depth;

        // If Minimizer Player Has Won
        // return Player Evaluated Score
        if (Board.boardSituation(board) == 1)
            return -10 + depth;

        //If Game Finished And It's A Tie
        if (Board.boardSituation(board) == 3)
            return 0;

        // Traverse all cells
        // And Play By End Of The Game Recursively In Each Position

        // If This Is Maximizer's Move
        if (isMax) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check If Cell Is Empty
                    if (board[i][j].getShoot() ==' ') {

                        // Make The Temp Move
                        board[i][j].setShootTemp('O');

                        // Call MiniMax Recursively And Choose
                        // The Maximum Value
                        best = Math.max(best, miniMax(board, depth + 1, false));

                        // Undo The Move
                        board[i][j].setShootTemp(' ');
                    }
                }
            }
            return best;
        }

        // If This Is Minimizer's Move
        else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check If Cell Is Empty
                    if (board[i][j].getShoot() == ' ') {

                        // Make The Temp Move
                        board[i][j].setShootTemp('X');

                        // Call miniMax recursively and choose
                        // The Minimum Value
                        best = Math.min(best, miniMax(board, depth + 1, true));

                        // Undo The Move
                        board[i][j].setShootTemp(' ');
                    }
                }
            }
            return best;
        }
    }

    static int[] findBestMove(Cell[][] board) {
        int bestVal = -1000;

        int row = -1;
        int col = -1;

        // Traverse All Cells, Evaluate MiniMax Function
        // For All Empty Cells. And Return The Cell
        // With Optimal Value.

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j].getShoot() == ' ') {

                    // Make The Move
                    board[i][j].setShootTemp('O');

                    // Compute Evaluation Function For This Move
                    //false Because We Choose Our Maximizer Player
                    //Actually Choose All Reminder Boar Cells And
                    // Continue Game By Minimizer Turn

                    int moveVal = miniMax(board, 0, false);

                    // Undo the move
                    board[i][j].setShootTemp(' ');

                    // If The Value Of The Current Move Is
                    // More Than The Best Value, Then Update
                    // Best
                    if (moveVal > bestVal)
                    {
                        row = i;
                        col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        int[] result = new int[2];
        result[0] = row;
        result[1] = col;

        return result;
    }
}
