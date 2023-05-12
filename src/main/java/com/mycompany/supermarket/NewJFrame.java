

package com.mycompany.supermarket;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.AbstractList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import jdk.jfr.events.FileReadEvent;




public class NewJFrame extends javax.swing.JFrame {
    
    //-------------- file manipulation ------------
    public static BufferedWriter writer;
    public static BufferedReader reader;
    
    //-------------- webcam----------------
    private Webcam webcam = null;
    private WebcamPanel panel = null;

    //--------------- search helpers -------------
    boolean isFound;
    private static Object objectFound = null;
    public static Product productResult = null;//the latest product search result in the global scope
    public static Client clientResult = null;//the latest client search result in the global scope
    //------------------ lists ---------------------
    public static ArrayList<Product> products;
    public static ArrayList<Client> clients;
    public static ArrayList<GoldenClient> goldenClients;
    public static ArrayList<Product> discountProducts;
    
    //----------- counters ---------------
    private int shiftCounter = 1;
    private int pointCounter = 1;
    
   
    
    
    //------------------ methods -------------
    public static boolean isNumiric(String string){
        try{
            Integer.parseInt(string);
            return true;
        }catch(NumberFormatException ex){
                return false;
             }
    }
    
    public static Object find(String key, String name)
    {
        //search by name
        //if found return the obeject
        //if not return null
        Object returned = null;
        
        switch(key){
            case "product":
                System.out.println("searching for a product py name");
                name = name.toLowerCase().replaceAll(" ", "");
            for (Product product : products) {
                if(name.equals(product.getName().toLowerCase().replaceAll(" ", "")))
                {
                    returned = product;
                    System.out.println(product.toString());
                    break;
                }
            }
            break;


            case "client":
                System.out.println("search for a client by name");
                name = name.toLowerCase().replaceAll(" ", "");
                for(Client client : clients)
                {
                    if(name.equals(client.getName().toLowerCase().replaceAll(" ", "")))
                    {
                        returned = client;
                        System.out.println(client.toString());
                        break;
                    }
                }
                
            break;
            case "staff":
                System.out.println("searching for a staff member by name");
                name = name.toLowerCase().replaceAll(" ", "");
                for(Staff staff : StaffForm.staffMembers)
                {
                    if(name.equals(staff.getName().toLowerCase().replaceAll(" ", "")))
                    {
                        returned = staff;
                        System.out.println(staff.toString());
                        break;
                    }
                }
                
            break;
        }
        
        return(returned);
        
    }
   
    //overload
    public static Object find(int id, String key)
    {
        //search my id
        //if found return the obeject
        //if not return null
        Object returned = null;
        
        
        switch(key){
            case "client":
                System.out.println("search for a client by id");
                for(Client cli : clients)
                {
                    if(id == cli.getId())
                    {
                        returned = cli;
                        System.out.println(cli.toString());
                        break;
                    }
                }
                
            break;
            case "product":
                System.out.println("search for a product by id");
                for(Product product : products)
                {
                    if(id == product.getId())
                    {
                        returned = product;
                        System.out.println(product.toString());
                        break;
                    }
                }
            break;
            case "staff":
                System.out.println("search for a staff member by id");
                for(Staff staff : StaffForm.staffMembers)
                {
                    if(id == staff.getId())
                    {
                        returned = staff;
                        System.out.println(staff.toString());
                        break;
                    }
                }
                
            break;
            
            case "golden":
                System.out.println("Searching for golden client");
                for(GoldenClient golden: goldenClients){
                    
                   if(id == golden.getId()){
                       returned = golden;
                       System.out.println(golden.toString());
                       break;
                   }
                }
        }
        
        
        return(returned);
    }
    
