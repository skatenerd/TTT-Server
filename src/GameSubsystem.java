import java.io.IOException;

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
        int [] move= _tttLibrary.getMove(null,1);
        String postdata="move="+Integer.toString(move[0])+Integer.toString(move[1]);
        byte [] postBytes=postdata.getBytes();
        return new TextResponse(request, postBytes);
    }
    public boolean shouldHandle(Request request){
        boolean shouldHandle=false;
        if(request.get_path().equals("/ttt")){
            if(request.get_requestType().equalsIgnoreCase("POST")){
                shouldHandle = true;
            }
        }
        return shouldHandle;
    }

}
