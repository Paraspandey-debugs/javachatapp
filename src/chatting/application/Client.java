package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    JScrollPane scrollPane;

    Client() {
        f.setUndecorated(true);
        f.setShape(new RoundRectangle2D.Double(0, 0, 450, 700, 30, 30));
        f.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setLayout(new BorderLayout());
        p1.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftHeader.setOpaque(false);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/close.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        leftHeader.add(back);

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/client.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        leftHeader.add(profile);
        p1.add(leftHeader, BorderLayout.WEST);

        JPanel centerHeader = new JPanel();
        centerHeader.setLayout(new BoxLayout(centerHeader, BoxLayout.Y_AXIS));
        centerHeader.setOpaque(false);

        JLabel name = new JLabel("Client User");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma" , Font.BOLD , 18 ));
        centerHeader.add(name);

        JLabel status = new JLabel("I'm using Baat.");
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Tahoma" , Font.PLAIN , 14 ));
        centerHeader.add(status);

        p1.add(centerHeader, BorderLayout.CENTER);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightHeader.setOpaque(false);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        rightHeader.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        rightHeader.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/other.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        rightHeader.add(morevert);

        p1.add(rightHeader, BorderLayout.EAST);

        f.add(p1, BorderLayout.NORTH);

        a1 = new JPanel();
        a1.setBackground(Color.WHITE);
        a1.setBorder(new EmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(a1);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        f.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        text = new JTextField();
        text.setFont(new Font("Tahoma" , Font.PLAIN , 16 ));
        text.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        bottomPanel.add(text, BorderLayout.CENTER);

        JButton send = new JButton("Send");
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Tahoma" , Font.BOLD , 16 ));
        bottomPanel.add(send, BorderLayout.EAST);

        f.add(bottomPanel, BorderLayout.SOUTH);

        f.setSize(450,700);
        f.setLocation(800,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try{
            String out = text.getText();
            if (out.isEmpty()) return;

            JPanel p2 = formatLabel(out, "sender");

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(Color.WHITE);
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();

            autoScroll();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out, String senderType) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final Color bubbleColor = ("sender".equals(senderType)) ? new Color(225, 253, 219) : Color.WHITE;
        final Color textColor = Color.BLACK;

        JPanel bubblePanel = new JPanel(new BorderLayout()) {
            private int cornerRadius = 24;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(cornerRadius, cornerRadius);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics.setColor(bubbleColor);
                graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);

                if (!"sender".equals(senderType)) {
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
                }
            }
        };
        bubblePanel.setOpaque(false);

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setForeground(textColor);
        output.setOpaque(false);
        output.setBorder(new EmptyBorder(10, 15, 10, 15));

        bubblePanel.add(output, BorderLayout.CENTER);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        time.setForeground(Color.GRAY);
        time.setFont(new Font("Tahoma", Font.PLAIN, 10));

        float alignment = ("sender".equals(senderType)) ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT;
        bubblePanel.setAlignmentX(alignment);
        time.setAlignmentX(alignment);

        mainPanel.add(bubblePanel);
        mainPanel.add(time);

        return mainPanel;
    }

    private void autoScroll() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            System.setProperty("awt.useSystemAAFontSettings","on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Client client = new Client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg, "receiver");

                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.WHITE);
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
                client.autoScroll();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

