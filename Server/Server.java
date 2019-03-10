package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this is the server class which hosts the server and the thread pool for games; when 2 clients(classified as the
 * GUI class in this case) have connected to the server socket, the server will create an instance of a game
 * and pass those two sockets to the game
 * @author Leon
 *
 */
public class Server {

	private ExecutorService pool;
	
	private PrintWriter out;
	private Socket aSocket;
	private ServerSocket serverSocket;
	
	/**
	 * this is the default constructor for the class Server and creates a new server socket at port 9898 as well as
	 * a fixed thread pool size of 2
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(2);
		} catch (IOException e) {
			System.out.println("Create new socket error");
			System.out.println(e.getMessage());
		}
		
		System.out.println("Server is running");
		
	}
	
	/**
	 * this method is what runs the server; it creates an empty arraylist and waits for two GUI classes to
	 * connect to the server socket that was created in this class. After it has accepted two GUI classes, it will 
	 * create a new instance of a game and pass those sockets along to the game before clearing the arraylist
	 * and repeating this process
	 */
	public void runServer() {
		ArrayList<Socket> list = new ArrayList<Socket>();
		try {
			while (true) {
					aSocket = serverSocket.accept();
					out = new PrintWriter((aSocket.getOutputStream()), true);
					if (list.size() == 0)
						out.println("X");
					else {
						out.println("O");
					}
					
				list.add(aSocket);
				
				if (list.size()== 2) {
					Game game = new Game(list.get(0), list.get(1));
					list.clear();
					pool.execute(game);
				}
			}
		}	catch (IOException e) {
				System.out.println(e.getMessage());
				// Stop accepting new games and finish any active ones, then shutdown the threadpool.
				pool.shutdown();
				try {
					out.close();
					aSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.runServer();
	}


}
