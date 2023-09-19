package org.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serial;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ChatPanel extends JPanel implements Runnable {

    @Serial
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JButton btnNewButton;
    private JLabel lblNewLabel;
    private JLabel lblHistory;
    private JTextArea textArea_1;
    // ______________________________
    Socket socket = null;
    String sender;
    String receiver;
    BufferedReader bf = null;
    DataOutputStream os = null;

    @Override
    public void run() {
        while (true) {
            try {
                if (socket != null) {
                    String msg = "";
                    while ((msg = bf.readLine()) != null) {
                        textArea_1.append(msg + "\n");
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public ChatPanel(Socket s, String sender, String receiver) {
        initComponents();
        socket = s;
        this.sender = sender;
        this.receiver = receiver;

        try {

            GroupLayout groupLayout = new GroupLayout(this);
            groupLayout.setHorizontalGroup(
                    groupLayout.createParallelGroup(Alignment.LEADING)
                            .addGroup(groupLayout.createSequentialGroup().addGap(300)
                                    .addComponent(getLblHistory(), GroupLayout.DEFAULT_SIZE, 67,
                                            Short.MAX_VALUE)
                                    .addGap(300))
                            .addGroup(groupLayout
                                    .createSequentialGroup().addGap(12)
                                    .addComponent(getTextArea_1(), GroupLayout.DEFAULT_SIZE, 200,
                                            480)
                                    .addGap(12))
                            .addGroup(groupLayout.createSequentialGroup().addGap(12).addGroup(groupLayout
                                            .createParallelGroup(Alignment.LEADING)
                                            .addGroup(groupLayout.createSequentialGroup().addGap(168)
                                                    .addComponent(getLblNewLabel(),
                                                           0, 0,
                                                            0)
                                                    .addGap(147))
                                            .addComponent(getTextArea(), 300, 300,
                                                    300))
                                    .addGap(12).addComponent(getBtnNewButton(), 100,
                                            100, 100)
                                    .addGap(12)));
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                    .createSequentialGroup().addComponent(getLblHistory(), GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addGap(6).addComponent(getTextArea_1(), GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE).addGap(1)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(getLblNewLabel(), GroupLayout.PREFERRED_SIZE, 27,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(getTextArea(), 100, 150,
                                            150))
                            .addGroup(groupLayout.createSequentialGroup().addGap(26).addComponent(
                                    getBtnNewButton(), 50, 50,50)))
                    .addGap(13)));

            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new DataOutputStream(socket.getOutputStream());

            setLayout(groupLayout);
            (new Thread(this)).start();
        } catch (Exception e) {
            System.out.println("Error while create Main Panel");
        }

    }

    private void initComponents() {
    }

    public JTextArea getTextArea() {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        }

        return textArea;
    }

    public JButton getBtnNewButton() {

        if (btnNewButton == null) {

            btnNewButton = new JButton("SEND");
            btnNewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (textArea.getText().isEmpty()) return;
                    try {
                        os.writeBytes(sender + ": " + textArea.getText() + "\n");
                        os.flush();
                        textArea_1.append(sender + ": " + textArea.getText() + "\n");
                        textArea.setText("");
                    } catch (Exception e) {
                        System.out.println("Error while sendding messeger");
                    }
                }
            });
            btnNewButton.setBackground(Color.CYAN);
            btnNewButton.setForeground(Color.RED);
            btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        }

        return btnNewButton;
    }

    public JLabel getLblNewLabel() {
        if (lblNewLabel == null) {
            lblNewLabel = new JLabel("Type the mess here to send");
            lblNewLabel.setForeground(Color.GRAY);
            lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 18));
        }


        return lblNewLabel;
    }

    public JLabel getLblHistory() {
        if (lblHistory == null) {
            lblHistory = new JLabel();
            lblHistory.setForeground(Color.GRAY);
            lblHistory.setFont(new Font("Tahoma", Font.ITALIC, 13));
        }
        return lblHistory;
    }

    public JTextArea getTextArea_1() {
        if (textArea_1 == null) {
            textArea_1 = new JTextArea();
            textArea_1.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea_1.setBounds(20, 300, 300, 150); // Điều chỉnh kích thước ở đây (width=300, height=150)
        }
        return textArea_1;
    }

}