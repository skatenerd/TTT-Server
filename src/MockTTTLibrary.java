import java.util.ArrayList;
import java.util.List;
import clojure.lang.*;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockTTTLibrary implements TTTLibrary{
    public List<String> _calls;
    public List<PersistentVector> _boardArgs;
    public List<Character> _playerArgs;
    public int [] _defaultMove={1,1};

    public MockTTTLibrary(){
        _calls = new ArrayList<String>();
        _boardArgs = new ArrayList<PersistentVector>();
        _playerArgs=new ArrayList<Character>();
    }
    
    public int [] getMove(PersistentVector board, char player){
        _calls.add("getMove");
        _boardArgs.add(board);
        _playerArgs.add(player);
        return _defaultMove;
    }
}
