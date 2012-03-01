import clojure.lang.*;
import sun.tools.tree.CastExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


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
        try{
            RT.load("tictactoe.minimax");
            Var minimax=RT.var("tictactoe.minimax","compute-next-move");

            PersistentVector boardvector=stringToBoardVector(board);
            Object move=minimax.invoke(boardvector,Keyword.intern("x"),9);
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
    
    private PersistentVector stringToBoardVector(String boardString){
        int inc=3;
        List<PersistentVector> boardVectors=new ArrayList<PersistentVector>();
        for(int i=0; i+inc<=boardString.length();i+=inc){
            String substring=boardString.substring(i,i+inc);
            boardVectors.add(stringToVector(substring));
        }
        return PersistentVector.create(boardVectors);
    }
    
    private PersistentVector stringToVector(String rowString){
        List<Keyword> keywords=new ArrayList<Keyword>();
        for(int i=0;i<rowString.length();i++){
            String currentChar=rowString.substring(i,i+1);
            if(currentChar.equals(" ")){
                keywords.add(null);
            }else{
                keywords.add(Keyword.intern(currentChar));
            }
        }
        return PersistentVector.create(keywords);
    }
}
