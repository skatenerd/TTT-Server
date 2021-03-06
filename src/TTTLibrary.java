import clojure.lang.*;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TTTLibrary {
    public int [] getMove(PersistentVector board, String player, Integer maxDepth);
    public String winner(PersistentVector board);
    public boolean playerValid(String player);
    public boolean boardValid(PersistentVector board);
}