    public static boolean checkDate(String stringDate, String key){
        //paramitar stringDate used to take the date we want to check
        //split the parts taken from the file
        String[] dateParts = stringDate.split("/");
        int stringDay = Integer.parseInt(dateParts[0]);
        int stringMonth = Integer.parseInt(dateParts[1]);
        int stringYear = Integer.parseInt(dateParts[2]);
       
        //init calendar
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;// added 1 because months start from zero
        int year = calendar.get(Calendar.YEAR);//Jan is 0, Feb is 1, mar is 2......
   
        switch(key){
            case "product":
                 if(year>stringYear){
                    return true;
                }else{ 
                    return (month>stringMonth && year>=stringYear) || (day>=stringDay && month>=stringMonth);
                }
            case "client":
                if(year >= stringYear + 5){
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
            }
                  
    }
    
    public static void reload(String key)
    {
        //how it works:
        //modification, adding, deleting, editing a specific object, "we are always modifying the csv file"
        //this method will translate the rows from our file to objects
        //it'll be called when any modification happens
        
        //swtich case on the key
//        String file = null;
//        AbstractList list = null;
        String line = " ";
        switch(key)
        {//client, golden, product, staffmember
            case "client"://the sub date missing
                try
                {
                    reader = new BufferedReader(new FileReader("clientsData.csv"));
                    while ((line = reader.readLine()) != null) {
                        String[] row = line.split(",");
                        String[] addressSplit = row[3].split("-");
                        Address clientAddress = new Address(Integer.parseInt(addressSplit[0]), addressSplit[1], addressSplit[2]);
                        clients.add(new Client(Integer.parseInt(row[0]), row[1], row[2], clientAddress, row[4]));
                    }
                }catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            break;
            case "golden":
                
            break;
            case "product":
                try {
                reader = new BufferedReader(new FileReader("productData.csv"));
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    products.add(new Product(Integer.parseInt(row[0]), row[1], row[2], Double.parseDouble(row[3]), Double.parseDouble(row[4]), row[5], Integer.parseInt(row[6])));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            break;
        }
    }
    
    private void initWebcam() {
        boolean isFound = false;
        webcam = Webcam.getDefault();

        GregorianCalendar calendar = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        webcam.open();
        panel = new WebcamPanel(webcam);

        qrPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        Thread qrCodeThred = new Thread(() -> {
            while (true) {
                this.isFound = false;
                BufferedImage image = webcam.getImage();
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                MultiFormatReader qrBarcodeReade = new MultiFormatReader();

                Result result;
                try {
                    result = qrBarcodeReade.decode(bitmap);
                    if (result != null) {
                        String qrCodeData = result.getText();

                        if (isNumiric(qrCodeData)) {

                            ProductID.setText(qrCodeData);

                            objectFound = find(Integer.parseInt(qrCodeData), "product");
                            if (objectFound == null) {
                                qrSound();

                                ProductID.setText(qrCodeData);

                            } else {
                                qrSound();

                                Product pro = (Product) objectFound;
                                ProductID.setText(String.valueOf(pro.getId()));
                                productName.setText(pro.getName());
                                productCategory.setText(pro.getCategory());
                                ProductBuyingPrice.setText(String.valueOf(pro.getBuyingPrice()));
                                productSellingPrice.setText(String.valueOf(pro.getSellingPrice()));
                                expirationDate.setText(pro.getExpirationDate());
                                numberItem.setText(String.valueOf(pro.getItemNo()));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Not Valid", "Error 404", JOptionPane.ERROR_MESSAGE);
                            ProductID.setText("");
                        }

                    }

                } catch (NotFoundException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        qrCodeThred.start();

    }
    
    private void qrSound() {
        File file = new File("qrSound.wav");

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public NewJFrame() {
        initComponents();
        products = new ArrayList<>();
        clients = new ArrayList<>();
        goldenClients = new ArrayList<>();
        discountProducts  = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        int toDay = calendar.get(Calendar.DAY_OF_MONTH);

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
        int id = Integer.parseInt(oneValues[0].replaceAll(" ", ""));
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
        
        //check expiration
        if(checkDate(expDate, "product")){
            notificationSection.setText(notificationSection.getText() + "\n" +  "The expiry date of "+pro.getName()+" has expired");
        }
    }
        
        //finish reading
        
    }
    reader.close();
  
} catch (IOException ex) {
        System.out.println("Io exeption");

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
        int id = Integer.parseInt(oneValues[0].replaceAll(" ", ""));
        String name = oneValues[1];
        String telephone = oneValues[2];
        String address = oneValues[3];       
        String[] splited =address.split("-");
        String date = oneValues[4];
        
        
        //enter the data as paramiters in the object
        Client client = new Client(id,name,telephone,new Address(Integer.parseInt(splited[2]),splited[1],splited[0]));
        client.setSubDate(date);
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

        try {
            //read the discount list
            reader = new BufferedReader(new FileReader("DiscountList.csv"));
            String line = "";
            
            if((line = reader.readLine()) == null){
                System.out.println("null discount reader");
                
            }else{
                while((line = reader.readLine()) != null){
                    String[] discountParts = line.split(",");
                    int endDay =Integer.parseInt( discountParts[0]);
                    int id = Integer.parseInt(discountParts[1]);
                    double discountPerc = Double.parseDouble(discountParts[2]);
                    Product discountProduct = new Product(endDay, id, discountPerc);
                    discountProducts.add(discountProduct);
                    
                }
                reader.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            
         System.out.println("Arrived");   
         System.out.println(discountProducts);
        //access the discounted products
        for(Product normal: products){
            System.out.println("loop1");
            
            for(Product discountSearch : discountProducts){
                   if(normal.getId() == discountSearch.getId()){
                    if(toDay<discountSearch.getEndDay()){           
                        normal.setSellingPrice(normal.weeklyDiscount(discountSearch.getDiscountPerc()));
                        System.out.println(toDay);
                        System.out.println(discountSearch.getEndDay());
                        System.out.println(normal.getSellingPrice());
                    }
            }
         }
        }
        
        //read golden clients
        
            try {
                reader = new BufferedReader(new FileReader("goldenData.csv"));
                String line = "";
                
                if((line = reader.readLine()) == null){
                    System.out.println("null golden reader");
                }else{
                while((line = reader.readLine())!= null){
                    String[] goldenParts = line.split(",");
                    int id = Integer.parseInt(goldenParts[0]);
                    String name = goldenParts[1];
                    String phone = goldenParts[2];
                    String goldenAddress = goldenParts[3];
                    
                    String[] addressParts = goldenAddress.split("-");
                        
                   
                    String subDate = goldenParts[4];
                    String dateOfBirth = goldenParts[5];
                    String favouritProduct = goldenParts[6];
                    GoldenClient golden = new GoldenClient(id, name, phone,  new Address(Integer.parseInt(addressParts[2]), addressParts[1], addressParts[0]), true,subDate, dateOfBirth, favouritProduct);
                    goldenClients.add(golden);
                }
                reader.close();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        System.out.println("this is the golden data ");
        System.out.println(goldenClients);
    }
    //----------------- gui components ---------------
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
        posText = new javax.swing.JLabel();
        shiftText = new javax.swing.JLabel();
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
        changeCombo = new javax.swing.JComboBox<>();
        readQr = new javax.swing.JButton();
        ClientSubDate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        goldenBirthday = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        goldenFav = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        discountOption = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        notificationSection = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        toGolden = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supermarket");
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
        jLabel3.setText("Telephone number");

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
            .addGap(0, 9, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Staff member:");

        posText.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        posText.setText("Point of sale: 1");

        shiftText.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        shiftText.setText("Shift: 1");

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
        Bill.setText("Date:00/00/2023\nClient: some one\nStaff member: some one\nPoint of sale:000\n----------------------------\n");
        jScrollPane1.setViewportView(Bill);

        javax.swing.GroupLayout qrPanelLayout = new javax.swing.GroupLayout(qrPanel);
        qrPanel.setLayout(qrPanelLayout);
        qrPanelLayout.setHorizontalGroup(
            qrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
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

        changeCombo.setBackground(new java.awt.Color(0, 0, 0));
        changeCombo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        changeCombo.setForeground(new java.awt.Color(255, 255, 255));
        changeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Change shift", "Change point" }));
        changeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeComboActionPerformed(evt);
            }
        });

        readQr.setBackground(new java.awt.Color(0, 0, 0));
        readQr.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        readQr.setForeground(new java.awt.Color(255, 255, 255));
        readQr.setText("Read Qr");
        readQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readQrActionPerformed(evt);
            }
        });

        ClientSubDate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClientSubDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientSubDateActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Subscribtion date");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Date of birth");

        goldenBirthday.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        goldenBirthday.setEnabled(false);
        goldenBirthday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goldenBirthdayActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("Favourite product");

        goldenFav.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        goldenFav.setEnabled(false);
        goldenFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goldenFavActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setText("Discount");

        discountOption.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        discountOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "item for week", "all for day" }));
        discountOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountOptionActionPerformed(evt);
            }
        });

        notificationSection.setColumns(20);
        notificationSection.setRows(5);
        jScrollPane2.setViewportView(notificationSection);

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setText("Notification");

        toGolden.setBackground(new java.awt.Color(0, 0, 0));
        toGolden.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        toGolden.setForeground(new java.awt.Color(255, 255, 255));
        toGolden.setText("to Golden");
        toGolden.setEnabled(false);
        toGolden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toGoldenActionPerformed(evt);
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
                        .addComponent(posText, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shiftText, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(readQr, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(discountOption, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(52, 52, 52)))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientSubDate, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(DeleteClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ClearClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ClientTelephone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(AddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AddressStr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(goldenBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(goldenFav, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toGolden, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(qrPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(productCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(ProductBuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(DeleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clearProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(expirationDate)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(productSellingPrice)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(numberItem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(44, 44, 44)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddToBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(posText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shiftText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(readQr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(ClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(discountOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(toGolden, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ClientTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(AddressStr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ClientSubDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(AddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ClearClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(DeleteClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                                                    .addComponent(numberItem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(goldenBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(goldenFav, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(49, 49, 49))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(AddToBill, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    public void findInGolden(Client golden){
        Object foundClient = null;
        try{
        foundClient = find(golden.getId(),"golden");
        GoldenClient gold = (GoldenClient)foundClient;
            System.out.println(gold.favProduct);
       // gold.displayGolden();
        }catch(Exception ex){
            foundClient = find("golden",golden.getName());
        GoldenClient gold = (GoldenClient)foundClient;
        gold.displayGolden();
        }
        
        
    }
    
    private void FindClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindClientActionPerformed

        Object foundClient = null;
        System.out.println("Button clicked");
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        
        //check the texts are not empty
        if(thereAreEmptyFields("client")){
            JOptionPane.showMessageDialog(null, "The fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
        }
        //starting the search
        else{
            try {
                foundClient = find(Integer.parseInt(ClientId.getText()), "client");
                
            } catch (NumberFormatException e) {
                
                foundClient = find("client", ClientName.getText());
                
            }finally{
                
                if(foundClient == null){
                    JOptionPane.showMessageDialog(null, "Not found! ","Error 404",JOptionPane.ERROR_MESSAGE);
                    clientResult = null;
                }else{
                 clientResult = (Client) foundClient;
          
               
             
                 System.out.println(clientResult.isGolden);
              
                
                   
                    
                   
                
                
                String[] datePart = clientResult.getSubDate().split("/");
                int subYear = Integer.parseInt(datePart[2]);
                if(subYear+5<=year){
                    clientResult.isGolden = true;
                    System.out.println(clientResult.isGolden);
                    goldenBirthday.setEnabled(true);
                    goldenFav.setEnabled(true);
                    toGolden.setEnabled(true);
                }
                
                    if(clientResult.isGolden){
                      //search in golded data
                      try{
                          clientResult.display();
                          foundClient = find(clientResult.getId(),"golden");
                          GoldenClient gold = (GoldenClient)foundClient;
                          gold.displayGolden();
                          
                      }catch(Exception e){
                          System.out.println("Not found in golden data");
                      }
                      
                  }else{
                clientResult.display();
                    }
                }
            }
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
      Client c = new Client(Integer.valueOf(ClientId.getText()),ClientName.getText(),ClientTelephone.getText(),address); 
      c.setSubDate(ClientSubDate.getText());
      clients.add(c);
      
       try {
            writer = new BufferedWriter(new FileWriter("clientsData.csv"));
            writer.write(("id,name,phone,address\n")+String.valueOf(clients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ","));
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
        ClientSubDate.setText("");
        goldenBirthday.setText("");
        goldenFav.setText("");
        goldenBirthday.setEnabled(false);
        goldenFav.setEnabled(false);
        toGolden.setEnabled(false);
        
        
        
    }//GEN-LAST:event_ClearClientActionPerformed

    private void DeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteClientActionPerformed
    
        if(ClientId.getText().isEmpty() || ClientName.getText().isEmpty() || ClientTelephone.getText().isEmpty() || AddressStr.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "There are empty fields!","Error 404", JOptionPane.ERROR_MESSAGE);
        }else{
            int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to permanently delete this record?","Warning",JOptionPane.YES_NO_OPTION);
          
            if(option == JOptionPane.YES_OPTION){
                
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
        
        GregorianCalendar calendar = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int endDay = 0;
        double perc = 0;
        Object foundProduct = null;
        System.out.println("Button clicked");
        //check the texts are not empty
        
        if(thereAreEmptyFields("product")){
            JOptionPane.showMessageDialog(null, "The fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
        }//search
        else{
            try {
            
                foundProduct = find(Integer.parseInt(ProductID.getText()), "product");
                
            } catch (NumberFormatException e) {
                
                foundProduct = find("product", productName.getText());
                
            }finally{
                
                if(foundProduct == null){
                    JOptionPane.showMessageDialog(null, "Not found! ","Error 404",JOptionPane.ERROR_MESSAGE);
                }else{ 
                productResult = (Product) foundProduct;
                productResult.display();
              
                }
            }
        }
    }//GEN-LAST:event_FindNumberActionPerformed

    
    private void AddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProductActionPerformed

        if(ProductID.getText().isEmpty()|| productName.getText().isEmpty()|| productCategory.getText().isEmpty() || ProductBuyingPrice.getText().isEmpty() || productSellingPrice.getText().isEmpty() || expirationDate.getText().isEmpty() || numberItem.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "The data fields are empty","Error 404",JOptionPane.ERROR_MESSAGE);
            
        }else{
        /*get the data from the texts then add the data to the Arraylist*
        
        */
       Product pro = new Product(Integer.parseInt(ProductID.getText()),productName.getText(),productCategory.getText()
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
            writer.write(("id,name,category,buying,selling,expiration,number\n")+String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
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

    public boolean thereAreEmptyFields(String identfier){
        if("product".equals(identfier)){
            if(ProductID.getText().isEmpty() && 
                    productName.getText().isEmpty() &&
                    productCategory.getText().isEmpty() &&
                    ProductBuyingPrice.getText().isEmpty() &&
                    productSellingPrice.getText().isEmpty() &&
                    expirationDate.getText().isEmpty() &&
                    numberItem.getText().isEmpty()){
                return true;
                
            }else if("client".equals(identfier)){
                if(ClientId.getText().isEmpty() ||
                        ClientName.getText().isEmpty() ||
                        ClientTelephone.getText().isEmpty() ||
                        AddressStr.getText().isEmpty()){
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        
        return true;      
    }
    private void DeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteProductActionPerformed

        if(thereAreEmptyFields("product")){
            JOptionPane.showMessageDialog(null, "There are empty fields!","Error 404",JOptionPane.ERROR_MESSAGE);
        }else{
            //find the product
            for (Product proSearch : products) {
                System.out.println("objectiv not found");
                if(ProductID.getText().equals(proSearch.getId())){
                    int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to permanently delete this record","Warrning",JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        products.remove(proSearch);
                        System.out.println(products);
                       
                        try {
                            writer = new BufferedWriter(new FileWriter("productData.csv"));
                            writer.write(("id,name,category,buying,selling,expiration,itemNo\n")+String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                
                           
                            ProductID.setText("");
                            productName.setText("");
                            productCategory.setText("");
                            ProductBuyingPrice.setText("");
                            productSellingPrice.setText("");
                            expirationDate.setText("");
                            numberItem.setText("");
                            System.out.println("data deleted");
                            writer.close();
                        } catch (IOException ex) {
                            
                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
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

    private void readQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readQrActionPerformed
       if(readQr.getText().equals("Read Qr")){
        initWebcam(); 
        readQr.setText("Stop reading");
        Color red = new Color(255,51,0);
        readQr.setBackground(red);
        
       }else{
           readQr.setText("Read Qr");
           webcam.close();
           readQr.setBackground(Color.BLACK);
       }
    }//GEN-LAST:event_readQrActionPerformed

   
    private void changeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeComboActionPerformed
       
        String value  = changeCombo.getSelectedItem().toString();
        
        switch(value){
            case "Change shift":
                 if(shiftCounter< 3){
                 shiftCounter++;
                 System.out.println("if caled");
                 System.out.println(shiftCounter);
                 shiftText.setText("Shift: "+shiftCounter);
                }else{
                     System.out.println("else caled");
                     shiftCounter = 1;
                     System.out.println(shiftCounter);
                     shiftText.setText("Shift: "+shiftCounter);
         
                }
                break;
            case "Change point":
                if(pointCounter< 5){
                 pointCounter++;
                 System.out.println("if caled");
                 System.out.println(pointCounter);
                 posText.setText("Point of sale: "+pointCounter);
                }else{
                     System.out.println("else caled");
                     pointCounter = 1;
                     System.out.println(pointCounter);
                     posText.setText("Point of sale: "+pointCounter);
         
                }
                break;
                
            }
    }//GEN-LAST:event_changeComboActionPerformed

    private void ClientSubDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientSubDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientSubDateActionPerformed

    private void goldenBirthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goldenBirthdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goldenBirthdayActionPerformed

    private void goldenFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goldenFavActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goldenFavActionPerformed

    public void dayDiscount(double per){
        
        
        for(Product pro: products){
            double newPrice = pro.weeklyDiscount(per);
            pro.setSellingPrice(newPrice);
        }
        
         try {
                writer = new BufferedWriter(new FileWriter("productData.csv"));
                writer.write(("id,name,category,buying,selling,expiration,number\n")+String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\|", ","));
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    private void discountOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountOptionActionPerformed
        String value = discountOption.getSelectedItem().toString();
      
        Object foundProduct = null;
       
        //get the day
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

     
       
        
        switch(value){
            case "item for week":
                String msgValue = JOptionPane.showInputDialog(null, "Enter the discount percentage: ","Discount", JOptionPane.INFORMATION_MESSAGE);
                foundProduct = find(Integer.parseInt(ProductID.getText()), "product");
                Product item = (Product) foundProduct;
                item.setSellingPrice(item.weeklyDiscount(Double.parseDouble(msgValue)));
                productSellingPrice.setText(String.valueOf(item.getSellingPrice()));
                
           
                try {
                    File file = new File("DiscountList.csv");
                    writer = new BufferedWriter(new FileWriter(file,true));
                    if(file.length() == 0){
                        writer.write("endDay,id,discPerc"+"\n"+String.valueOf(day+6)+","+String.valueOf(item.getId())+","+msgValue);
                        
                    }else{
                   
                    writer.write("\n"+String.valueOf(day+6)+","+String.valueOf(item.getId())+","+msgValue);
                    
                    }
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                
               
                break;



            case "all for day":
                //second choice function
                String msgValue2 = JOptionPane.showInputDialog(null, "Enter the discount percentage: ","Discount", JOptionPane.INFORMATION_MESSAGE);
                for(Product p1 :products){
                    p1.setSellingPrice(p1.weeklyDiscount(Double.parseDouble(msgValue2)));
                try {
                    File file = new File("DiscountList.csv");
                    writer = new BufferedWriter(new FileWriter(file,true));
                    if(file.length() == 0){
                        writer.write("endDay,id,discPerc"+"\n"+String.valueOf(day+1)+","+String.valueOf(p1.getId())+","+msgValue2);
                        
                    }else{
                   
                    writer.write("\n"+String.valueOf(day+1)+","+String.valueOf(p1.getId())+","+msgValue2);
                    
                    }
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                
                   
                }
                break;

                
               
                
        }
    }//GEN-LAST:event_discountOptionActionPerformed

    private void toGoldenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toGoldenActionPerformed
        if(thereAreEmptyFields("client"))
        {
            JOptionPane.showMessageDialog(null, "There are empty fields!", "Error 404", JOptionPane.ERROR_MESSAGE);
        }
        else if(goldenBirthday.getText().isEmpty() || goldenFav.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "please enter a date of birth and a favorite product", "Error 404", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            clientResult.toGolden();
        }
    }//GEN-LAST:event_toGoldenActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddClient;
    private javax.swing.JButton AddProduct;
    private javax.swing.JButton AddToBill;
    public static javax.swing.JTextField AddressStr;
    private javax.swing.JTextArea Bill;
    private javax.swing.JButton ClearClient;
    public static javax.swing.JTextField ClientId;
    public static javax.swing.JTextField ClientName;
    public static javax.swing.JTextField ClientSubDate;
    public static javax.swing.JTextField ClientTelephone;
    private javax.swing.JButton DeleteClient;
    private javax.swing.JButton DeleteProduct;
    private javax.swing.JButton FindClient;
    private javax.swing.JButton FindNumber;
    public static javax.swing.JTextField ProductBuyingPrice;
    public static javax.swing.JTextField ProductID;
    private javax.swing.JComboBox<String> changeCombo;
    private javax.swing.JButton clearProduct;
    private javax.swing.JComboBox<String> discountOption;
    public static javax.swing.JTextField expirationDate;
    public static javax.swing.JTextField goldenBirthday;
    public static javax.swing.JTextField goldenFav;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notificationSection;
    public static javax.swing.JTextField numberItem;
    private javax.swing.JLabel posText;
    public static javax.swing.JTextField productCategory;
    public static javax.swing.JTextField productName;
    public static javax.swing.JTextField productSellingPrice;
    private javax.swing.JPanel qrPanel;
    private javax.swing.JButton readQr;
    private javax.swing.JLabel shiftText;
    private javax.swing.JButton toGolden;
    // End of variables declaration//GEN-END:variables
}
