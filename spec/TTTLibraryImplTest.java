import clojure.lang.PersistentVector;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 3/1/12
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class TTTLibraryImplTest {
    @Test
    public void getsEasyWin(){
        String board="oo xxoxx ";
        char player='o';
        int[] intendedMove={0,2};
        TTTLibrary tttLibrary=new TTTLibraryImpl();
        assertArrayEquals(intendedMove, tttLibrary.getMove(BoardStringParser.stringToBoardVector(board), player, null));
    }
    @Test
    public void returnsNullOnTie(){
        String board="xoxooxxxo";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(board);
        char player='o';
        TTTLibrary tttLibrary=new TTTLibraryImpl();
        assertNull(tttLibrary.getMove(boardVector, player,16));
        assertEquals("",tttLibrary.winner(boardVector));
    }
    @Test
    public void knowsBoardWinner(){
        String board="xxxxxxxxx";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(board);
        TTTLibrary tttLibrary=new TTTLibraryImpl();
        assertEquals("x",tttLibrary.winner(boardVector));
    }
    
    
}
