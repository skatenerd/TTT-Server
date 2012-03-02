import clojure.lang.PersistentVector;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockTTTLibraryTest {
    @Test
    public void recordsCalls(){
        int [] defaultMove={1,1};
        MockTTTLibrary mockTTTLibrary=new MockTTTLibrary(defaultMove,null);
        String board="xxxo xx o";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(board);
        int [] move = mockTTTLibrary.getMove(boardVector, 'o',74);
        assertEquals(boardVector,mockTTTLibrary._boardArgs.get(0));
        assertEquals('o',(char)mockTTTLibrary._playerArgs.get(0));
        assertEquals("getMove",mockTTTLibrary._calls.get(0));
        assertEquals(74,(int)mockTTTLibrary._depthArgs.get(0));
        assertEquals(1,move[0]);
        assertEquals(1,move[1]);
        

        PersistentVector nextBoardVector=BoardStringParser.stringToBoardVector(board);
        String winner=mockTTTLibrary.winner(nextBoardVector);
        assertNull(winner);
        assertEquals(nextBoardVector, mockTTTLibrary._boardArgs.get(1));
        assertEquals("winner",mockTTTLibrary._calls.get(1));
        
    }


    
}
