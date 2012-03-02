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
        MockTTTLibrary mockTTTLibrary=new MockTTTLibrary(defaultMove);
        String board="xxxo xx o";
        int [] move = mockTTTLibrary.getMove(BoardStringParser.stringToBoardVector(board), 'o',74);
        assertEquals(BoardStringParser.stringToBoardVector(board),mockTTTLibrary._boardArgs.get(0));
        assertEquals('o',(char)mockTTTLibrary._playerArgs.get(0));
        assertEquals("getMove",mockTTTLibrary._calls.get(0));
        assertEquals(74,(int)mockTTTLibrary._depthArgs.get(0));
        assertEquals(1,move[0]);
        assertEquals(1,move[1]);
        
    }


    
}
