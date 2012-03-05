import clojure.lang.*;


/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class TTTLibraryImpl implements TTTLibrary{
    public TTTLibraryImpl(){}
    
    public int [] getMove(PersistentVector boardVector, char player, Integer maxDepth){
        int [] rtn=null;
        try{
            RT.load("tictactoe.minimax");
            Var minimax=RT.var("tictactoe.minimax","compute-next-move");


            Object move=minimax.invoke(boardVector,Keyword.intern("x"),maxDepth);
            if(move != null){
                PersistentVector moveVector=(PersistentVector) move;
                int rowCoordinate=(Integer)moveVector.get(0);
                int colCoordinate=(Integer)moveVector.get(1);
                rtn=new int[2];
                rtn[0]=rowCoordinate;
                rtn[1]=colCoordinate;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return rtn;
    }

    public String winner(PersistentVector board){
        String winnerString="";
        try{
        RT.load("tictactoe.board_utils");
        Var winnerFn=RT.var("tictactoe.board-utils","game-winner");
        Object winner=winnerFn.invoke(board);
        if(winner!=null){
            winnerString=((Keyword) winner).getName();
        }

        }catch(Exception e){
            e.printStackTrace();
        }
        return winnerString;
    }

    public boolean playerValid(String  player){
        boolean valid=false;
        try{
            if(player!=null &&player.length() ==1){
                RT.load("tictactoe.board_utils");
                Var validator=RT.var("tictactoe.board-utils","player-valid?");
                valid = (Boolean)validator.invoke(Keyword.intern(player));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return valid;
    }

    public boolean boardValid(PersistentVector board){
        boolean valid=false;
        try{

            RT.load("tictactoe.board_utils");
            Var validator=RT.var("tictactoe.board-utils","board-legal");
            valid = (Boolean)validator.invoke(board);
        }catch(Exception e){
            e.printStackTrace();
        }
        return valid;
    }
}
