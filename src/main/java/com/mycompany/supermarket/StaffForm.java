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
    
    BufferedWriter writer;
    BufferedReader reader;
    ArrayList<Staff> staffMembers;
    

    /**
     * Creates new form StaffForm
     */
    public StaffForm() {
        initComponents();
        staffMembers = new ArrayList();
        String row = "";
        String memberId = "";
        String memberName = "";
        String memberPhone = "";
        int memberAge = 0;
        String street = "";
        String town = "";
        int homeNumber = 0;
        double memberSalary = 0;
        
        //input variables
        String id, name, phoneNumber = "";
        try {
            reader = new BufferedReader(new FileReader("StaffMembers.csv"));
            
            while((row = reader.readLine()) != null)
            {
                String[] parts = row.split(",");
                memberId = parts[0];
                memberName = parts[1];
                memberPhone = parts[2];
                memberAge = Integer.parseInt(parts[3]);
                String[] addresss = parts[4].split("-");
                homeNumber = Integer.parseInt(addresss[2]);
                street = addresss[0];
                town = addresss[1];
                memberSalary = Double.parseDouble(parts[5]);
                
                Address staffAddress = new Address(homeNumber, street, town);
                Staff member = new Staff(memberId, memberName, memberPhone, memberAge, staffAddress, memberSalary);
                staffMembers.add(member);
                
                //consistantly checking for new inputs
//                id = this.memberId.getText();
//                name = this.memberName.getText();
//                phoneNumber = this.memberPhone.getText();
            }
            reader.close();
            System.out.println(staffMembers);
            
            
        } 
        catch (IOException ex) {
            Logger.getLogger(StaffForm.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField2)
                        .addComponent(jTextField3)
                        .addComponent(clearStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(findButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(addButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(deleteStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)))
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
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(memberPhone))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(findButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButtonStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(memberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memberSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(112, 112, 112))
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
                            writer = new BufferedWriter(new FileWriter("productData.csv"));
                            writer.write(String.valueOf(staffMembers).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                        
                            memberId.setText("");
                            memberName.setText("");
                            memberAge.setText("");
                            memberPhone.setText("");
                            memberAddress.setText("");
                            memberSalary.setText("");
                                     
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
        
        Staff member = new Staff(memberId.getText(),memberName.getText(),memberPhone.getText(),Integer.parseInt(memberAge.getText()),address,Double.parseDouble(memberSalary.getText()));
        staffMembers.add(member);
        try {
            //write
            writer = new BufferedWriter(new FileWriter("StaffMembers.csv"));
            writer.write(String.valueOf(staffMembers).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|",","));
            writer.close();
            System.out.println("Added to the file");
        } catch (IOException ex) {
            Logger.getLogger(StaffForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }//GEN-LAST:event_addButtonStaffActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
        // TODO add your handling code here:
        //use getters
        //search accourding to the filled field
        //
        
        if(memberId.getText().isEmpty() && memberName.getText().isEmpty() && memberPhone.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The feileds is empty!","Error 404",JOptionPane.ERROR_MESSAGE);
        }else{
        
        for(Staff staff: staffMembers){
            /*Search by id*/
            if(memberId.getText().equals(staff.getId())){
                memberId.setText(staff.getId());
                memberName.setText(staff.getName());
                memberAge.setText(String.valueOf(staff.getAge()));
                memberPhone.setText(staff.getTelephone());
                memberAddress.setText(staff.getAdress().toString());
                memberSalary.setText(String.valueOf(staff.getSalary()));
                break;
               /*Search by name*/
            }else if(memberName.getText().replaceAll(" ", "").toLowerCase().equals(staff.getName().toLowerCase().replaceAll(" ", ""))){
                memberId.setText(staff.getId());
                memberName.setText(staff.getName());
                memberAge.setText(String.valueOf(staff.getAge()));
                memberPhone.setText(staff.getTelephone());
                memberAddress.setText(staff.getAdress().toString());
                memberSalary.setText(String.valueOf(staff.getSalary()));
                break;
            }
            else    
            {
                JOptionPane.showMessageDialog(null, "Not found","Error 404",JOptionPane.ERROR_MESSAGE);
                       
            }
        }
        }
    }//GEN-LAST:event_findButtonStaffActionPerformed

    private void memberNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberNameActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButtonStaff;
    private javax.swing.JButton clearStaff;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField memberAddress;
    private javax.swing.JTextField memberAge;
    private javax.swing.JTextField memberId;
    private javax.swing.JTextField memberName;
    private javax.swing.JTextField memberPhone;
    private javax.swing.JTextField memberSalary;
    // End of variables declaration//GEN-END:variables
}
