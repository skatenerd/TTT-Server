import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 3/2/12
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoardStringParserTest {
    @Test
    public void parsesFullNineBoard(){
        String boardString="xoxoxoxox";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(boardString);
        PersistentVector firstRow=(PersistentVector)boardVector.get(0);
        PersistentVector thirdRow=(PersistentVector)boardVector.get(2);
        assertEquals(Keyword.intern("x"),firstRow.get(0));
        assertEquals(Keyword.intern("o"),thirdRow.get(1));
    }

    @Test
    public void parsesFullSixteenBoard(){
        String boardString="xoxoxoxoxoooxxxo";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(boardString);
        PersistentVector fourthRow=(PersistentVector)boardVector.get(3);
        assertEquals(Keyword.intern("o"),fourthRow.get(3));
        assertEquals(Keyword.intern("x"),fourthRow.get(0));
    }
    
    @Test
    public void leavesAHangingEntryOnSeventeenBoard(){
        String boardString="xoxoxoxoxoooxxxox";
        PersistentVector boardVector=BoardStringParser.stringToBoardVector(boardString);
        PersistentVector fifthRow=(PersistentVector)boardVector.get(4);
        assertEquals(1,fifthRow.count());
        assertEquals(Keyword.intern("x"),fifthRow.get(0));
    }

    
    
}
