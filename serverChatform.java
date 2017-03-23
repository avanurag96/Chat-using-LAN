

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class serverChatform extends JFrame implements ActionListener {
    static ServerSocket server;
    static Socket conn;
    JPanel panel;
    JTextField NewMsg;
    JTextArea ChatHistory;
    JButton Send;
    DataInputStream dis;
    DataOutputStream dos;
    JScrollPane scroll ;
 
    public serverChatform() throws UnknownHostException, IOException {
 
        panel = new JPanel();
        NewMsg = new JTextField();
        ChatHistory = new JTextArea();
        ChatHistory.setEditable(false);
        scroll = new JScrollPane(ChatHistory,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scroll1 = new JScrollPane(ChatHistory);
//        
//        this.add(scroll1);
        Send = new JButton("Send");
       
        this.setSize(500, 500);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(null);
        this.add(panel);
        ChatHistory.setLineWrap(true);
        ChatHistory.setWrapStyleWord(true);
        ChatHistory.setBounds(20, 20, 450, 360);
        panel.add(ChatHistory);
        NewMsg.setBounds(20, 400, 340, 30);
        panel.add(scroll);
        panel.add(scroll);
        panel.add(NewMsg);
        Send.setBounds(375, 400, 95, 30);
        panel.add(Send);
        panel.getRootPane().setDefaultButton(Send);
        this.setTitle("EasyTrip Customer Service");
        Send.addActionListener(this);
       ServerSocket server = new ServerSocket(4444);
        ChatHistory.setText("Waiting for EasyTrip User:");
        conn = server.accept();
        ChatHistory.setText(ChatHistory.getText() + '\n' + "User Found");
        while (true) {
            try {
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                ChatHistory.setText(ChatHistory.getText() + '\n' + "User:"
                        + string);
            } catch (Exception e1) {
                ChatHistory.setText(ChatHistory.getText() + '\n'
                        + "Message Delivery failed: Network Error");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally {
                    server.close();
                }
            }
        }
     
    }
    
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if ((e.getSource() == Send) && (NewMsg.getText() != "")) {
            ChatHistory.setText(ChatHistory.getText() + '\n' + "ME:"
                    + NewMsg.getText());
            try {
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                dos.writeUTF(NewMsg.getText());
            } catch (Exception e1) {
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
            NewMsg.setText("");
        }
    }
 
    public static void main(String[] args) throws UnknownHostException,
    IOException {
        new serverChatform();
    }
}
