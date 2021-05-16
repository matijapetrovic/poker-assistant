package ww10;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ww10.WW10Protocol.PrologBotDescription;

public class PrologBotServer implements Runnable {

	public final int port;

	private boolean running = true;

	private ServerSocket serverSocket;

	public PrologBotServer() {
		this(20000);
	}

	public PrologBotServer(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Could not listen on port: " + port);
			System.exit(-1);
		}
	}

	public void stop() throws IOException {
		running = false;
		serverSocket.close();
	}

	@Override
	public void run() {
		while (running) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				System.out.println("Incoming connection");
				InputStream inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream();
				read(inputStream);
				inputStream.close();
				outputStream.close();
				clientSocket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
		}

	}

	private void read(InputStream inputStream) {
		try {
			PrologBotDescription bot = PrologBotDescription.parseFrom(inputStream);
			onNewBot(bot);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Could not parse from inputstream.");
			System.exit(-1);
		}

	}

	protected void onNewBot(PrologBotDescription bot) {

	}

	public static void main(String[] args) {
		(new PrologBotServer() {
			@Override
			protected void onNewBot(PrologBotDescription bot) {
				System.out.println(bot.toString());
			}
		}).run();
	}

}
