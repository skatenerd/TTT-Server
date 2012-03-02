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
        char player='o';
        TTTLibrary tttLibrary=new TTTLibraryImpl();
        assertTrue(null==tttLibrary.getMove(BoardStringParser.stringToBoardVector(board), player,16));
    }
    
    
}
