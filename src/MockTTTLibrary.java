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
    public List<Integer> _depthArgs;
    public String _winner=null;
    public int [] _defaultMove;

    public MockTTTLibrary(int [] move, String winner){
        _calls = new ArrayList<String>();
        _boardArgs = new ArrayList<PersistentVector>();
        _playerArgs=new ArrayList<Character>();
        _depthArgs=new ArrayList<Integer>();
        _defaultMove = move;
        _winner = winner;
    }

    public int [] getMove(PersistentVector board, char player, Integer maxDepth){
        _calls.add("getMove");
        _boardArgs.add(board);
        _playerArgs.add(player);
        _depthArgs.add(maxDepth);
        return _defaultMove;
    }
    
    public String winner(PersistentVector board){
        _boardArgs.add(board);
        _calls.add("winner");
        return _winner;
    }
    

}
