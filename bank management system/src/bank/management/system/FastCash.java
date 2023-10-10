
package bank.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;


public class FastCash extends JFrame implements ActionListener{
    
    JButton hundred,fivehundred,oneK,twoK,fiveK,tenK, back;
    String pinnumber;
    
    FastCash(String pinnumber){
        this.pinnumber =  pinnumber;
        setLayout(null);
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text= new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(210,300,700,35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System",Font.BOLD,16));
        image.add(text);
        
       hundred= new JButton("Rs 100");
        hundred.setBounds(170,415,150,30);
        hundred.addActionListener(this);
        image.add(hundred);
        
        fivehundred= new JButton("Rs 500");
        fivehundred.setBounds(355,415,150,30);
        fivehundred.addActionListener(this);
        image.add(fivehundred);
        oneK = new JButton("Rs 1000");
        oneK.setBounds(170,450,150,30);
        oneK.addActionListener(this);
        image.add(oneK);
        
        twoK= new JButton("Rs 2000");
       twoK.setBounds(355,450,150,30);
        twoK.addActionListener(this);
        image.add(twoK);
        
       fiveK= new JButton("Rs 5000");
        fiveK.setBounds(170,485,150,30);
        fiveK.addActionListener(this);
        image.add(fiveK);
        
        tenK= new JButton("Rs 10000");
        tenK.setBounds(355,485,150,30);
        tenK.addActionListener(this);
        image.add(tenK);
        
        back= new JButton("BACK");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);

        
        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
        
    }
    public void actionPerformed(ActionEvent ae){
       try{
        
            String amount= ((JButton)ae.getSource()).getText().substring(3);//Rs 500
            Conn c = new Conn();
            
                ResultSet rs= c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                int balance =0;
                while(rs.next()){
                    if(rs.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs.getString("amount"));
                    }
                    else{
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                String num="17";    //  ------
                if(ae.getSource() != back && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }//-----------
                if(ae.getSource()==back){
                    this.setVisible(false);
                    new Transactions(pinnumber).setVisible(true);
                }else{
                Date date = new Date();
                String query = "insert into bank values('"+pinnumber+"','"+date+"','Withdrawl','"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs "+ amount + " Debited Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            }}
            catch(Exception e){
                System.out.println(e);
            }
        }
        
    
    
    
    public static void main(String args[]){
        new FastCash("").setVisible(true);
        
    }
}
