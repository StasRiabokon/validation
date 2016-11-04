/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yasta
 */
public class Validate extends JFrame {

    public static final int HEIGHT = 240;
    public static final int WIDTH = 350;

    public static final int LOCALE_X = 400;
    public static final int LOCALE_Y = 250;

    private JTextField domain;
    private JTextField ipAddress;
    private JTextField username;
    private JPasswordField password;

    private JPanel panel;

    private String emptyField;

    public Validate() {
        super("Validate Form");

        Box boxDomain = Box.createHorizontalBox();
        JLabel domainLabel = new JLabel("Domain:");
        domain = new JTextField(15);
        boxDomain.add(domainLabel);
        boxDomain.add(Box.createHorizontalStrut(6));
        boxDomain.add(domain);

        Box boxIpAddress = Box.createHorizontalBox();
        JLabel ipAddressLabel = new JLabel("Ip Address:");
        ipAddress = new JTextField(15);
        boxIpAddress.add(ipAddressLabel);
        boxIpAddress.add(Box.createHorizontalStrut(6));
        boxIpAddress.add(ipAddress);

        Box boxUserName = Box.createHorizontalBox();
        JLabel usernameLabel = new JLabel("Username:");
        username = new JTextField(15);
        boxUserName.add(usernameLabel);
        boxUserName.add(Box.createHorizontalStrut(6));
        boxUserName.add(username);

        Box boxPass = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password:");
        password = new JPasswordField(15);
        boxPass.add(passwordLabel);
        boxPass.add(Box.createHorizontalStrut(6));
        boxPass.add(password);

        Box boxButtons = Box.createHorizontalBox();
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkOfEmptyFields()) {
                    JOptionPane.showMessageDialog(null, "Please, fill in this field: " + emptyField, "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!checkOfDomain()) {
                    JOptionPane.showMessageDialog(null, "Not a valid domain.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    domain.setText("");
                    domain.requestFocus();
                    return;
                }
                if (!checkOfIP()) {
                    JOptionPane.showMessageDialog(null, "Not a valid IP.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    ipAddress.setText("");
                    ipAddress.requestFocus();
                    return;
                }
                if (!checkOfEmailAddress()) {
                    JOptionPane.showMessageDialog(null, "Not a valid email.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    username.setText("");
                    username.requestFocus();
                    return;
                }

                if (!checkOfPassword()) {
                    JOptionPane.showMessageDialog(null, "Not a valid password.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    password.setText("");
                    password.requestFocus();
                    return;
                }
                JOptionPane.showMessageDialog(null, "You entered the system", "Welcome!", JOptionPane.PLAIN_MESSAGE);

            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        boxButtons.add(Box.createHorizontalGlue());
        boxButtons.add(ok);
        boxButtons.add(Box.createHorizontalStrut(12));
        boxButtons.add(cancel);

        domainLabel.setPreferredSize(ipAddressLabel.getPreferredSize());
        usernameLabel.setPreferredSize(ipAddressLabel.getPreferredSize());
        passwordLabel.setPreferredSize(ipAddressLabel.getPreferredSize());

        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(20, 12, 12, 12));
        mainBox.add(boxDomain);

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(boxIpAddress);

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(boxUserName);

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(boxPass);

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(boxButtons);

        setContentPane(mainBox);

    }

    private boolean checkOfEmptyFields() {
        if (domain.getText() == null || domain.getText().equals("")) {
            emptyField = "Domain";
            return false;
        }
        if (ipAddress.getText() == null || ipAddress.getText().equals("")) {
            emptyField = "IP Address";
            return false;
        }
        if (username.getText() == null || username.getText().equals("")) {
            emptyField = "Username";
            return false;
        }
        if (new String(password.getPassword()).equals("")) {
            emptyField = "Password";
            return false;
        }
        return true;

    }

    private boolean checkOfEmailAddress() { // username@somename.com
        Pattern pattern = Pattern.compile("[a-zA-Z]{1}[\\w\\.]+"
                + "@[a-zA-Z]{1}([a-zA-Z0-9]+\\u002E){1}((edu)|(com)|(org))");
        String email = username.getText();
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private boolean checkOfIP() { // xxx.xxx.xx.xx
        Pattern pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        String ip = ipAddress.getText();
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    private boolean checkOfDomain() { // xxx\YYYY.xxx.edu
        Pattern pattern = Pattern.compile("[a-z]{3}\\x5C[A-Z]{4}\\.[a-z]{3}\\.(edu)");
        String dom = domain.getText();
        Matcher matcher = pattern.matcher(dom);
        return matcher.matches();

    }

    private boolean checkOfPassword() { //azAZ09!@    - min 8
        Pattern pattern = Pattern.compile("(?=^.{8,}$)((?=.*\\d)(?=.*[$~!#]))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
        String pass = new String(password.getPassword());
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Validate validateForm = new Validate();
                validateForm.setDefaultCloseOperation(EXIT_ON_CLOSE);
                validateForm.setResizable(false);
                validateForm.setLocation(LOCALE_X, LOCALE_Y);
                validateForm.setSize(WIDTH, HEIGHT);
                validateForm.setVisible(true);
            }
        });
    }

}
