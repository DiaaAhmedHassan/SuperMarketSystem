

package com.mycompany.supermarket;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import static com.mycompany.supermarket.StaffForm.staffMembers;
import static com.mycompany.supermarket.StaffForm.writer;
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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;





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
    public static GoldenClient gold = null;
    public static Staff member = null;
    //------------------ lists ---------------------
    public static ArrayList<Product> products;
    public static ArrayList<Client> clients;
    public static ArrayList<GoldenClient> goldenClients;
    public static ArrayList<Product> discountProducts;
    
    //----------- counters ---------------
    private int shiftCounter = 1;
    private int pointCounter = 1;
    private double tottalPrice = 0.0;
    
   
    
    
    //------------------ methods -------------
    
    //check the strings if it's numeric
    public static boolean isNumeric(String string){
        try{
            Integer.parseInt(string);
            return true;
        }catch(NumberFormatException ex){
                return false;
             }
    }
    
    
    //This method is used in find process
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
                System.out.println("searching for a staff member by code");
                name = name.toLowerCase().replaceAll(" ", "");
                for(Staff staff : StaffForm.staffMembers)
                {  
                  String code =  staff.getCard().getCode();
                    System.out.println(code);
                    if(name.equals(code))
                    {
                        returned = staff;
                        System.out.println(staff.toString());
                        break;
                    }
                }
                
            break;
               case "staffName":
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
   
    //overload for the find method to find by id
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
    
    
    //this method is used to compare between current date and another date
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
            case "product": //2022 sy< 2023 cy ex
                 if(year>stringYear){ //false
                  return true;
                }else if(year == stringYear){
                    if(month>stringMonth){
                        return true;
                    }else if(day>=stringDay){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
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
    
   
    //this method used to show bill defaults
    public void showBillDefs(){
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH)+1;
        
        String date = calendar.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+calendar.get(Calendar.YEAR);
        bill.setText("Date: "+date+",\n"+staffName.getText()+",\n"+posText.getText()+",\n"+shiftText.getText()+",\n---------------------------");
        
    }
    
    
    //using webcam and read the qr code
    private void initWebcam() {
     
        boolean isFound = false;
        
        
      webcam = Webcam.getDefault();

        GregorianCalendar calendar = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        webcam.open();
        panel = new WebcamPanel(webcam);

        qrPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1031, 83, 226, 66));
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

                        if (isNumeric(qrCodeData)) {

                            ProductID.setText(qrCodeData);

                            objectFound = find(Integer.parseInt(qrCodeData), "product");
                            if (objectFound == null) {
                                qrSound();

                                ProductID.setText(qrCodeData);

                            } else {
                                qrSound();

                                TimeUnit.SECONDS.sleep(1);
                                Product pro = (Product) objectFound;
                                ProductID.setText(String.valueOf(pro.getId()));
                                productName.setText(pro.getName());
                                productCategory.setText(pro.getCategory());
                                ProductBuyingPrice.setText(String.valueOf(pro.getBuyingPrice()));
                                productSellingPrice.setText(String.valueOf(pro.getSellingPrice()));
                                expirationDate.setText(pro.getExpirationDate());
                                numberItem.setText(String.valueOf(pro.getItemNo()));
                                addToBill();
                                productResult = pro;
                                productResult.decreaseItemNo(Integer.parseInt(itemsNumber.getText()));
                                numberItem.setText(String.valueOf(productResult.getItemNo()));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Not Valid", "Error 404", JOptionPane.ERROR_MESSAGE);
                            ProductID.setText("");
                        }

                    }

                } catch (NotFoundException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        qrCodeThred.start();

    }
    
    
    //creat sound for the qr reading
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
    
    //used to know who is the staff member working in this shift and point of sale
    public void showStaffName(){
        Object foundStaff = null;
        
        String pointNumber = String.valueOf(posText.getText().charAt(15));
        String shiftNumber = String.valueOf(shiftText.getText().charAt(7));
        String card ="p"+ pointNumber+"s"+shiftNumber; //p1s1
        try{
        foundStaff = find("staff",card);
        member = (Staff) foundStaff;
        staffName.setText("Staff member: "+member.getCard().getUsername());
        }catch(Exception e){
            staffName.setText("Staff member: None");
            
        }
        
        
        
        
        System.out.println(card);
        //find the code of the card
        
        System.out.println(pointNumber+" "+shiftNumber);
    }
    
    public NewJFrame() {
        initComponents();
        products = new ArrayList<>();
        clients = new ArrayList<>();
        goldenClients = new ArrayList<>();
        discountProducts  = new ArrayList<>();
        StaffForm.staffMembers = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        int toDay = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(qrPanel.getBounds());
       

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
        //line = "4545,Milk,Diary,23.0,28.0,4/7/2024,10000"
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
        if(pro.getItemNo()<=10){
            notificationSection.setText(notificationSection.getText()+"\n"+pro.getName()+" stock is running out");
            
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
                        //set selling price with 
                        normal.setSellingPrice(normal.Discount(discountSearch.getDiscountPerc()));
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
        
        
        StaffForm.staffReload();
        showStaffName();
         showBillDefs();
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
        staffName = new javax.swing.JLabel();
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
        bill = new javax.swing.JTextArea();
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
        tottalLabel = new javax.swing.JLabel();
        itemsNumber = new javax.swing.JTextField();
        tottalLabel1 = new javax.swing.JLabel();
        AddToBill1 = new javax.swing.JButton();
        updateClient = new javax.swing.JButton();
        updateProduct = new javax.swing.JButton();

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
        ClientId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClientIdKeyPressed(evt);
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
        ClientName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClientNameKeyPressed(evt);
            }
        });

        ClientTelephone.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ClientTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientTelephoneActionPerformed(evt);
            }
        });
        ClientTelephone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClientTelephoneKeyPressed(evt);
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
        AddressStr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressStrKeyPressed(evt);
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
        FindClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FindClientKeyPressed(evt);
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
        ClearClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClearClientKeyPressed(evt);
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

        staffName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        staffName.setText("Staff member:");

        posText.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        posText.setText("Point of sale: 1");

        shiftText.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        shiftText.setText("Shift: 1");

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Mange staff members");
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
        ProductID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProductIDKeyPressed(evt);
            }
        });

        productName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        productName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameActionPerformed(evt);
            }
        });
        productName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productNameKeyPressed(evt);
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
        productCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productCategoryKeyPressed(evt);
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
        ProductBuyingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProductBuyingPriceKeyPressed(evt);
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
        productSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productSellingPriceKeyPressed(evt);
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
        expirationDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                expirationDateKeyPressed(evt);
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
        numberItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numberItemKeyPressed(evt);
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
        FindNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FindNumberKeyPressed(evt);
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
        AddProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddProductKeyPressed(evt);
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
        DeleteProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeleteProductKeyPressed(evt);
            }
        });

        bill.setColumns(20);
        bill.setRows(5);
        bill.setTabSize(4);
        bill.setText("Date:00/00/2023\nClient: some one\nStaff member: some one\nshifht: 1\nPoint of sale:000\n----------------------------\n");
        jScrollPane1.setViewportView(bill);

        javax.swing.GroupLayout qrPanelLayout = new javax.swing.GroupLayout(qrPanel);
        qrPanel.setLayout(qrPanelLayout);
        qrPanelLayout.setHorizontalGroup(
            qrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
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
        ClientSubDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClientSubDateKeyPressed(evt);
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
        goldenBirthday.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                goldenBirthdayKeyPressed(evt);
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
        goldenFav.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                goldenFavKeyPressed(evt);
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
            .addGap(0, 0, Short.MAX_VALUE)
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
        toGolden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                toGoldenKeyPressed(evt);
            }
        });

        tottalLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tottalLabel.setText("Tottal: 0.0");
        tottalLabel.setToolTipText("");

        itemsNumber.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        itemsNumber.setText("1");
        itemsNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsNumberActionPerformed(evt);
            }
        });
        itemsNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemsNumberKeyPressed(evt);
            }
        });

        tottalLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tottalLabel1.setText("Number of items");
        tottalLabel1.setToolTipText("");

        AddToBill1.setBackground(new java.awt.Color(0, 255, 51));
        AddToBill1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        AddToBill1.setForeground(new java.awt.Color(0, 0, 0));
        AddToBill1.setText("Finish");
        AddToBill1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToBill1ActionPerformed(evt);
            }
        });

        updateClient.setBackground(new java.awt.Color(0, 0, 0));
        updateClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        updateClient.setForeground(new java.awt.Color(255, 255, 255));
        updateClient.setText("Update");
        updateClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateClientActionPerformed(evt);
            }
        });
        updateClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                updateClientKeyPressed(evt);
            }
        });

        updateProduct.setBackground(new java.awt.Color(0, 0, 0));
        updateProduct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        updateProduct.setForeground(new java.awt.Color(255, 255, 255));
        updateProduct.setText("Update");
        updateProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProductActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientSubDate, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ClearClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ClientTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddressStr)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(goldenBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(goldenFav, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toGolden, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(qrPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(61, 61, 61))
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
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(clearProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(updateProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(expirationDate)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(productSellingPrice)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(numberItem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(44, 44, 44))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(staffName, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(posText, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shiftText, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(readQr, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tottalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tottalLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddToBill, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(itemsNumber)
                            .addComponent(AddToBill1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(staffName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(posText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shiftText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(readQr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tottalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tottalLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemsNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(AddToBill, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddToBill1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(qrPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FindNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(clearProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                                    .addComponent(AddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(updateClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ClearClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DeleteClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)))))
                        .addContainerGap())
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientIdActionPerformed
        
        
    }//GEN-LAST:event_ClientIdActionPerformed

    private void ClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientNameActionPerformed
        ClientName.setNextFocusableComponent(ClientTelephone);
        
    }//GEN-LAST:event_ClientNameActionPerformed

    private void ClientTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientTelephoneActionPerformed
        ClientTelephone.setNextFocusableComponent(AddressStr);
    }//GEN-LAST:event_ClientTelephoneActionPerformed

    //used to find the client if it's exist in the golden clients file
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
                    return;
                }else{
                 clientResult = (Client) foundClient;
          
               
             
                 System.out.println(clientResult.isGolden);
              
                
                   
                    
                   
                
                //check if the golden is passed 5 years subscription and display bill with discount
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
                          gold = (GoldenClient)foundClient;
                          gold.displayGolden();
                          toGolden.setEnabled(false);
                          
                      }catch(Exception e){
                          System.out.println("Not found in golden data");
                      }
                      
                  }else{
                clientResult.display();
                    }
                }
            }
        }
        
        //show the name of the client in the bill
        bill.setText(bill.getText()+"\nClient: "+clientResult.getName()+"\n---------------------------");
        
    }//GEN-LAST:event_FindClientActionPerformed

    private void AddClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddClientActionPerformed
     if(ClientId.getText().isEmpty()|| ClientName.getText().isEmpty()|| ClientTelephone.getText().isEmpty() || AddressStr.getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "The data fieleds are empty","Error 404",JOptionPane.ERROR_MESSAGE);
         
        }else{   
      String ad=AddressStr.getText();
      String[]parts=ad.split("-");
      Address address = new Address(Integer.parseInt(parts[0]),parts[1],parts[2]);
      Client c = new Client(Integer.valueOf(ClientId.getText()),ClientName.getText(),ClientTelephone.getText(),address); 
      c.setSubDate(ClientSubDate.getText());
      clients.add(c);
      
         writeToFiles("clients");
    }//GEN-LAST:event_AddClientActionPerformed
    }
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
        showBillDefs();
        
        
        
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
                if(productResult.getItemNo()<=10){
                    JOptionPane.showMessageDialog(null, productResult.getName()+" stock is running out","Alert",JOptionPane.INFORMATION_MESSAGE);
                }
              
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
        
       
          
            writeToFiles("product");
            System.out.println("Added to file succefuly");
            ProductID.setText("");
            productName.setText("");
            productCategory.setText("");
            ProductBuyingPrice.setText("");
            productSellingPrice.setText("");
            expirationDate.setText("");
            numberItem.setText("");
           
           //catch the expetion
       
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

    //add the product to bill
    
    public void addToBill(){
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        double price = Double.parseDouble(itemsNumber.getText())*Double.parseDouble(productSellingPrice.getText());
         if(itemsNumber.getText().isEmpty()){
            itemsNumber.setText("1");
        }
            //golden case
        /*10% for birth day 
        20% for favourite product
        2% for golden clients*/
        //check for golden clients
        if(clientResult.isGolden){
            //10% birth day
            String birthPart[] = goldenBirthday.getText().split("/");
            int birthday = Integer.parseInt(birthPart[0]);
            int birthMonth = Integer.parseInt(birthPart[1]);
            if(birthday == day && birthMonth == month){
                     if(!thereAreEmptyFields("product")){
                         price -= price*(10/100.0);            
            }
                     //20% fav product
        }else if(goldenFav.getText().toLowerCase().replaceAll(" ", "").equals(productName.getText().toLowerCase().replaceAll(" ", ""))){ //fav product
                if(!thereAreEmptyFields("product")){
                    price -= price*(20/100.0);              
            } 
                
            }else{ //2% golden client
                 if(!thereAreEmptyFields("product")){
                    price -= price*(2/100.0);           
            } 
        }
            
        }else{

        if(!thereAreEmptyFields("product")){
        bill.setText(bill.getText()+"\n"+itemsNumber.getText()+" "+productName.getText()+"\t"+price);          
        }
        }
        
            bill.setText(bill.getText()+"\n"+itemsNumber.getText()+" "+productName.getText()+"\t"+price);
            tottalPrice += price;
            System.out.println(tottalPrice);
            tottalLabel.setText("Tottal: "+String.valueOf(tottalPrice));
       
    
    }
    
    private void AddToBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToBillActionPerformed
        addToBill();
        productResult.decreaseItemNo(Integer.parseInt(itemsNumber.getText()));
        numberItem.setText(String.valueOf(productResult.getItemNo()));
        
        writeToFiles("products");
    }//GEN-LAST:event_AddToBillActionPerformed

    private void AddressStrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressStrActionPerformed
       AddressStr.setNextFocusableComponent(ClientSubDate);
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
        showStaffName();
        showBillDefs();
    }//GEN-LAST:event_changeComboActionPerformed

    private void ClientSubDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientSubDateActionPerformed
       
        ClientSubDate.setNextFocusableComponent(goldenBirthday);
       
    }//GEN-LAST:event_ClientSubDateActionPerformed

    private void goldenBirthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goldenBirthdayActionPerformed
       goldenBirthday.setNextFocusableComponent(goldenFav);
    }//GEN-LAST:event_goldenBirthdayActionPerformed

    private void goldenFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goldenFavActionPerformed
        goldenFav.setNextFocusableComponent(toGolden);
    }//GEN-LAST:event_goldenFavActionPerformed

    public void dayDiscount(double per){
        
        
        for(Product pro: products){
            double newPrice = pro.Discount(per);
            pro.setSellingPrice(newPrice);
        }
        
        writeToFiles("products");
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
                item.setSellingPrice(item.Discount(Double.parseDouble(msgValue)));
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
                    p1.setSellingPrice(p1.Discount(Double.parseDouble(msgValue2)));
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

    private void ClientIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClientIdKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ClientName.requestFocus();
        }
    }//GEN-LAST:event_ClientIdKeyPressed

    private void ClientNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClientNameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ClientTelephone.requestFocus();
        }
    }//GEN-LAST:event_ClientNameKeyPressed

    private void ClientTelephoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClientTelephoneKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println("enter presed");
            AddressStr.requestFocus();
        }
    }//GEN-LAST:event_ClientTelephoneKeyPressed

    private void AddressStrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressStrKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ClientSubDate.requestFocus();
        }
    }//GEN-LAST:event_AddressStrKeyPressed

    private void ClientSubDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClientSubDateKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            goldenBirthday.requestFocus();
        }
    }//GEN-LAST:event_ClientSubDateKeyPressed

    private void goldenBirthdayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_goldenBirthdayKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            goldenFav.requestFocus();
        }
    }//GEN-LAST:event_goldenBirthdayKeyPressed

    private void goldenFavKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_goldenFavKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            toGolden.requestFocus();
        }
    }//GEN-LAST:event_goldenFavKeyPressed

    private void toGoldenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toGoldenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            AddClient.requestFocus();
        }
    }//GEN-LAST:event_toGoldenKeyPressed

    private void ClearClientKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClearClientKeyPressed
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            FindClient.requestFocus();
            }         

    }//GEN-LAST:event_ClearClientKeyPressed

    private void FindClientKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FindClientKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            DeleteClient.requestFocus();
        }
    }//GEN-LAST:event_FindClientKeyPressed

    private void ProductIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductIDKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            productName.requestFocus();
        }
    }//GEN-LAST:event_ProductIDKeyPressed

    private void productNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productNameKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            productCategory.requestFocus();
        }
    }//GEN-LAST:event_productNameKeyPressed

    private void productCategoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productCategoryKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ProductBuyingPrice.requestFocus();
        }
    }//GEN-LAST:event_productCategoryKeyPressed

    private void ProductBuyingPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductBuyingPriceKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            productSellingPrice.requestFocus();
        }
        }
    }//GEN-LAST:event_ProductBuyingPriceKeyPressed

    private void productSellingPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productSellingPriceKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            expirationDate.requestFocus();
        }
    }//GEN-LAST:event_productSellingPriceKeyPressed

    private void expirationDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expirationDateKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            numberItem.requestFocus();
        }
    }//GEN-LAST:event_expirationDateKeyPressed

    private void numberItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberItemKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            AddProduct.requestFocus();
        }
    }//GEN-LAST:event_numberItemKeyPressed

    private void AddProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddProductKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           FindNumber.requestFocus();
        }
    }//GEN-LAST:event_AddProductKeyPressed

    private void FindNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FindNumberKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            clearProduct.requestFocus();
        }
    }//GEN-LAST:event_FindNumberKeyPressed

    private void DeleteProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeleteProductKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            DeleteProduct.requestFocus();
        }
    }//GEN-LAST:event_DeleteProductKeyPressed

    private void itemsNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemsNumberActionPerformed

    private void itemsNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemsNumberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemsNumberKeyPressed

    private void AddToBill1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToBill1ActionPerformed
        showBillDefs();
        tottalLabel.setText("Tottal: 0.0");
        itemsNumber.setText("1");
        tottalPrice = 0.0;
        //reset the bill
      
        
        
    }//GEN-LAST:event_AddToBill1ActionPerformed
  
    //method used to write data to the files
    public static void writeToFiles(String key) {
       
      try { 
      switch(key){
           //write product to file of the products
           case "product":
                writer = new BufferedWriter(new FileWriter("productData.csv"));
                writer.write("id,name,category,buying,selling,expiration,number\n"+String.valueOf(products).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ","));
                writer.close();
                   break;
           //write clients to client file        
           case "clients":
               writer = new BufferedWriter(new FileWriter("clientsData.csv"));
               writer.write(("id,name,phone,address,subdate\n")+String.valueOf(clients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ","));
               writer.close();
               break;
           case "golden":
              writer = new BufferedWriter(new FileWriter("goldenData.csv"));
              writer.write(("id,name,phone,address, subscribtion date, birthday, favorite product\n")+String.valueOf(goldenClients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ",").replaceFirst(" ", ""));
              writer.close();
           case "staff":
               writer = new BufferedWriter(new FileWriter("StaffMembers.csv"));
               writer.write(("id,name,phone,age,address,salary,cardId,username\n")+String.valueOf(staffMembers).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|",",").replaceAll(" ", ""));
               writer.close();
               break;
               

       }
       } catch (IOException ex) {
                   Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
               }
      
      //try to write the new product to the csv file
       
            /*
            remove all unused simbols to write the data in the file like that
           4545,Milk,diary,20.0,28.0,4-8-2024
           7241,chease,diary,35.0,40.0,4-9-2024
            
            instate of writing it like that
            [4545/Milk/diary/20.0/28.0/4-8-2024
            , 7241/chease/diary/35.0/40.0/4-9-2024
            ]
            bec these formate will be hard for csv file to read]
            */
    }
    private void updateClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateClientActionPerformed

        //update client with new data
        
        if(!clientResult.isGolden){
        clientResult.setId(Integer.parseInt(ClientId.getText()));
        clientResult.setName(ClientName.getText());
        clientResult.setTelephone(ClientTelephone.getText());
        String addressParts[]= AddressStr.getText().split("-");
        Address address = new Address(Integer.parseInt(addressParts[2]), addressParts[1], addressParts[0]);
        clientResult.setAdress(address);
        clientResult.setSubDate(ClientSubDate.getText());
        clients.set(clients.indexOf(clientResult), clientResult);
        writeToFiles("clients");
        }else{
        gold.setId(Integer.parseInt(ClientId.getText()));
        gold.setName(ClientName.getText());
        gold.setTelephone(ClientTelephone.getText());
        String addressParts[]= AddressStr.getText().split("-");
        Address address = new Address(Integer.parseInt(addressParts[2]), addressParts[1], addressParts[0]);
        gold.setAdress(address);
        gold.setSubDate(ClientSubDate.getText());
        gold.setBirthday(goldenBirthday.getText());
        gold.setFavProduct(goldenFav.getText());
        goldenClients.set(clients.indexOf(clientResult), gold);
        
            writeToFiles("golden");
        }
        System.out.println("");
    }//GEN-LAST:event_updateClientActionPerformed

    private void updateClientKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updateClientKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateClientKeyPressed

    private void updateProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProductActionPerformed
        productResult.setId(Integer.parseInt(ProductID.getText()));
        productResult.setName(productName.getText());
        productResult.setCategory(productCategory.getText());
        productResult.setBuyingPrice(Double.parseDouble(ProductBuyingPrice.getText()));
        productResult.setSellingPrice(Double.parseDouble(productSellingPrice.getText()));
        productResult.setExpirationDate(expirationDate.getText());
        productResult.setItemNo(Integer.parseInt(numberItem.getText()));
        products.set(products.indexOf(productResult), productResult);
        writeToFiles("product");
        
        
       
    }//GEN-LAST:event_updateProductActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddClient;
    private javax.swing.JButton AddProduct;
    private javax.swing.JButton AddToBill;
    private javax.swing.JButton AddToBill1;
    public static javax.swing.JTextField AddressStr;
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
    private javax.swing.JTextArea bill;
    private javax.swing.JComboBox<String> changeCombo;
    private javax.swing.JButton clearProduct;
    private javax.swing.JComboBox<String> discountOption;
    public static javax.swing.JTextField expirationDate;
    public static javax.swing.JTextField goldenBirthday;
    public static javax.swing.JTextField goldenFav;
    public static javax.swing.JTextField itemsNumber;
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
    private javax.swing.JLabel staffName;
    private javax.swing.JButton toGolden;
    private javax.swing.JLabel tottalLabel;
    private javax.swing.JLabel tottalLabel1;
    private javax.swing.JButton updateClient;
    private javax.swing.JButton updateProduct;
    // End of variables declaration//GEN-END:variables

  
}
