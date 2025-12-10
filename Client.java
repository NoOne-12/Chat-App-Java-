import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.util.*;

public class Client extends JFrame {

    private JTextPane chatPane;           
    private StyledDocument doc;           
    private JTextField inputField;
    private JButton sendBtn;
    private JList<String> userList;
    private DefaultListModel<String> userModel;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String userName;

    public Client() {
        buildUI();
    }

    private void buildUI() {
        setTitle("Friendly Chat");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(12, 12, 12, 12));
        add(main);

        // CHAT PANE (supports alignment)
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setFont(new Font("SansSerif", Font.PLAIN, 14));
        doc = chatPane.getStyledDocument();

        JScrollPane chatScroll = new JScrollPane(chatPane);
        chatScroll.setBorder(BorderFactory.createTitledBorder("Chat"));
        main.add(chatScroll, BorderLayout.CENTER);

        // USER LIST
        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(150, 0));
        userScroll.setBorder(BorderFactory.createTitledBorder("Users"));
        main.add(userScroll, BorderLayout.EAST);

        // INPUT + SEND
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        inputField = new JTextField();
        inputField.addActionListener(e -> send());
        bottom.add(inputField, BorderLayout.CENTER);

        sendBtn = new JButton("Send");
        sendBtn.addActionListener(e -> send());
        bottom.add(sendBtn, BorderLayout.EAST);

        main.add(bottom, BorderLayout.SOUTH);
    }

    private void connect(String host, int port, String user) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);

            userName = user;
            out.println(userName);

            new Thread(this::readerLoop).start();

            appendRight("[Connected as " + userName + "]");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + e.getMessage());
        }
    }

    private void readerLoop() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.startsWith("/users ")) {
                    updateUsers(msg.substring(7));
                } else {
                    if (msg.startsWith(userName + ":"))
                        appendRight(msg);
                    else
                        appendLeft(msg);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void updateUsers(String csv) {
        SwingUtilities.invokeLater(() -> {
            userModel.clear();
            for (String u : csv.split(",")) userModel.addElement(u);
        });
    }

    // INSERT LEFT aligned
    private void appendLeft(String text) {
        appendStyled(text, StyleConstants.ALIGN_LEFT);
    }

    // INSERT RIGHT aligned
    private void appendRight(String text) {
        appendStyled(text, StyleConstants.ALIGN_RIGHT);
    }

    // Core alignment logic
    private void appendStyled(String text, int alignment) {SwingUtilities.invokeLater(() -> {
            try {
                SimpleAttributeSet set = new SimpleAttributeSet();
                StyleConstants.setAlignment(set, alignment);
                doc.setParagraphAttributes(doc.getLength(), 1, set, false);
                doc.insertString(doc.getLength(), text + "\n", set);
            } catch (Exception e) {}
        });
    }

    private void send() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;
        out.println(msg);
        inputField.setText("");
    }

    private boolean connectDialog() {
        JTextField hostField = new JTextField("127.0.0.1");
        JTextField portField = new JTextField("4800");
        JTextField nameField = new JTextField("User" + new Random().nextInt(999));

        JComponent[] inputs = {
                new JLabel("Server Host:"), hostField,
                new JLabel("Port:"), portField,
                new JLabel("Your Name:"), nameField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Connect", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String host = hostField.getText().trim();
            int port = Integer.parseInt(portField.getText().trim());
            String name = nameField.getText().trim();
            connect(host, port, name);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client c = new Client();
            c.setVisible(true);
            c.connectDialog();
        });
    }
}