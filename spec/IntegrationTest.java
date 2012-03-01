import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
    
    private Runnable startServer=new Runnable() {
        @Override
        public void run() {
            String [] args={"-p", Integer.toString(port), "-d", "/Users"};
            StartTTTServer.Main(args);            
        }
    };
    
    @Test
    public void buildsTTTResponse()
    throws Exception{
        new Thread(startServer).start();
        Thread.sleep(400);
        Socket socket=new Socket("localhost",port);
        String board="xoxxox   ";
        char player='o';
        String body=("board="+board+"&player="+player);
        String contentLength=Integer.toString(body.getBytes().length);
        byte [] requestBytes=("POST /ttt HTTP/1.1\nContent-Length: "+contentLength+"\n\n"+body).getBytes();

        socket.getOutputStream().write(requestBytes);

        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line="";
        Thread.sleep(2000);
        while(reader.ready()){
            line=reader.readLine();
            System.out.println(line);
        }

        int [] move=new TTTLibraryImpl().getMove(board,player);
        String moveString=Integer.toString(move[0])+Integer.toString(move[1]);
        assertTrue(line.indexOf(moveString)>0);

    }
    
    
}
