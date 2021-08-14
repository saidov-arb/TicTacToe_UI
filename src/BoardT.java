import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardT
{
    Board board;
    int[][] field;


    @BeforeEach
    void setUp()
    {
        board = new Board(3);
        field = board.getField();
    }


    //-------------------------------------checkForSpace()-------------------------------------

    @Test
    void setSymbolAt1and2()
    {
        board.setSymbol(1,1,0);
        assertEquals(1, board.getField()[0][1]);
    }

    @Test
    void setSymbolAt2and3()
    {
        board.setSymbol(2,2,1);
        assertEquals(2,board.getField()[1][2]);
    }


    //-------------------------------------checkForSpace()-------------------------------------

    @Test
    void checkForSpaceWith1and2False()
    {
        int[][] field = board.getField();
        field[0][1] = 1;
        board.setField(field);
        assertFalse(board.checkForSpace(1, 0));
    }

    @Test
    void checkForSpaceWith1and2True()
    {
        assertTrue(board.checkForSpace(1,0));
    }


    //-------------------------------------horCheck()-------------------------------------

    @Test
    void horCheckWinnerIsNoone()
    {
        field[0][0] = 1;
        field[0][1] = 2;
        field[0][2] = 2;
        board.setField(field);
        assertEquals(0,board.horCheck());
    }

    @Test
    void horCheckWinnerIs1()
    {
        field[0][0] = 1;
        field[0][1] = 1;
        field[0][2] = 1;
        board.setField(field);
        assertEquals(1,board.horCheck());
    }

    @Test
    void horCheckWinnerIs2()
    {
        field[0][0] = 2;
        field[0][1] = 2;
        field[0][2] = 2;
        board.setField(field);
        assertEquals(2,board.horCheck());
    }


    //-------------------------------------verCheck()-------------------------------------

    @Test
    void verCheckWinnerIsNoone()
    {
        field[0][0] = 1;
        field[1][0] = 2;
        field[2][0] = 2;
        board.setField(field);
        assertEquals(0,board.verCheck());
    }

    @Test
    void verCheckWinnerIs1()
    {
        field[0][0] = 1;
        field[1][0] = 1;
        field[2][0] = 1;
        board.setField(field);
        assertEquals(1,board.verCheck());
    }

    @Test
    void verCheckWinnerIs2()
    {
        field[0][0] = 2;
        field[1][0] = 2;
        field[2][0] = 2;
        board.setField(field);
        assertEquals(2,board.verCheck());
    }


    //-------------------------------------diaCheck()-------------------------------------

    @Test
    void diaCheckWinnerIsNoone()
    {
        field[0][0] = 1;
        field[1][1] = 2;
        field[2][2] = 1;
        board.setField(field);
        assertEquals(0,board.diaCheck());
    }

    @Test
    void diaCheckWinnerIs1()
    {
        field[0][2] = 1;
        field[1][1] = 1;
        field[2][0] = 1;
        board.setField(field);
        assertEquals(1,board.diaCheck());
    }

    @Test
    void diaCheckWinnerIs2()
    {
        field[0][0] = 2;
        field[1][1] = 2;
        field[2][2] = 2;
        board.setField(field);
        assertEquals(2,board.diaCheck());
    }
}
