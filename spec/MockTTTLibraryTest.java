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
        MockTTTLibrary mockTTTLibrary=new MockTTTLibrary();
        int [][] board=new int [3][];
        int [] firstRow={1,1,1};
        int [] secondRow={-1,0,1};
        int [] thirdRow={1,0,-1};
        board[0]=firstRow;
        board[1]=secondRow;
        board[2]=thirdRow;
        mockTTTLibrary.getMove(board, -1);
        
        assertArrayEquals(board,mockTTTLibrary._boardArgs.get(0));
        assertEquals((int)-1,(int)mockTTTLibrary._playerArgs.get(0));
        assertEquals("getMove",mockTTTLibrary._calls.get(0));
    }


    
}
