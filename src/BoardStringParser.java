import clojure.lang.Keyword;
import clojure.lang.PersistentVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 3/1/12
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardStringParser {
    public static PersistentVector stringToBoardVector(String boardString){
        int inc=(int)Math.sqrt(boardString.length());
        List<PersistentVector> boardVectors=new ArrayList<PersistentVector>();
        for(int i=0; i+inc<=boardString.length();i+=inc){
            String substring=boardString.substring(i,i+inc);
            boardVectors.add(stringToVector(substring));
        }
        return PersistentVector.create(boardVectors);
    }

    private static PersistentVector stringToVector(String rowString){
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
