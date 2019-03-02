import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        String choice;
        int port = 8000;
        WebServer webServer = new WebServer();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入你的选项: ");
            System.out.println("1.开启服务器\n2.更改端口号\n3.退出程序");
            choice = scanner.nextLine();
            if(choice.equals("1")) {
                System.out.println(webServer.startServer(port));
            } else if (choice.equals("2")) {
                System.out.println("请输入你要修改成的端口号");
                String temp = scanner.nextLine();
                if (temp.matches("\\d+")) {
                    port = Integer.parseInt(temp);
                    System.out.println("修改成功");
                } else {
                    System.out.println("请正确输入端口号");
                }
            } else if (choice.equals("3")) {
                return;
            } else {
                System.out.println("请正确输入选项");
            }
        }
    }
}
