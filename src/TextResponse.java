/**
 * Created by IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/29/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextResponse extends Response{
    byte [] _body;
    public TextResponse(Request request, byte [] body){
        super(request);
        _body = body;
    }
    public String contentType(){
        return "text/plain";
    }
    public String status(){
        return "200 OK";
    }
    public byte [] getBody(){
        return _body;
    }
    
}
