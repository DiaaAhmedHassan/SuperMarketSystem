

package com.mycompany.supermarket;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;




public class NewJFrame extends javax.swing.JFrame {
    
    BufferedWriter writer;
    BufferedReader reader;
    boolean isFound;
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    ArrayList<Product> products;
    ArrayList<Client> clients;
    
    public NewJFrame() {
        initComponents();
        products = new ArrayList<>();
        clients = new ArrayList<>();
        
         
       
      
        //finish reading Qr code
       
    //read the product data from file using BufferedReader
    try {
    
    reader = new BufferedReader(new FileReader("productData.csv"));
    String line = "";
    /*
    loop the file line by line 
    then split every line to defirent data fields */
   
    if((line = reader.readLine()) == null){
        System.out.println("products null reader");
    }else{
    while ((line = reader.readLine()) != null) {
        String[] oneValues = line.split(","); //spiliting 
        
        //store the data in variables to generat new Product objects
        String id = oneValues[0];
        String name = oneValues[1];
        String category = oneValues[2];
        double buyingPrice = Double.parseDouble(oneValues[3]);
        double sellingPrice = Double.parseDouble(oneValues[4]);
        String expDate = oneValues[5];
        int items = Integer.parseInt(oneValues[6]);
        
        //enter the data as paramiters in the object
        Product pro = new Product(id, name, category, buyingPrice, sellingPrice, expDate, items);
        //enter the object to the arrayList 
        products.add(pro);
    }
        
        //finish reading
        
    }
    reader.close();
  
} catch (IOException ex) {
    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
} 

    
System.out.println(products);
 //reading client       
    try {
    reader = new BufferedReader(new FileReader("clientsData.csv"));
    String line = "";
    /*
    loop the file line by line 
    then split every line to defirent data fields */
    if((line = reader.readLine()) == null){
        System.out.println("clients Null reader");
    }else{
    while ((line = reader.readLine()) != null) {
        String[] oneValues = line.split(","); //spiliting 
        
        //store the data in variables to generat new Product objects
        String id = oneValues[0];
        String name = oneValues[1];
        String telephone = oneValues[2];
        String address = oneValues[3];       
        String[] splited =address.split("-");
        
        
        //enter the data as paramiters in the object
        Client client = new Client(id,name,telephone,new Address(Integer.parseInt(splited[0]),splited[1],splited[2]));
        //enter the object to the arrayList 
        clients.add(client);
        
        //finish reading
        
    }
    }
    reader.close();
        System.out.println(clients);
    
} catch (IOException ex) {
    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
} 

    //initWebcam(); 

    

    }
    
   
    private void initWebcam(){
       
       
       webcam = Webcam.getDefault();
       
       webcam.open();
       
       panel = new WebcamPanel(webcam);
       qrPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
       panel.setFPSDisplayed(true);
       panel.setMirrored(true);
       
       
       Thread qrCodeThred = new Thread(()->{
           while(true){
               isFound = false; 
               BufferedImage image = webcam.getImage();
               BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
               MultiFormatReader qrBarcodeReade = new MultiFormatReader();
              
               Result result;
               try {
                   result = qrBarcodeReade.decode(bitmap);
                   if(result != null){
                   String qrCodeData = result.getText();
                   
                   
                   ProductID.setText(qrCodeData);
                   for(Product searchQr : products){
                       if(ProductID.getText().equals(searchQr.getId())){
                           ProductID.setText("");
                           productName.setText("");
                           productCategory.setText("");
                           ProductBuyingPrice.setText("");
                           productSellingPrice.setText("");
                           expirationDate.setText("");
                           numberItem.setText("");
                           isFound = true;
                           ProductID.setText(searchQr.getId());
                           productName.setText(searchQr.getName());
                           productCategory.setText(searchQr.getCategory());
                           ProductBuyingPrice.setText(String.valueOf(searchQr.getBuyingPrice()));
                           productSellingPrice.setText(String.valueOf(searchQr.getSellingPrice()));
                           expirationDate.setText(String.valueOf(searchQr.getExpirationDate()));
                           numberItem.setText(String.valueOf(searchQr.getItemNo())); 
                         break;
                       }
                   }
                   
                   if(isFound == false){
                       JOptionPane.showMessageDialog(null, "Not Found!", "Error 404",JOptionPane.ERROR_MESSAGE);
                         ProductID.setText("");
                           productName.setText("");
                           productCategory.setText("");
                           ProductBuyingPrice.setText("");
                           productSellingPrice.setText("");
                           expirationDate.setText("");
                           numberItem.setText("");
                       
                   }
                
                  
               }
                   
               } catch (NotFoundException ex) {
                   Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
               }
               
               
           }
       });
       qrCodeThred.start();
        
      
        
        
                
    }
    
    
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ClientId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ClientName = new javax.swing.JTextField();
        ClientTelephone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        AddressStr = new javax.swing.JTextField();
        FindClient = new javax.swing.JButton();
        AddClient = new javax.swing.JButton();
        ClearClient = new javax.swing.JButton();
        DeleteClient = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        ProductID = new javax.swing.JTextField();
        productName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        productCategory = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ProductBuyingPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        productSellingPrice = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        expirationDate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        numberItem = new javax.swing.JTextField();
        FindNumber = new javax.swing.JButton();
        AddProduct = new javax.swing.JButton();
        clearProduct = new javax.swing.JButton();
        DeleteProduct = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Bill = new javax.swing.JTextArea();
        qrPanel = new javax.swing.JPanel();
        AddToBill = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Client ID");

