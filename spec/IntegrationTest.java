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
    int port=9001;
    
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
        String board="xoxo xo x";
        char player='x';

        socket.getOutputStream().write(("POST /ttt HTTP/1.1\n\nboard="+board+"&player="+player).getBytes());
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line="";
        Thread.sleep(50);
        while(reader.ready()){
            line=reader.readLine();
        }

        int [] move=new TTTLibraryImpl().getMove(board,player);
        String moveString=Integer.toString(move[0])+Integer.toString(move[1]);
        assertEquals("move="+moveString,line);
        
    }
    
    
}
