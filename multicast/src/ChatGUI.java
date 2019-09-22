/*
    Description: This is the Multicast chat. The idea is someone hosts the communication between users.
    Author: Vitor Bueno de Camargo, Gabriel David Sacca
    Created at: September, 18th. 2019
    Updated at: September, 22th. 2019
*/

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.*;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatGUI extends javax.swing.JFrame implements UIControl {
    private Listener listener;

    DefaultListModel<String> modelList;
    ProtocolController protoController;

    Style blackStyle, blueStyle, greenStyle;
    StyledDocument styledDoc;

    public ChatGUI() {
        initComponents();

        styledDoc = areaMensagem.getStyledDocument();
        blackStyle = areaMensagem.addStyle("blackStyle", null);
        blueStyle = areaMensagem.addStyle("blueStyle", null);
        greenStyle = areaMensagem.addStyle("greenStyle", null);
        StyleConstants.setForeground(blackStyle, Color.black);
        StyleConstants.setForeground(blueStyle, Color.blue);
        StyleConstants.setForeground(greenStyle, new Color(31,92,0));

        modelList = new DefaultListModel<>();
        lstLista.setModel(modelList);

        btnDesconectar.setEnabled(false);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        pnlMensagem = new javax.swing.JPanel();
        txtMensagem = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        scrollArea = new javax.swing.JScrollPane();
        areaMensagem = new javax.swing.JTextPane();
        pnlConfiguracao = new javax.swing.JPanel();
        pnlOpcoes = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        lblPorta = new javax.swing.JLabel();
        lblApelido = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        txtPorta = new javax.swing.JTextField();
        txtApelido = new javax.swing.JTextField();
        pnlBotoes = new javax.swing.JPanel();
        btnConectar = new javax.swing.JButton();
        btnDesconectar = new javax.swing.JButton();
        lstLista = new javax.swing.JList();

        getContentPane().setLayout(new java.awt.BorderLayout(3, 1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("mIRC Vitor & Gabriel");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMensagem.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 2));

        txtMensagem.setColumns(30);
        txtMensagem.setMinimumSize(new java.awt.Dimension(19, 19));
        pnlMensagem.add(txtMensagem);

        btnEnviar.setMnemonic('E');
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        pnlMensagem.add(btnEnviar);

        getContentPane().add(pnlMensagem, java.awt.BorderLayout.NORTH);

        areaMensagem.setPreferredSize(new Dimension(300,300));
        scrollArea.setViewportView(areaMensagem);

        getContentPane().add(scrollArea, java.awt.BorderLayout.CENTER);

        pnlConfiguracao.setLayout(new java.awt.BorderLayout());

        pnlConfiguracao.setBorder(new javax.swing.border.TitledBorder(null, " Configura\u00e7\u00f5es ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        pnlOpcoes.setLayout(new java.awt.GridBagLayout());

        pnlOpcoes.setMinimumSize(new java.awt.Dimension(124, 19));
        pnlOpcoes.setPreferredSize(new java.awt.Dimension(210, 19));
        lblIP.setText("ID Grupo: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlOpcoes.add(lblIP, gridBagConstraints);

        lblPorta.setText("Porta: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlOpcoes.add(lblPorta, gridBagConstraints);

        lblApelido.setText("Apelido: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlOpcoes.add(lblApelido, gridBagConstraints);

        txtIP.setColumns(10);
        txtIP.setText("225.1.2.3");
        txtIP.setMinimumSize(new java.awt.Dimension(124, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlOpcoes.add(txtIP, gridBagConstraints);

        txtPorta.setColumns(6);
        txtPorta.setText("6789");
        txtPorta.setMinimumSize(new java.awt.Dimension(70, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlOpcoes.add(txtPorta, gridBagConstraints);

        txtApelido.setColumns(10);
        txtApelido.setText("campiolo");
        txtApelido.setMinimumSize(new java.awt.Dimension(114, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlOpcoes.add(txtApelido, gridBagConstraints);

        pnlConfiguracao.add(pnlOpcoes, java.awt.BorderLayout.WEST);

        pnlBotoes.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        btnConectar.setMnemonic('n');
        btnConectar.setText("Entrar no grupo");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        pnlBotoes.add(btnConectar);

        btnDesconectar.setMnemonic('S');
        btnDesconectar.setText("Sair do grupo");
        btnDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconectarActionPerformed(evt);
            }
        });

        pnlBotoes.add(btnDesconectar);

        pnlConfiguracao.add(pnlBotoes, java.awt.BorderLayout.EAST);

        getContentPane().add(pnlConfiguracao, java.awt.BorderLayout.SOUTH);

        lstLista.setBorder(new javax.swing.border.EtchedBorder());
        lstLista.setFont(new java.awt.Font("Dialog", 0, 10));
        lstLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstLista.setFixedCellWidth(100);
        lstLista.setPreferredSize(new java.awt.Dimension(100, 0));
        getContentPane().add(lstLista, java.awt.BorderLayout.EAST);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            modelList.addElement("Todos");

            btnConectar.setEnabled(false);
            txtApelido.setEnabled(false);
            txtIP.setEnabled(false);
            txtPorta.setEnabled(false);
            btnDesconectar.setEnabled(true);

            Properties prop = new Properties();
            prop.put("multicastIP", InetAddress.getByName(txtIP.getText()));
            prop.put("multicastPort", Integer.parseInt(txtPorta.getText()));
            prop.put("udpPort", 6799);
            prop.put("nickname", this.getApelido());
            prop.put("UI", this);
            
            protoController = new ProtocolController(prop);
            listener = new Listener(protoController);
            listener.run();
            
            protoController.join();

        } catch(UnknownHostException uhe) {
            JOptionPane.showMessageDialog(this,
                uhe.getMessage(),"Erro ao entrar no grupo.",JOptionPane.ERROR_MESSAGE);
        } catch(IOException ioe) {
            JOptionPane.showMessageDialog(this,
                ioe.getMessage(), "Erro de I/O.",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnDesconectarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if(protoController != null) {
                protoController.leave();
                protoController.close();
                protoController = null;
            } 
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, ioe.getMessage(),
                "Erro ao sair do grupo.", JOptionPane.ERROR_MESSAGE);
        }

        btnConectar.setEnabled(true);
        txtApelido.setEnabled(true);
        txtIP.setEnabled(true);
        txtPorta.setEnabled(true);
        btnDesconectar.setEnabled(false);

        modelList.clear();
    }

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = txtMensagem.getText();
        
        if((msg.trim().length() > 0) && (lstLista.getSelectedIndex() != -1)) {
            String nickTarget = (String)lstLista.getSelectedValue();

            this.writeLocalMessage(this.getApelido(), msg);
            txtMensagem.setText("");
            txtMensagem.requestFocus();

            try {
                protoController.send(nickTarget, msg);
            } catch(IOException ioe) {
                JOptionPane.showMessageDialog(this,
                    ioe.getMessage(), "Erro de I/O.",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        this.btnDesconectarActionPerformed(null);
    }

    public void writeMessage(String id, String mensagem) {
        try {
            styledDoc.insertString(styledDoc.getLength(), "<" + id + "> ", greenStyle);
            styledDoc.insertString(styledDoc.getLength(), mensagem + "\n", blackStyle);
        } catch(BadLocationException ble) {
            System.err.println("Erro ao escrever mensagem na UI" + ble);
        }
    }

    public void writeLocalMessage(String id, String mensagem) {
        try {
            styledDoc.insertString(styledDoc.getLength(), "<" + id + "> ", blueStyle);
            styledDoc.insertString(styledDoc.getLength(), mensagem + "\n", blackStyle);
        } catch(BadLocationException ble) {
            System.err.println("Erro ao escrever mensagem na UI" + ble);
        }
    }

    public void addNickname(String apelido) {
        modelList.addElement(apelido);
    }

    public void remNickname(String apelido) {
        modelList.removeElement(apelido);
    }

    public String getApelido() {
        return txtApelido.getText();
    }
    
    @Override
    public void update(Message m) {
        switch(m.getType()) {
            case 1:
                this.addNickname(m.getSource());
                break;
            case 2:
                this.addNickname(m.getSource());
                break;
            case 3:
            case 4:
                this.writeMessage(m.getSource(), m.getMessage());
                break;
            case 5:
                this.remNickname(m.getSource());
                break;
        }
    }

    private javax.swing.JTextPane areaMensagem;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel lblApelido;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblPorta;
    private javax.swing.JList lstLista;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlConfiguracao;
    private javax.swing.JPanel pnlMensagem;
    private javax.swing.JPanel pnlOpcoes;
    private javax.swing.JScrollPane scrollArea;
    private javax.swing.JTextField txtApelido;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtMensagem;
    private javax.swing.JTextField txtPorta;
}
