import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private HttpServer httpServer = null;
    public String startServer(int port) {
        if (httpServer != null) {
            return "服务器已启动，请勿重复";
        }
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动");
            while (true) {
                Socket socket = serverSocket.accept();
                httpServer = new HttpServer(socket);
                httpServer.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "服务器已开启";
    }

    public String closeServer() {
        if (httpServer == null) {
            return "服务器还未开启";
        }
        httpServer.interrupt();
        return "服务器已关闭";
    }
}
