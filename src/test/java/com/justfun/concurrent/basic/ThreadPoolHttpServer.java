package com.justfun.concurrent.basic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolHttpServer {
	
	static ThreadPool<HttpRequestHandler> threadPool = new ThreadPoolDefault<>(1);
	static String basePath = "/Users/liupeng/Desktop";
	static ServerSocket serverSocket;
	static int port = 8080;
	
	public static void setBasePath(String basePath) {
		if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
			ThreadPoolHttpServer.basePath = basePath;
		}
	}
	
	public static void main(String[] args) throws IOException {
		start();
	}
	
	public static void start() throws IOException {
		serverSocket = new ServerSocket(port);
		Socket socket = null;
		while((socket = serverSocket.accept()) != null) {
			threadPool.execute(new HttpRequestHandler(socket));
		}
		serverSocket.close();
	}

	
	static class HttpRequestHandler implements Runnable {
		private Socket socket;
		public HttpRequestHandler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			BufferedReader reader = null;
			PrintWriter out = null;
			InputStream in = null;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String header = reader.readLine();
				String filePath = basePath + header.split(" ")[1];
				out = new PrintWriter(socket.getOutputStream());
				if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
					in = new FileInputStream(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i = 0;
					while ((i = in.read()) != -1) {
						baos.write(i);
					}
					byte[] array = baos.toByteArray();
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type: imae/jpeg");
					out.println("Content-Length: " + array.length);
					out.println("");
					socket.getOutputStream().write(array, 0, array.length);
				} else {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
					out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type: text/html; charset=UTF-8");
					out.println("");
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
				}
				out.flush();
			} catch (Exception ex) {
				out.println("HTTP/1.1 500");
				out.println("");
				out.flush();
			} finally {
				close(br, in, reader, out, socket);
			}
		}
		
		private static void close(Closeable ... closeables) {
			if (closeables != null) {
				for (Closeable c : closeables) {
					try {
						c.close();
					} catch (Exception ex) {}
				}
			}
		}
	}
}
