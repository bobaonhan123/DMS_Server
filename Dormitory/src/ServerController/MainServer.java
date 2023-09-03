package ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Configurations.Host;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MainServer {

    private ServerSocket server;
    private final int port = Host.port;

    private JFrame frame;
    private JTextArea logTextArea;

    File file = new File("serverlog");

    FileWriter fileWriter;
    public MainServer() {
        try {
            fileWriter = new FileWriter(file, true);
            if (!file.exists()) {
                // If file does not exist, create a new file
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Server Log");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            logTextArea = new JTextArea(50, 80);
            logTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(logTextArea);

            JPanel panel = new JPanel();
            panel.add(scrollPane);

            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);
        });


        try {
            server = new ServerSocket(port);
            appendToLog("Server is started at port "+Host.port);
            fileWriter.write("Server is started at port "+Host.port);
            fileWriter.write("\r\n");
            while (true) {
                run();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            Socket socket = server.accept();
            appendToLog("Server accept");
            Thread T = new Thread(() -> {

                try {
                    BufferedReader inFromClient =
                            new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    OutputStreamWriter outToClient =
                            new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

                    String sentence_from_client = inFromClient.readLine();
                    appendToLog("Request message:"+sentence_from_client);

                    fileWriter.write("Request message:"+sentence_from_client);
                    fileWriter.write("\r\n");
                    String sentence_to_client = new MappingMethod().Mapping(sentence_from_client);

                    outToClient.write(sentence_to_client);
                    outToClient.flush();
                    socket.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            T.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendToLog(String message) {
        SwingUtilities.invokeLater(() -> logTextArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        new MainServer();
    }
}