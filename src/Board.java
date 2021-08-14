public final class Board
{
    private int[][] field;

    public int[][] getField() { return field; }
    public void setField(int[][] field) { this.field = field; }

    Board(int fieldsize)
    {
        setField((new int[fieldsize][fieldsize]));
    }


    //Setzt ein Symbol/Zeichen. Bzw. in dem System eine Zahl.
    //1     , wenn Spieler 1 sein Zeichen setzen möchte.
    //2     , wenn Spieler 2 sein Zeichen setzen möchte.
    //Die Umformung, zur grafischen Darstellung wird in drawField() umgesetzt.

    public void setSymbol(int playerNr,int x,int y)
    {

        this.field[y][x] = playerNr;
    }


    //Kotrolle, ob der gewünschte Slot frei ist.

    public boolean checkForSpace(int x, int y)
    {

        return getField()[y][x] == 0;
    }


    //Drei Methoden für die Methode checkForWinner() aus Game.

    public int horCheck()
    {
        for (int i = 0; i < Game.FIELD_SIZE; i++)
        {
            if ((getField()[i][0] == 1)&&(getField()[i][1] == 1)&&(getField()[i][2] == 1))
            {
                return 1;
            }
            else if ((getField()[i][0] == 2)&&(getField()[i][1] == 2)&&(getField()[i][2] == 2))
            {
                return 2;
            }
        }
        return 0;
    }
    public int verCheck()
    {
        for (int i = 0; i < Game.FIELD_SIZE; i++)
        {
            if ((getField()[0][i] == 1)&&(getField()[1][i] == 1)&&(getField()[2][i] == 1))
            {
                return 1;
            }
            else if ((getField()[0][i] == 2)&&(getField()[1][i] == 2)&&(getField()[2][i] == 2))
            {
                return 2;
            }
        }
        return 0;
    }
    public int diaCheck()
    {
        if (((getField()[0][0] == 1)&&(getField()[1][1] == 1)&&(getField()[2][2] == 1))||((getField()[0][2] == 1)&&(getField()[1][1] == 1)&&(getField()[2][0] == 1)))
        {
            return 1;
        }
        else if (((getField()[0][0] == 2)&&(getField()[1][1] == 2)&&(getField()[2][2] == 2))||((getField()[0][2] == 2)&&(getField()[1][1] == 2)&&(getField()[2][0] == 2)))
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
}
