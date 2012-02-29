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
        String postdata="board=ooox oo x&player=x";
        Request request=new MockRequest("POST","ttt",postdata.getBytes(),false,true);
        gameSubsystem.buildResponse(request);
        assertEquals("getMove", mockLibrary._calls.get(0));
    }
    @Test
    public void buildPostResponse()
    throws IOException{
        MockTTTLibrary mockLibrary=new MockTTTLibrary();
        ResponseSubsystem gameSubsystem=new GameSubsystem(mockLibrary);
        String postdata="board=ooox oo x&player=x";
        Request request=new MockRequest("POST","ttt",postdata.getBytes(),false,true);
        Response response = gameSubsystem.buildResponse(request);
        String body=new String(response.getBody());
        String move="move=11";
        assertEquals(1,mockLibrary._defaultMove[0]);
        assertEquals(1,mockLibrary._defaultMove[1]);
        assertEquals(move,body);
    }

}
