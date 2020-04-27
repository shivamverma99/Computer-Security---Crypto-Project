
import java.net.*; 
import java.io.*; 
  
public class Client 
{  
    private Socket socket = null; 
    private DataInputStream input = null; 
    private DataOutputStream out = null; 
  
    /**
     * Constructor takes address / port
     * @param address
     * @param port
     */
    public Client(String address, int port, String data) 
    { 
        //establish connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // data is the input from the terminal 
            // out sends output to the socket 
            out = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException UHexc) 
        { 
            System.out.println(UHexc); 
        } 
        catch(IOException IOexc) 
        { 
            System.out.println(IOexc); 
        } 
  
        // send message to the server 

        try
        { 
            out.writeUTF(data); 
        } 
        catch(IOException exc) 
        { 
            System.out.println(exc); 
        } 
         
  
        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
     
    }
    
} 
