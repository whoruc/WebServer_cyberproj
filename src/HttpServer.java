import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpServer extends Thread {
    public static final String ROOT = "D:/";

    /**
     * 输入流和输出流
     */
    private InputStream input;
    private OutputStream output;
    private Socket socket;

    public HttpServer(Socket socket) {
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            this.socket = socket;
        } catch (IOException e) {

        }
    }

    /**
     * run方法
     */
    @Override
    public void run() {
        String filepath = read();
        response(filepath);
    }

    /**
     * 响应浏览器请求
     */
    private void response(String filepath) {
        File file = new File(ROOT + filepath);
        String filetype = (filepath.split("\\."))[1];
        if (file.exists()) {
            try {
                StringBuffer sb = new StringBuffer();
                FileInputStream input = new FileInputStream(file);
                int readLine;
                String temp = "";
                byte[] buffer = new byte[1024];
                sb.append(temp).append("\r\n");
                StringBuffer result = new StringBuffer();
                result.append("HTTP /1.1 200 ok\r\n");
                if (filetype.equals("html")) {
                    result.append("Content-Type:text/html; charset=UTF-8\r\n");
                } else {
                    result.append("Content-Type:image/jpg; charset=UTF-8\r\n");
                }
                result.append("Content-Length:" + file.length() + "\r\n");
                result.append("\r\n");
                output.write(result.toString().getBytes());
                while ((readLine = input.read(buffer)) > 0) {
                    output.write(buffer, 0, readLine);
                }
                output.flush();
                output.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            StringBuffer error = new StringBuffer();
            error.append("HTTP/1.1 404 File Not Found\r\n");
            error.append("Content-Type: text/html\r\n");
            error.append("Content-Length: 23\r\n").append("\r\n");
            error.append("<h1>File Not Found..</h1>");
            try {
                output.write(error.toString().getBytes());
                output.flush();
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件路径
     */
    private String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            //读取请求头
            String readLine = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(readLine + "\ntime：" + sdf.format(new Date()));
            if (readLine == null) {
                return null;
            }
            String[] split = readLine.split(" ");
            if (split.length != 3) {
                return null;
            }
            return split[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
