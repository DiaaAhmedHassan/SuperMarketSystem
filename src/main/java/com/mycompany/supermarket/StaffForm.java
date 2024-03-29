package com.mycompany.supermarket;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author moham
 */
public class StaffForm extends javax.swing.JFrame {
    
    public static BufferedWriter writer;
    public static BufferedReader reader;
    public static ArrayList<Staff> staffMembers;
    public static Staff member;

    /**
     * Creates new form StaffForm
     */
    
    public static void staffReload()
    {
        String row = "";
        try {
            reader = new BufferedReader(new FileReader("StaffMembers.csv"));

            if ((row = reader.readLine()) == null) {
                System.out.println("Null reader");
            } else {
                while ((row = reader.readLine()) != null) {
                    String[] parts = row.split(",");

                    String[] addresss = parts[4].split("-");
                    Address staffAddress = new Address(Integer.parseInt(addresss[2]), addresss[1], addresss[0]);

                    String[] cardParts = parts[6].split("-");
                    Card userCard = new Card(cardParts[0], cardParts[1], cardParts[2]);

                    member = new Staff(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), staffAddress, Double.parseDouble(parts[5]), userCard);
                    staffMembers.add(member);
                }
                System.out.println(staffMembers);
            }
        } catch (IOException ex) {
            Logger.getLogger(StaffForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(StaffForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public StaffForm() {
        initComponents();
        staffMembers = new ArrayList();
        
        staffReload();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteStaff = new javax.swing.JButton();
        clearStaff = new javax.swing.JButton();
        findButtonStaff = new javax.swing.JButton();
        addButtonStaff = new javax.swing.JButton();
        cardCode = new javax.swing.JTextField();
        cardId = new javax.swing.JTextField();
        cardUsername = new javax.swing.JTextField();
        memberName = new javax.swing.JTextField();
        memberPhone = new javax.swing.JTextField();
        memberAddress = new javax.swing.JTextField();
        memberId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        memberSalary = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        memberAge = new javax.swing.JTextField();
        clearStaff1 = new javax.swing.JButton();

        deleteStaff.setBackground(new java.awt.Color(255, 0, 0));
        deleteStaff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        deleteStaff.setForeground(new java.awt.Color(255, 255, 255));
        deleteStaff.setText("Delete");
        deleteStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStaffActionPerformed(evt);
            }
        });

        clearStaff.setBackground(new java.awt.Color(0, 0, 0));
        clearStaff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clearStaff.setForeground(new java.awt.Color(255, 255, 255));
        clearStaff.setText("Clear");
        clearStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearStaffActionPerformed(evt);
            }
        });

        findButtonStaff.setBackground(new java.awt.Color(0, 0, 0));
        findButtonStaff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        findButtonStaff.setForeground(new java.awt.Color(255, 255, 255));
        findButtonStaff.setText("Find");
        findButtonStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonStaffActionPerformed(evt);
            }
        });

        addButtonStaff.setBackground(new java.awt.Color(0, 0, 0));
        addButtonStaff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addButtonStaff.setForeground(new java.awt.Color(255, 255, 255));
        addButtonStaff.setText("Add");
        addButtonStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonStaffActionPerformed(evt);
            }
        });

        cardCode.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cardCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardCodeActionPerformed(evt);
            }
        });

        cardId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        cardUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        memberName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        memberName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberNameActionPerformed(evt);
            }
        });

        memberPhone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        memberAddress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        memberAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberAddressActionPerformed(evt);
            }
        });

        memberId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        memberId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberIdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("member ID");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Card ID");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("member name");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("code");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("phone number");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Address");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Salary");

        memberSalary.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        memberSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberSalaryActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Age");

        memberAge.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        clearStaff1.setBackground(new java.awt.Color(0, 0, 0));
        clearStaff1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clearStaff1.setForeground(new java.awt.Color(255, 255, 255));
        clearStaff1.setText("Update");
        clearStaff1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearStaff1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(memberPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(memberName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(memberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberId, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(memberAge, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(cardId)
                    .addComponent(cardUsername)
                    .addComponent(clearStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(findButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(addButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deleteStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardCode, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clearStaff1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardId, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(cardUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memberId, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(memberName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addComponent(memberAge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cardCode, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(memberPhone))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(memberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(findButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearStaff1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(memberSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearStaffActionPerformed
        memberId.setText("");
        memberName.setText("");
        memberAge.setText("");
        memberPhone.setText("");
        memberAddress.setText("");
        memberSalary.setText("");
        cardId.setText("");
        cardUsername.setText("");
        cardCode.setText("");
    }//GEN-LAST:event_clearStaffActionPerformed

    private void deleteStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStaffActionPerformed
      if(memberId.getText().isEmpty() || memberName.getText().isEmpty() || memberAge.getText().isEmpty() || memberPhone.getText().isEmpty() 
                || memberAddress.getText().isEmpty() || memberSalary.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "there is an empty fields");
        }else{
            //find the member
            for(Staff searchStaff : staffMembers){
                if(memberId.getText().equals(searchStaff.getId())){
                    //ask if the user sure
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this record", "Warning",JOptionPane.YES_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        staffMembers.remove(searchStaff);
                        try {
                            writer = new BufferedWriter(new FileWriter("StaffMembers.csv"));
                            writer.write(("id,name,phone,age,address,salary,cardId,username,cardCode\n")+String.valueOf(staffMembers).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                        
                            memberId.setText("");
                            memberName.setText("");
                            memberAge.setText("");
                            memberPhone.setText("");
                            memberAddress.setText("");
                            memberSalary.setText("");
                            cardId.setText("");
                            cardUsername.setText("");
                            cardCode.setText("");
                                    
                                     
                        } catch (IOException ex) {
                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                    }
                }
            }
        }
    }//GEN-LAST:event_deleteStaffActionPerformed

    private void addButtonStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonStaffActionPerformed

        if(memberId.getText().isEmpty()|| memberName.getText().isEmpty()|| memberAge.getText().isEmpty() || memberPhone.getText().isEmpty()){
          JOptionPane.showMessageDialog(null, "The data fieleds are empty","Error 404",JOptionPane.ERROR_MESSAGE);

        }else{
        String addresText = memberAddress.getText();
        String street="";
        String town="";
        int homeNumber=0;
        for(int i = 0; i<addresText.length(); i++){
            String[] parts = addresText.split("-");  
            homeNumber = Integer.parseInt(parts[0]);
            street = parts[1];
            town = parts[2];      
        }
        
        Address address = new Address(homeNumber,street,town);
        Card card = new Card(cardId.getText(), cardUsername.getText(), cardCode.getText());
        

        Staff member = new Staff(Integer.parseInt(memberId.getText()),memberName.getText(),memberPhone.getText(),Integer.parseInt(memberAge.getText()),address,Double.parseDouble(memberSalary.getText()),card);
        staffMembers.add(member);
        try {
            //write
            writer = new BufferedWriter(new FileWriter("StaffMembers.csv"));
            writer.write(("id,name,phone,age,address,salary,cardId,username,cardCode\n")+String.valueOf(staffMembers).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|",","));
            writer.close();
            System.out.println("Added to the file");
        } catch (IOException ex) {
            Logger.getLogger(StaffForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }//GEN-LAST:event_addButtonStaffActionPerformed

    private void cardCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardCodeActionPerformed

    private void memberAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberAddressActionPerformed

    private void memberIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberIdActionPerformed

    private void memberSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberSalaryActionPerformed

    private void findButtonStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonStaffActionPerformed


        Object foundProduct = null;
        System.out.println("Button clicked");
        //check the texts are not empty
        if(memberId.getText().isEmpty() && memberName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
        }//search
        else{
            try {
                foundProduct = NewJFrame.find(Integer.parseInt(memberId.getText()), "staff");
                
            } catch (NumberFormatException e) {
                
                foundProduct = NewJFrame.find("staffName", memberName.getText());
                
            }finally{
                
                if(foundProduct == null){
                    JOptionPane.showMessageDialog(null, "Not found! ","Error 404",JOptionPane.ERROR_MESSAGE);
                }else{
                
                Staff result = (Staff) foundProduct;
                result.display();
                }
            }
        }
    }//GEN-LAST:event_findButtonStaffActionPerformed

    private void memberNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberNameActionPerformed

    private void clearStaff1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearStaff1ActionPerformed
        member.setId(Integer.parseInt(memberId.getText()));
        member.setName(memberName.getText());
        member.setAge(Integer.parseInt(memberAge.getText()));
        
        member.setTelephone(memberPhone.getText());
        String addressParts[] = memberAddress.getText().split("-");
        Address address = new Address(Integer.parseInt(addressParts[2]), addressParts[1], addressParts[0]);
        member.setAdress(address);
        
        member.setSalary(Double.parseDouble(memberSalary.getText()));
        
        member.setCard(new Card(cardId.getText(), cardUsername.getText(), cardCode.getText()));
        staffMembers.set(staffMembers.indexOf(member), member);
        NewJFrame.writeToFiles("staff");
    }//GEN-LAST:event_clearStaff1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButtonStaff;
    public static javax.swing.JTextField cardCode;
    public static javax.swing.JTextField cardId;
    public static javax.swing.JTextField cardUsername;
    private javax.swing.JButton clearStaff;
    private javax.swing.JButton clearStaff1;
    private javax.swing.JButton deleteStaff;
    private javax.swing.JButton findButtonStaff;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JTextField memberAddress;
    public static javax.swing.JTextField memberAge;
    public static javax.swing.JTextField memberId;
    public static javax.swing.JTextField memberName;
    public static javax.swing.JTextField memberPhone;
    public static javax.swing.JTextField memberSalary;
    // End of variables declaration//GEN-END:variables
}
