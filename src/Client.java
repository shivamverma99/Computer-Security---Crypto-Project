
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
    public Client(String address, int port) 
    { 
        //establish connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input = new DataInputStream(System.in); 
  
            // sends output to the socket 
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
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until user inputs Send 
        while (!line.equals("Send")) 
        { 
            try
            { 
                line = input.readLine(); 
                out.writeUTF(line); 
            } 
            catch(IOException exc) 
            { 
                System.out.println(exc); 
            } 
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
