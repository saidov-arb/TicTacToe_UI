public final class Game
{
    public final static int FIELD_SIZE = 3;

    private TTTPlayer[] tttPlayer = new TTTPlayer[2];
    private Board board;

    public TTTPlayer[] getTTTPlayer() { return this.tttPlayer; }
    public Board getBoard() { return this.board; }
    public void setBoard(Board board) { this.board = board; }

    Game(TTTPlayer p1,TTTPlayer p2)
    {
        this.tttPlayer[0] = p1;
        this.tttPlayer[1] = p2;
        setBoard((new Board(FIELD_SIZE)));
    }


    //Das Feld zurücksetzen, bzw. ein neues Feld anlegen.

    public void restartGame()
    {

        setBoard((new Board(FIELD_SIZE)));
    }


    //Mit den 3 Methoden aus Board, den Gewinner ermitteln.
    //0 = kein Gewinner
    //1 = getPlayer()[0]
    //2 = getPlayer()[1]

    public int checkForWinner()
    {
        if (getBoard().horCheck() == 0)
        {
            if (getBoard().verCheck() == 0)
            {
                return getBoard().diaCheck();
            }
            else
            {
                return getBoard().verCheck();
            }
        }
        else
        {
            return getBoard().horCheck();
        }
    }


    //Dient lediglich dazu, zu kontrollieren, ob das Feld voll ist.

    public boolean checkIfFieldIsFull()
    {
        for (int i = 0; i < FIELD_SIZE; i++)
        {
            for (int j = 0; j < FIELD_SIZE; j++)
            {
                if (getBoard().getField()[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
