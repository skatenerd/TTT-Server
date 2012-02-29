import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameSubsystemTest {
    String board="ooox oo x";
    char player='x';
    String postdata="board="+board+"&player="+player;
    @Test
    public void acceptPostWithTTTPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);
        Request request=new MockRequest("POST","/ttt","".getBytes(),false,true);
        assertTrue(gameSubsystem.shouldHandle(request));
    }

    @Test
    public void rejectGetWithTTTPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);
        Request request=new MockRequest("GET","/ttt","".getBytes(),false,true);
        assertFalse(gameSubsystem.shouldHandle(request));
    }
    @Test
    public void rejectPostWithRandomPath(){
        ResponseSubsystem gameSubsystem=new GameSubsystem(null);
        Request request=new MockRequest("POST","/fizz","".getBytes(),false,true);
        assertFalse(gameSubsystem.shouldHandle(request));
    }

    @Test
    public void sendGameRequest()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary();
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        Request request=new MockRequest("POST","ttt",postdata.getBytes(),false,true);
        gameSubsystem.buildResponse(request);
        assertEquals("getMove", mockLibrary._calls.get(0));
        assertEquals(board,mockLibrary._boardArgs.get(0));
        assertEquals((char)player,(char)mockLibrary._playerArgs.get(0));
        
    }
    @Test
    public void buildPostResponse()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary();
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        int[] expectedMove=mockLibrary.getMove(board,player);
        String expectedMoveString=Integer.toString(expectedMove[0])+Integer.toString(expectedMove[1]);
        
        Request request=new MockRequest("POST","/ttt",postdata.getBytes(),false,true);
        Response response = gameSubsystem.buildResponse(request);
        String body=new String(response.getBody());        
        
        assertEquals("move="+expectedMoveString,body);
    }

}
