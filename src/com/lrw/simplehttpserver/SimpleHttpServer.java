package com.lrw.simplehttpserver;

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

import com.lrw.threadpool.ThreadPool;
import com.lrw.threadpool.impl.DefaultThreadPool;

public class SimpleHttpServer {
  //处理httpRequest的线程池
  static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);
  static String basePath;
  static ServerSocket serverSocket;
  static int port =8080;
  public static void setPort(int port) {
	  if(port>0) {
		  SimpleHttpServer.port = port;
	  }
	}

  public static void setBasePath(String basePath) {
	  if(basePath!=null&& new File(basePath).exists()&& new File(basePath).isDirectory()) {
		  SimpleHttpServer.basePath = basePath;
	  }
}

  public static void start() throws Exception {
	  serverSocket = new ServerSocket(port);
	  Socket socket= null;
	  while ((socket = serverSocket.accept())!=null) {
		  //结束一个客户端Socket，生成一个HttpRequestHadnler，放入线程池执行
		  threadPool.execute(new HttpRequestHandler(socket));
	  }
	  serverSocket.close();
  }



static class HttpRequestHandler implements Runnable{
    
	private Socket socket ;
	public HttpRequestHandler(Socket socket) {
		this.socket=socket;
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
			//由相对路径计算出绝对路径
			String filePath = basePath+header.split(" ")[1];
			out = new PrintWriter(socket.getOutputStream());
			//如果请求资源的后缀为jpg或者ico，则读取资源并输出
			if(filePath.endsWith("jpg")||filePath.endsWith("ico")) {
				in = new FileInputStream(filePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i=0;
				while((i=in.read())!=-1) {
					baos.write(i);
				}
				byte[] array = baos.toByteArray();
				out.println("Http/1.1 200 ok");
				out.println("Server: Molly");
				socket.getOutputStream().write(array, 0, array.length);
			}else {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				out = new PrintWriter(socket.getOutputStream());
				out.println("Http/1.1 200 ok");
				out.println("Server: Molly");
				while((line=br.readLine())!=null) {
					out.println(line);
				}
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("Http/1.1 500");
			out.println("");
			out.flush();
		}finally {
			close(br,in,reader,out,socket);
		}
	}
  }
  private static void close(Closeable...closeables) {
	  if(closeables!=null) {
		  for (Closeable closeable : closeables) {
			  try {
				closeable.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  }
  }
}
