import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntegrationTest {
    int port=8080;
    Server _server;

    @Before
    public void startServer()
    throws Exception{
        List<ResponseSubsystem>subsystems=new ArrayList<ResponseSubsystem>();
        subsystems.add(new GameSubsystem(new TTTLibraryImpl()));
        _server = new Server("/",8080,subsystems);
        _server.start();
        Thread.sleep(400);
    }
    
    @After
    public void killServer(){
        _server.kill();
    }
    
    @Test
    public void buildsTTTMoveResponse()
    throws Exception{
        Socket socket=new Socket("localhost",port);
        String board="oo       ";
        String player="o";
        String maxdepth="9";
        String body=("board="+board+"&player="+player+"&maxdepth="+maxdepth);
        String contentLength=Integer.toString(body.getBytes().length);
        byte [] requestBytes=("POST /ttt/cpumove HTTP/1.1\nContent-Length: "+contentLength+"\n\n"+body).getBytes();

        socket.getOutputStream().write(requestBytes);

        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line="";
        Thread.sleep(2000);
        while(reader.ready()){
            line=reader.readLine();
        }

        int [] move=new TTTLibraryImpl().getMove(BoardStringParser.stringToBoardVector(board),player,Integer.parseInt(maxdepth));
        String moveString=Integer.toString(move[0])+Integer.toString(move[1]);
        assertTrue(line.indexOf(moveString)>0);
    }

    @Test
    public void buildsWinnerResponse()
    throws Exception{
        Socket socket=new Socket("localhost",port);
        String board="xxxoo o  ";
        String body=("board="+board);
        String contentLength=Integer.toString(body.getBytes().length);
        byte [] requestBytes=("POST /ttt/winner HTTP/1.1\nContent-Length: "+contentLength+"\n\n"+body).getBytes();

        socket.getOutputStream().write(requestBytes);

        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line="";
        Thread.sleep(2000);
        while(reader.ready()){
            line=reader.readLine();
        }

        assertTrue(line.indexOf("x")>=0);
    }
    
}