        ClientId.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientIdActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Client Name");

        ClientName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientNameActionPerformed(evt);
            }
        });

        ClientTelephone.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClientTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientTelephoneActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Telephon number");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Address");

        AddressStr.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        AddressStr.setToolTipText("Street");
        AddressStr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressStrActionPerformed(evt);
            }
        });

        FindClient.setBackground(new java.awt.Color(0, 0, 0));
        FindClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        FindClient.setForeground(new java.awt.Color(255, 255, 255));
        FindClient.setText("Find");
        FindClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindClientActionPerformed(evt);
            }
        });

        AddClient.setBackground(new java.awt.Color(0, 0, 0));
        AddClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        AddClient.setForeground(new java.awt.Color(255, 255, 255));
        AddClient.setText("Add");
        AddClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddClientActionPerformed(evt);
            }
        });

        ClearClient.setBackground(new java.awt.Color(0, 0, 0));
        ClearClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClearClient.setForeground(new java.awt.Color(255, 255, 255));
        ClearClient.setText("Clear");
        ClearClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearClientActionPerformed(evt);
            }
        });

        DeleteClient.setBackground(new java.awt.Color(255, 51, 0));
        DeleteClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        DeleteClient.setForeground(new java.awt.Color(255, 255, 255));
        DeleteClient.setText("Delete");
        DeleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteClientActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Staff member:");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Point of sale:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Shift:");

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Change");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Edit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setText("Product ID");

        ProductID.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ProductID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductIDActionPerformed(evt);
            }
        });

        productName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        productName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Product Name");

        productCategory.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        productCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productCategoryActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("Product category");

        ProductBuyingPrice.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ProductBuyingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductBuyingPriceActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setText("Buying price");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("Selling price");

        productSellingPrice.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        productSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productSellingPriceActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setText("Expiration date");

        expirationDate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        expirationDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expirationDateActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setText("available items");

        numberItem.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        numberItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberItemActionPerformed(evt);
            }
        });

        FindNumber.setBackground(new java.awt.Color(0, 0, 0));
        FindNumber.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        FindNumber.setForeground(new java.awt.Color(255, 255, 255));
        FindNumber.setText("Find");
        FindNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindNumberActionPerformed(evt);
            }
        });

        AddProduct.setBackground(new java.awt.Color(0, 0, 0));
        AddProduct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        AddProduct.setForeground(new java.awt.Color(255, 255, 255));
        AddProduct.setText("Add");
        AddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProductActionPerformed(evt);
            }
        });

        clearProduct.setBackground(new java.awt.Color(0, 0, 0));
        clearProduct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        clearProduct.setForeground(new java.awt.Color(255, 255, 255));
        clearProduct.setText("Clear");
        clearProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearProductActionPerformed(evt);
            }
        });

        DeleteProduct.setBackground(new java.awt.Color(255, 51, 0));
        DeleteProduct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        DeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        DeleteProduct.setText("Delete");
        DeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteProductActionPerformed(evt);
            }
        });

        Bill.setColumns(20);
        Bill.setRows(5);
        Bill.setTabSize(4);
        Bill.setText("Date:00/00/2023\nClient: some one\nStaff member: some one\nPoint of sale:000\n-------------------------------\n");
        jScrollPane1.setViewportView(Bill);

        javax.swing.GroupLayout qrPanelLayout = new javax.swing.GroupLayout(qrPanel);
        qrPanel.setLayout(qrPanelLayout);
        qrPanelLayout.setHorizontalGroup(
            qrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qrPanelLayout.setVerticalGroup(
            qrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        AddToBill.setBackground(new java.awt.Color(0, 0, 0));
        AddToBill.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        AddToBill.setForeground(new java.awt.Color(255, 255, 255));
        AddToBill.setText("Add to bill");
        AddToBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(DeleteClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ClearClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ClientTelephone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(AddClient, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AddressStr, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ClientName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(productCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(ProductBuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(DeleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(expirationDate)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(productSellingPrice)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(numberItem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(qrPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1)))
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(AddToBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClientTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddressStr)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ClearClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeleteClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(qrPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(productSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(productCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(expirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ProductBuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberItem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddToBill, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientIdActionPerformed

    private void ClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientNameActionPerformed

    private void ClientTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientTelephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientTelephoneActionPerformed

    private void FindClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindClientActionPerformed
        boolean objectFound = false;
        if(ClientId.getText().isEmpty() && ClientName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The fieldes are empty! ","Error 404",JOptionPane.ERROR_MESSAGE);          
        }else{
            for(Client clientSearch: clients){
            /*Search by id*/
            if(ClientId.getText().equals(clientSearch.getId())){
                isFound = true;
               ClientId.setText(clientSearch.getId());
               ClientName.setText(clientSearch.getName());
               ClientTelephone.setText(clientSearch.getTelephone());
               AddressStr.setText(String.valueOf(clientSearch.getAdress()));
               break;
               /*Search by name*/
            }else if(ClientName.getText().replaceAll(" ", "").toLowerCase().equals(clientSearch.getName().toLowerCase().replaceAll(" ", "")));
                isFound = true;
                ClientId.setText(clientSearch.getId());
                ClientName.setText(clientSearch.getName());
                ClientTelephone.setText(clientSearch.getTelephone());
                AddressStr.setText(String.valueOf(clientSearch.getAdress()));
                break;
            }
        }
        
        if(isFound == false){
            JOptionPane.showMessageDialog(null, "Not found! ","Error 404",JOptionPane.ERROR_MESSAGE);
        } 
            
            
    }//GEN-LAST:event_FindClientActionPerformed

    private void AddClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddClientActionPerformed
     if(ClientId.getText().isEmpty()|| ClientName.getText().isEmpty()|| ClientTelephone.getText().isEmpty() || AddressStr.getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "The data fieleds are empty","Error 404",JOptionPane.ERROR_MESSAGE);
         
        }else{   
      int homeNumber;
      String street;
      String town;
      String ad=AddressStr.getText();
      String[]parts=ad.split("-");
      Address address = new Address(Integer.parseInt(parts[0]),parts[1],parts[2]);
      Client c = new Client(ClientId.getText(),ClientName.getText(),ClientTelephone.getText(),address); 
      clients.add(c);
      
       try {
            writer = new BufferedWriter(new FileWriter("clientsData.csv"));
            writer.write(String.valueOf(clients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
            writer.close();
            System.out.println("Added to file succefuly");
           
           //catch the expetion
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }//GEN-LAST:event_AddClientActionPerformed

    private void ClearClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearClientActionPerformed
        ClientId.setText("");
        ClientName.setText("");
        ClientTelephone.setText("");
        AddressStr.setText("");
        
    }//GEN-LAST:event_ClearClientActionPerformed

    private void DeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteClientActionPerformed
        if(ClientId.getText().isEmpty() || ClientName.getText().isEmpty() || ClientTelephone.getText().isEmpty() || AddressStr.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "there is an empty fields");
        }else{
            //find object
            for(Client client : clients){
                if(ClientId.getText().equals(client.getId())){
                    
                    //int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this record", "Warning",JOptionPane.YES_OPTION);
                    //if(option == JOptionPane.YES_OPTION){
                    
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this record ?", "warning", JOptionPane.YES_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        clients.remove(client);
                        try {
                            writer = new BufferedWriter(new FileWriter("clientsData.csv"));
                            writer.write(String.valueOf(clients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                        
                            ProductID.setText("");
                            productName.setText("");
                            productCategory.setText("");
                            ProductBuyingPrice.setText("");
                            productSellingPrice.setText("");
                            expirationDate.setText("");
                            numberItem.setText("");

                            
                        } catch (IOException ex) {
                            //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("ERROR");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_DeleteClientActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffForm().setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton6ActionPerformed

    private void ProductIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductIDActionPerformed

    private void productNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productNameActionPerformed

    private void productCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productCategoryActionPerformed

    private void ProductBuyingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductBuyingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductBuyingPriceActionPerformed

    private void productSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productSellingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productSellingPriceActionPerformed

    private void expirationDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expirationDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expirationDateActionPerformed

    private void numberItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberItemActionPerformed

    
        
    
    private void FindNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindNumberActionPerformed
        boolean objectFound = false;
        System.out.println("Button clicked");
        //check the texts are not empty
        if(ProductID.getText().isEmpty() && productName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
        }//search
        else{
            for(Product pro: products){
                //search by id
                if(ProductID.getText().equals(pro.getId())){
                    ProductID.setText(pro.getId());
                    productName.setText(pro.getName());
                    productCategory.setText(pro.getCategory());
                    ProductBuyingPrice.setText(String.valueOf(pro.getBuyingPrice()));
                    productSellingPrice.setText(String.valueOf(pro.getSellingPrice()));
                    expirationDate.setText(pro.getExpirationDate());
                    numberItem.setText(String.valueOf(pro.getItemNo()));
                    objectFound = true;
                    break;
                }//search by name
                else if(productName.getText().toLowerCase().replaceAll(" ","").equals(pro.getName().toLowerCase().replaceAll(" ", ""))){
                      ProductID.setText(pro.getId());
                      productName.setText(pro.getName());
                      productCategory.setText(pro.getCategory());
                      ProductBuyingPrice.setText(String.valueOf(pro.getBuyingPrice()));
                      productSellingPrice.setText(String.valueOf(pro.getSellingPrice()));
                      expirationDate.setText(pro.getExpirationDate());
                      numberItem.setText(String.valueOf(pro.getItemNo()));
                      objectFound = true;
                      break;
                }
            }
            if(objectFound == false){
                JOptionPane.showMessageDialog(null, "Not found! ","Error 404",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_FindNumberActionPerformed

    
    private void AddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProductActionPerformed

        if(ProductID.getText().isEmpty()|| productName.getText().isEmpty()|| productCategory.getText().isEmpty() || ProductBuyingPrice.getText().isEmpty() || productSellingPrice.getText().isEmpty() || expirationDate.getText().isEmpty() || numberItem.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The data fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
            
        }else{
        /*get the data from the texts then add the data to the Arraylist*
        
        */
       Product pro = new Product(ProductID.getText(),productName.getText(),productCategory.getText()
        ,Double.parseDouble(ProductBuyingPrice.getText()),Double.parseDouble(productSellingPrice.getText()),expirationDate.getText(),Integer.parseInt(numberItem.getText()));
        products.add(pro);
        
        //try to write the new product to the csv file
        try {
            writer = new BufferedWriter(new FileWriter("productData.csv"));
            /*
            remove all unused simbols to write the data in the file like that
           4545,Milk,diary,20.0,28.0,4-8-2024
           7241,chease,diary,35.0,40.0,4-9-2024
            
            instate of writing it like that
            [4545/Milk/diary/20.0/28.0/4-8-2024
            , 7241/chease/diary/35.0/40.0/4-9-2024
            ]
            bec these formate will be the csv file hard to read]
            */
            writer.write(String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
            writer.close();
            System.out.println("Added to file succefuly");
            ProductID.setText("");
            productName.setText("");
            productCategory.setText("");
            ProductBuyingPrice.setText("");
            productSellingPrice.setText("");
            expirationDate.setText("");
            numberItem.setText("");
           
           //catch the expetion
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
      
    }//GEN-LAST:event_AddProductActionPerformed

    private void clearProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearProductActionPerformed
        ProductID.setText("");
        productName.setText("");
        productCategory.setText("");
        ProductBuyingPrice.setText("");
        productSellingPrice.setText("");
        expirationDate.setText("");
        numberItem.setText("");
    }//GEN-LAST:event_clearProductActionPerformed

    private void DeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteProductActionPerformed
  
        
        
        //check the feildes are not empty
        if(ProductID.getText().isEmpty() || productName.getText().isEmpty() || productCategory.getText().isEmpty() || ProductBuyingPrice.getText().isEmpty() 
                || productSellingPrice.getText().isEmpty() || expirationDate.getText().isEmpty() || numberItem.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "there is an empty fields");
        }else{
            //find the product
            for(Product searchPro : products){
                if(ProductID.getText().equals(searchPro.getId())){
                    //ask if the user sure
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this record", "Warning",JOptionPane.YES_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        products.remove(searchPro);
                        try {
                            writer = new BufferedWriter(new FileWriter("productData.csv"));
                            writer.write(String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                        
                            ProductID.setText("");
                            productName.setText("");
                            productCategory.setText("");
                            ProductBuyingPrice.setText("");
                            productSellingPrice.setText("");
                            expirationDate.setText("");
                            numberItem.setText("");
           
                        } catch (IOException ex) {
                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                    }
                }
            }
        }
     
    }//GEN-LAST:event_DeleteProductActionPerformed

    private void AddToBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddToBillActionPerformed

    private void AddressStrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressStrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressStrActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddClient;
    private javax.swing.JButton AddProduct;
    private javax.swing.JButton AddToBill;
    private javax.swing.JTextField AddressStr;
    private javax.swing.JTextArea Bill;
    private javax.swing.JButton ClearClient;
    private javax.swing.JTextField ClientId;
    private javax.swing.JTextField ClientName;
    private javax.swing.JTextField ClientTelephone;
    private javax.swing.JButton DeleteClient;
    private javax.swing.JButton DeleteProduct;
    private javax.swing.JButton FindClient;
    private javax.swing.JButton FindNumber;
    private javax.swing.JTextField ProductBuyingPrice;
    private javax.swing.JTextField ProductID;
    private javax.swing.JButton clearProduct;
    private javax.swing.JTextField expirationDate;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField numberItem;
    private javax.swing.JTextField productCategory;
    private javax.swing.JTextField productName;
    private javax.swing.JTextField productSellingPrice;
    private javax.swing.JPanel qrPanel;
    // End of variables declaration//GEN-END:variables
}
