import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class StartTTTServer {
    public static void main(String [] args)
    {
        ResponseSubsystem gameSubsystem=new GameSubsystem(new TTTLibraryImpl());
        List<ResponseSubsystem> subsystemList=new ArrayList<ResponseSubsystem>();
        subsystemList.add(gameSubsystem);
        StartServer starter=new StartServer(subsystemList);
        try{
            starter.parseArgsAndStartServer(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
