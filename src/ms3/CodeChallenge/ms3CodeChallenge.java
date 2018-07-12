package ms3.CodeChallenge;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ms3CodeChallenge extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static JTable tbl_client;
	private static JTable tbl_transaction;
	java.sql.Connection connection =null;
	
	static JLabel lblTransactionTable;
	static JLabel lblClientTable;
	private JTextField client_name;
	private JTextField clientid;
	private JTextField contact_no;
	private JTextField mailing_add;
	private JTextField member_since;
	private JTextField branch_reg;
	
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JButton btnInsert;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ms3CodeChallenge frame = new ms3CodeChallenge();
					lblTransactionTable.setVisible(false);
					lblClientTable.setVisible(false);
					tbl_transaction.setVisible(false);
					tbl_client.setVisible(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public ms3CodeChallenge() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 719, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Check connection button
		JButton btnConnection = new JButton("Check Conn");
		btnConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			
				myDBConnection conn = new myDBConnection();
				conn.getConnection();
				
				
			}
		});
		
		//Button for refresh
		JButton btnClient = new JButton("Client Table");
		btnClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model;
				ResultSet rs = null;
				
				String query = "SELECT clientid as 'Client ID', client_name as 'Client Name', contact_no as 'Contact Number', "
						+ "mailing_add as 'Mailing address', member_since as 'Member Since', branch_reg as 'Branch Registration' "
						+ "FROM tbl_client";
				
				try {
					tbl_client.setVisible(true);
					tbl_transaction.setVisible(false);
					lblClientTable.setVisible(true);
					lblTransactionTable.setVisible(false);
					
//					connection = myDBConnection.populateTable();
					
					connection  = myDBConnection.getConnection();
					pst = connection.prepareStatement(query);
					rs = pst.executeQuery();
					
//					model = new DefaultTableModel();
//					model.setColumnIdentifiers(column);
//					tbl_client.setModel(model);
					tbl_client.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
					System.out.println(e1);
				}
				
			}
		});
		btnClient.setBounds(350, 335, 109, 23);
		contentPane.add(btnClient);
		
		
		//Button for Transaction
		JButton btnTransaction = new JButton("Transaction");
		btnTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				DefaultTableModel model;
				String getTable_query = "SELECT * FROM tbl_client";
			
				try {
					tbl_transaction.setVisible(true);
					tbl_client.setVisible(false);
					lblClientTable.setVisible(false);
					lblTransactionTable.setVisible(true);
					
					connection  = myDBConnection.getConnection();
					pst = connection.prepareStatement("SELECT * FROM tbl_transaction");
					rs = pst.executeQuery();

					tbl_transaction.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
					System.out.println(e1);
				}
				
				
				
			}
		});
		btnTransaction.setBounds(231, 335, 109, 23);
		contentPane.add(btnTransaction);
		
		//TABLE FOR CLIENT
		tbl_client = new JTable();
		tbl_client.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					int row = tbl_client.getSelectedRow();
					String clientID = (tbl_client.getModel().getValueAt(row, 0).toString());
					String query = "SELECT * FROM tbl_client WHERE clientid='"+ clientID + "'";
					pst = connection.prepareStatement(query);
					rs = pst.executeQuery();
					
					if (rs.next()) {
						
						String id = rs.getString("clientid");
						clientid.setText(id);
						
						String clientName = rs.getString("client_name");
						client_name.setText(clientName);
						
						String  contacNo= rs.getString("contact_no");
						contact_no.setText(contacNo);
						
						String mailingAdd = rs.getString("mailing_add");
						mailing_add.setText(mailingAdd);
						
						String memberSince = rs.getString("member_since");
						member_since.setText(memberSince);
						
						String branchReg = rs.getString("branch_reg");
						branch_reg.setText(branchReg);
					}
					
				}catch (Exception client_table) {
					
				}
			}
		});
		
		//INSERT VALUE TO TABLE CLIENT
		btnInsert = new JButton("Insert");
		btnInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean rs;
//				String clientName = client_name.getText();
//				String contactNo = contact_no.getText();
//				String mailAdd = mailing_add.getText();
//				String memberSince = member_since.getText();
//				String branchReg = branch_reg.getText();
				
			
				try {
					connection  = myDBConnection.getConnection();
					pst = connection.prepareStatement("INSERT INTO tbl_client (client_name,contact_no,mailing_add,member_since,branch_reg) VALUES (?,?,?,?,?)");
					pst.setString(1, client_name.getText());
					pst.setString(2, contact_no.getText());
					pst.setString(3, mailing_add.getText());
					pst.setString(4,  member_since.getText());
					pst.setString(5, branch_reg.getText());
					rs = pst.execute();
//					tbl_transaction.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				
			}
		});
		btnInsert.setBounds(202, 450, 89, 23);
		contentPane.add(btnInsert);
		
		
		
		
		tbl_client.setBounds(10, 62, 683, 262);
		contentPane.add(tbl_client);
		
		
		//Client Table
		lblClientTable = new JLabel("Client Table");
		lblClientTable.setBounds(10, 22, 67, 14);
		contentPane.add(lblClientTable);
		
		//Transaction Table
		tbl_transaction = new JTable();
		tbl_transaction.setBounds(10, 62, 683, 262);
		contentPane.add(tbl_transaction);
//		
//		JLabel lblTransactionTable = new JLabel("Transaction Table");
//		lblTransactionTable.setBounds(389, 42, 120, 14);
//		contentPane.add(lblTransactionTable);
//		
		//Button's
		btnConnection.setBounds(604, 450, 89, 23);
		contentPane.add(btnConnection);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(10, 450, 86, 23);
		contentPane.add(btnUpdate);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setBounds(106, 450, 86, 23);
		contentPane.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(505, 450, 89, 23);
		contentPane.add(btnDelete);
		
		
		lblTransactionTable = new JLabel("Transaction Table");
		lblTransactionTable.setBounds(10, 36, 109, 14);
		contentPane.add(lblTransactionTable);
		

		//Textfield for client
		client_name = new JTextField();
		client_name.setBounds(106, 403, 86, 20);
		contentPane.add(client_name);
		client_name.setColumns(10);
		
		clientid = new JTextField();
		clientid.setBounds(10, 403, 86, 20);
		contentPane.add(clientid);
		clientid.setColumns(10);
		clientid.setEditable(false);
		
		contact_no = new JTextField();
		contact_no.setBounds(202, 403, 86, 20);
		contentPane.add(contact_no);
		contact_no.setColumns(10);
		
		mailing_add = new JTextField();
		mailing_add.setBounds(298, 403, 86, 20);
		contentPane.add(mailing_add);
		mailing_add.setColumns(10);
		
		member_since = new JTextField();
		member_since.setBounds(394, 403, 86, 20);
		contentPane.add(member_since);
		member_since.setColumns(10);
		
		branch_reg = new JTextField();
		branch_reg.setBounds(490, 403, 86, 20);
		contentPane.add(branch_reg);
		branch_reg.setColumns(10);
		
		
	}
}
