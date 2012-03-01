import sun.tools.tree.CastExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class TTTLibraryImpl implements TTTLibrary{
    public TTTLibraryImpl(){}
    
    public int [] getMove(String board, char player){
        int [] rtn=null;
        Object raw=minimax.computeNextMoveFromString(board,new Character(player).toString());
        if(raw!=null){
            List<Object> asList=(ArrayList<Object>)raw;
            rtn=new int[2];
            rtn[0]=(Integer)asList.get(0);
            rtn[1]=(Integer)asList.get(1);
        }
        return rtn;

    }
}
