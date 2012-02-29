import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockTTTLibrary implements TTTLibrary{
    public List<String> _calls;
    public List<int[][]> _boardArgs;
    public List<Integer> _playerArgs;
    public int [] _defaultMove={1,1};

    public MockTTTLibrary(){
        _calls = new ArrayList<String>();
        _boardArgs = new ArrayList<int[][]>();
        _playerArgs=new ArrayList<Integer>();
    }
    
    public int [] getMove(int [][]board, int player){
        _calls.add("getMove");
        _boardArgs.add(board);
        _playerArgs.add(player);
        return _defaultMove;
    }
}
