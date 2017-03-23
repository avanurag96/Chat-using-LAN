
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
@SuppressWarnings("serial")
public class ClientChatForm extends JFrame implements ActionListener {
    static Socket conn;
    JPanel panel;
    JTextField NewMsg;
    JTextArea ChatHistory;
    JButton Send;
    JScrollPane scroll ;
 
    public ClientChatForm() throws UnknownHostException, IOException {
        panel = new JPanel();
        NewMsg = new JTextField();
        ChatHistory = new JTextArea();
        ChatHistory.setEditable(false);
        scroll = new JScrollPane(ChatHistory,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ChatHistory.setLineWrap(true);
        ChatHistory.setWrapStyleWord(true);
//        scroll = new JScrollPane(ChatHistory);
// 
//        this.add(scroll);

        Send = new JButton("Send");
        this.setSize(500, 500);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(null);
        this.add(panel);
        ChatHistory.setBounds(20, 20, 450, 360);
        panel.add(ChatHistory);
        NewMsg.setBounds(20, 400, 340, 30);
        panel.add(scroll);
        panel.add(NewMsg);
        Send.setBounds(375, 400, 95, 30);
        panel.add(Send);
        panel.getRootPane().setDefaultButton(Send);
        Send.addActionListener(this);
        conn = new Socket(InetAddress.getByName("GHOST"), 4444);
 
        /*
         * for remote pc use ip address of server with function
         * InetAddress.getByName("Provide ip here")
         * ChatHistory.setText("Connected to Server");
         */
 
        ChatHistory.setText("EasyTrip executive is online");
        this.setTitle("ET User");
        while (true) {
            try {
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                ChatHistory.setText(ChatHistory.getText() + '\n' + "ET Exec:"
                        + string);
            } catch (Exception e1) {
                ChatHistory.setText(ChatHistory.getText() + '\n'
                        + "Message sending fail:Network Error");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if ((e.getSource() == Send) && (NewMsg.getText() != "")) {
 
            ChatHistory.setText(ChatHistory.getText() + '\n' + "Me:"
                    + NewMsg.getText());
            try {
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                dos.writeUTF(NewMsg.getText());
            } catch (Exception e1) {
                ChatHistory.setText(ChatHistory.getText() + '\n'
                        + "Message sending fail:Network Error");
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
        ClientChatForm chatForm = new ClientChatForm();
    }
}
