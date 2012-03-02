import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
import clojure.lang.Var;

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
    private TTTLibrary _tttLibrary;
    private static String _appRoot="/ttt/";
    private static String [] _paths={"cpumove","winner"};
    public GameSubsystem(TTTLibrary tttLibrary){
        _tttLibrary = tttLibrary;
    }
    
    private Response buildCpuResponse(Request request,Map<String,String> postParams){


        String player = getPlayer(postParams);
        Integer maxDepth = getMaxDepth(postParams);
        PersistentVector board = getBoardVector(postParams);
        if (postDataValid(board, player)) {
            int[] move = _tttLibrary.getMove(board, player.charAt(0), maxDepth);
            byte [] postBytes=new byte[0];
            if(move!=null){
                String responsePostdata = "move=" + Integer.toString(move[0]) + Integer.toString(move[1]);
                postBytes = responsePostdata.getBytes();
            }
            return new TextResponse(request, postBytes);
        } else {
            return new BadRequestResponse();
        }
    }   

    private Response buildWinnerResponse(Request request, Map<String,String> postParams){
        PersistentVector board = getBoardVector(postParams);
        if(boardValid(board)){
            return new TextResponse(request, _tttLibrary.winner(board).getBytes());
        }else{
            return new BadRequestResponse();
        }
    }
    
    public Response buildResponse(Request request) {
        String body = new String(request.get_Body());
        Map<String, String> parsed = PostDataParser.parse(body);


        Response response=null;
        if(request.get_path().equals(_appRoot+"cpumove")){
            response = buildCpuResponse(request, parsed);
        }else if(request.get_path().equals(_appRoot+"winner")){
            response = buildWinnerResponse(request, parsed);
        }
        return response;
    }

    private PersistentVector getBoardVector(Map<String,String>postParams){
        PersistentVector boardVector=null;
        String board=postParams.get("board");
        if(board!=null){
            boardVector = BoardStringParser.stringToBoardVector(board);
        }
        
        return boardVector;
    }   
    
    private String getPlayer(Map<String,String>postParams){
        String playerField=postParams.get("player");
        if(playerField!=null && playerField.length()==1){
            return playerField;
        }else{
            return null;
        }
    }

    private Integer getMaxDepth(Map<String,String>postParams){
        String depthField=postParams.get("maxdepth");
        if(depthField!=null){
            return Integer.parseInt(depthField);
        }else{
            return null;
        }
    }
    
    private boolean postDataValid(PersistentVector board,String  player){
        return playerValid(player) && boardValid(board);
    }    

    private boolean boardValid(PersistentVector board){
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
    
    private boolean playerValid(String player){
        boolean rtn=false;
        if(player!=null &&player.length() ==1){
            char firstChar=player.charAt(0);
            rtn = (firstChar=='x' || firstChar=='o');
        }
        return rtn;
    }
    
    private boolean pathValid(String candidatePath){
        for(String path:_paths){
            if(candidatePath.equals(_appRoot+path)){
                return true;
            }
        }
        return false;
    }
    
    public boolean shouldHandle(Request request){
        boolean shouldHandle=false;
        if(request.pathSupplied() && pathValid(request.get_path())){
            if(request.get_requestType().equalsIgnoreCase("POST")){
                shouldHandle = true;
            }
        }
        return shouldHandle;
    }
}