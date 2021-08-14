import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameT
{

    Game game1;
    TTTPlayer p1,p2;

    @BeforeEach
    void setUp()
    {
        p1 = new TTTPlayer("Arbi",'X');
        p2 = new TTTPlayer("Marko",'O');
        game1 = new Game(p1,p2);
        System.out.println(game1.getTTTPlayer()[0].getName()+" "+game1.getTTTPlayer()[0].getSymbol());
        System.out.println(game1.getTTTPlayer()[1].getName()+" "+ game1.getTTTPlayer()[1].getSymbol());
    }


    //-------------------------------------restartGame()-------------------------------------

    @Test
    void restartGame()
    {
        game1.getBoard().setSymbol(1,1,1);
        game1.restartGame();
        assertEquals(0,game1.getBoard().getField()[0][0]);
    }


    //-------------------------------------checkForWinner()-------------------------------------

    @Test
    void checkForWinner1()
    {
        game1.getBoard().setSymbol(1,0,0);
        game1.getBoard().setSymbol(2,0,1);
        game1.getBoard().setSymbol(1,1,1);
        game1.getBoard().setSymbol(2,2,1);
        game1.getBoard().setSymbol(1,2,2);

        assertEquals(1,game1.checkForWinner());
    }

    @Test
    void checkForWinner2()
    {
        game1.getBoard().setSymbol(2,0,0);
        game1.getBoard().setSymbol(1,0,1);
        game1.getBoard().setSymbol(2,1,1);
        game1.getBoard().setSymbol(1,2,1);
        game1.getBoard().setSymbol(2,2,2);

        assertEquals(2,game1.checkForWinner());
    }


    //-------------------------------------checkIfFieldIsFull()-------------------------------------

    @Test
    void checkIfFieldIsFullTrue()
    {
        game1.getBoard().setSymbol(1,0,0);
        game1.getBoard().setSymbol(2,1,0);
        game1.getBoard().setSymbol(1,2,0);
        game1.getBoard().setSymbol(2,0,1);
        game1.getBoard().setSymbol(1,1,1);
        game1.getBoard().setSymbol(2,2,1);
        game1.getBoard().setSymbol(1,0,2);
        game1.getBoard().setSymbol(2,1,2);
        game1.getBoard().setSymbol(1,2,2);

        assertTrue(game1.checkIfFieldIsFull());
    }

    @Test
    void checkIfFieldIsFullFalse()
    {
        assertFalse(game1.checkIfFieldIsFull());
    }
}
