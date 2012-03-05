import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameSubsystemTest {
    String oWinBoard ="ooox oo x";
    String toMoveBoard = "oo       ";
    String player="x";
    String movePostdata ="board="+ toMoveBoard +"&player="+player+"&maxdepth=5";
    String winnerPostdata ="board="+ oWinBoard;
    int [] defaultMove={1,1};
    @Test
    public void acceptPostWithTTTPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);

        Request cpumoveRequest=new MockRequest("POST","/ttt/cpumove","".getBytes(),false,true);
        assertTrue(gameSubsystem.shouldHandle(cpumoveRequest));

        Request winnerRequest=new MockRequest("POST","/ttt/winner","".getBytes(),false,true);
        assertTrue(gameSubsystem.shouldHandle(winnerRequest));
    }

    @Test
    public void rejectGetWithTTTPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);
        Request request=new MockRequest("GET","/ttt/cpumove","".getBytes(),false,true);
        assertFalse(gameSubsystem.shouldHandle(request));
    }
    @Test
    public void rejectPostWithRandomPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);
        Request request=new MockRequest("POST","/fizz","".getBytes(),false,true);
        assertFalse(gameSubsystem.shouldHandle(request));
    }

    @Test
    public void sendMoveRequest()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary(defaultMove,null,true);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        Request request=new MockRequest("POST","/ttt/cpumove", movePostdata.getBytes(),false,true);
        gameSubsystem.buildResponse(request);
        assertEquals("getMove", mockLibrary._calls.get(0));
        assertEquals(BoardStringParser.stringToBoardVector(toMoveBoard),mockLibrary._boardArgs.get(0));
        assertEquals(player,mockLibrary._playerArgs.get(0));
        assertEquals(5,(int)mockLibrary._depthArgs.get(0));
    }

    @Test
    public void sendWinnerRequest()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary(null,"x",true);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        Request request=new MockRequest("POST","/ttt/winner", winnerPostdata.getBytes(),false,true);
        gameSubsystem.buildResponse(request);
        assertEquals("winner", mockLibrary._calls.get(0));
        assertEquals(BoardStringParser.stringToBoardVector(oWinBoard),mockLibrary._boardArgs.get(0));
    }
    

    @Test
    public void buildPostCPUMoveResponse()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary(defaultMove,null,true);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        int[] expectedMove=mockLibrary.getMove(null, player, null);
        String expectedMoveString=Integer.toString(expectedMove[0])+Integer.toString(expectedMove[1]);
        
        Request request=new MockRequest("POST","/ttt/cpumove", movePostdata.getBytes(),false,true);
        Response response = gameSubsystem.buildResponse(request);
        String body=new String(response.getBody());        
        
        assertEquals("move="+expectedMoveString,body);
    }

    @Test
    public void buildPostWinnerResponse()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary(null,"o",true);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);

        Request request=new MockRequest("POST","/ttt/winner", winnerPostdata.getBytes(),false,true);
        Response response = gameSubsystem.buildResponse(request);
        String body=new String(response.getBody());

        assertEquals("o",body);
    }

    @Test
    public void emptyResponseBodyForNullMove()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary(null,null,true);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);        
        Request request=new MockRequest("POST","/ttt/cpumove", movePostdata.getBytes(),false,true);
        Response response = gameSubsystem.buildResponse(request);
        assertEquals(0,response.getBody().length);
    }

    @Test
    public void sendsBadRequestOnGarbageInput()
    throws IOException{
        String tooLongBoard="oooooooooooooo";
        String illegalCharacters="hellooooo";
        
        byte [] tooLongBoardPostdata=("board="+tooLongBoard+"&player=x").getBytes();
        byte [] illegalCharactersPostdata=("board="+illegalCharacters+"&player=x").getBytes();


        MockTTTLibrary mockLibrary=new MockTTTLibrary(defaultMove,null,false);
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);

        Request tooLongWinnerRequest=new MockRequest("POST","/ttt/winner",tooLongBoardPostdata,false,true);
        Response tooLongWinnerResponse=gameSubsystem.buildResponse(tooLongWinnerRequest);
        assertEquals(BadRequestResponse.class,tooLongWinnerResponse.getClass());
        
        Request tooLongMoveRequest=new MockRequest("POST","/ttt/cpumove",tooLongBoardPostdata,false,true);
        Response tooLongResponse = gameSubsystem.buildResponse(tooLongMoveRequest);
        assertEquals(BadRequestResponse.class, tooLongResponse.getClass());
        
        Request illegalCharactersRequest=new MockRequest("POST","/ttt/cpumove",illegalCharactersPostdata,false,true);
        Response illegalCharactersResponse=gameSubsystem.buildResponse(illegalCharactersRequest);
        assertEquals(BadRequestResponse.class,illegalCharactersResponse.getClass());

        Request notPostdata=new MockRequest("POST","/ttt/cpumove","fizz".getBytes(),false,true);
        Response notPostdataResponse=gameSubsystem.buildResponse(notPostdata);
        assertEquals(BadRequestResponse.class,notPostdataResponse.getClass());


    }


}
