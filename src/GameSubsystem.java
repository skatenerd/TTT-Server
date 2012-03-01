import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameSubsystem implements ResponseSubsystem{
    TTTLibrary _tttLibrary;
    public GameSubsystem(TTTLibrary tttLibrary){
        _tttLibrary = tttLibrary;
    }

    public Response buildResponse(Request request){
        String body=new String(request.get_Body());
        Map<String,String>parsed=PostDataParser.parse(body);                
        String board=parsed.get("board");
        char player=parsed.get("player").charAt(0);
        int [] move= _tttLibrary.getMove(board, player);
        String responsePostdata="move="+Integer.toString(move[0])+Integer.toString(move[1]);
        byte [] postBytes=responsePostdata.getBytes();
        return new TextResponse(request, postBytes);
    }

    public boolean shouldHandle(Request request){
        boolean shouldHandle=false;
        if(request.pathSupplied() && request.get_path().equals("/ttt")){
            if(request.get_requestType().equalsIgnoreCase("POST")){
                shouldHandle = true;
            }
        }
        return shouldHandle;
    }
}
