/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

/**
 *
 * @author zeesh
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;
import static java.sql.DriverManager.println;

import javax.imageio.ImageIO;

import javax.swing.*;

public class ImageHiding extends JFrame implements ActionListener
{
 BufferedImage hostImage;
 BufferedImage secretImage;

 JPanel controlPanel;
 JPanel imagePanel;

 JTextField encodeBitsText;
 JButton encodeBitsPlus;
 JButton encodeBitsMinus;
JRadioButton rb1;
JRadioButton rb2;
JRadioButton rb3;
JRadioButton rb4;

static ButtonGroup group = new ButtonGroup();



 JTextField nBitsText;
 JButton nBitsPlus;
 JButton nBitsMinus;

 ImageCanvas hostCanvas;
 ImageCanvas secretCanvas;

 Steganography s,s1;

 public BufferedImage getHostImage()
 {
  BufferedImage img = null;

  try
  {
   img = ImageIO.read(new File("host_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }

  return img;
 }

 public BufferedImage getSecretImage()
 {
  BufferedImage img = null;

  try
  {
   img = ImageIO.read(new File("secret_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }

  return img;
 }

 public int getBits()
 {
  return Integer.parseInt(encodeBitsText.getText());
 }

 public void actionPerformed(ActionEvent event)
 {
  Object source = event.getSource();

  if (source == encodeBitsPlus)
  {
    //  
             int bits = this.getBits() + 1;
             if (bits > 8) { bits = 8; }
             encodeBitsText.setText(Integer.toString(bits));

             s = new Steganography(this.getHostImage());
             s1 = new Steganography(this.getSecretImage());
             if("ml".equals(group.getSelection().getActionCommand()))
             {
             s.encodeml(this.getSecretImage(), bits); 
             s1.getMaskedImageml(bits);
             }
             else
             if("mm".equals(group.getSelection().getActionCommand()))
             {
             s.encodemm(this.getSecretImage(), bits); 
             s1.getMaskedImagemm(bits);
             }
             else
             if("lm".equals(group.getSelection().getActionCommand()))
             {
             s.encodelm(this.getSecretImage(), bits); 
             s1.getMaskedImagelm(bits);
             }
             else
             if("ll".equals(group.getSelection().getActionCommand()))
             {
             s.encodell(this.getSecretImage(), bits); 
             s1.getMaskedImagell(bits);
             }
             
             hostCanvas.setImage(s.getImage());
             hostCanvas.repaint();     
             secretCanvas.setImage(s1.getImage());
             secretCanvas.repaint();
             
  }
  else if (source == encodeBitsMinus)
  {
   int bits = this.getBits() - 1;

   if (bits < 0) { bits = 0; }

   encodeBitsText.setText(Integer.toString(bits));

   s = new Steganography(this.getHostImage());
   s1 = new Steganography(this.getSecretImage());
   
    if("ml".equals(group.getSelection().getActionCommand()))
             {
             s.encodeml(this.getSecretImage(), bits); 
             s1.getMaskedImageml(bits);
             }
             else
             if("mm".equals(group.getSelection().getActionCommand()))
             {
             s.encodemm(this.getSecretImage(), bits); 
             s1.getMaskedImagemm(bits);
             }
             else
             if("lm".equals(group.getSelection().getActionCommand()))
             {
             s.encodelm(this.getSecretImage(), bits); 
             s1.getMaskedImagelm(bits);
             }
             else
             if("ll".equals(group.getSelection().getActionCommand()))
             {
             s.encodell(this.getSecretImage(), bits); 
             s1.getMaskedImagell(bits);
             }
             
        hostCanvas.setImage(s.getImage());
        hostCanvas.repaint();   
        secretCanvas.setImage(s1.getImage());
        secretCanvas.repaint();
        
  }
 }

 public ImageHiding()
 {
  GridBagLayout layout = new GridBagLayout();
  GridBagConstraints gbc = new GridBagConstraints();
  this.setTitle("Image Hiding Demo");

  Container container = this.getContentPane();

  this.setLayout(layout);

  this.add(new JLabel("Bits to encode into host image:"));

  encodeBitsText = new JTextField("0", 5);
  encodeBitsText.setEditable(false);

  gbc.weightx = -1.0;
  layout.setConstraints(encodeBitsText, gbc);
  this.add(encodeBitsText);

  encodeBitsPlus = new JButton("+");
  encodeBitsPlus.addActionListener(this);
  
  
  
  encodeBitsMinus = new JButton("-");
  encodeBitsMinus.addActionListener(this);

  gbc.weightx = 1.0;
  layout.setConstraints(encodeBitsPlus, gbc);
  this.add(encodeBitsPlus);

  rb1 = new JRadioButton("MSB TO LSB");
  rb1.addActionListener(this);
  rb1.setSelected(true);
  rb1.setActionCommand("ml");
          
  gbc.weightx = 1.0;
  layout.setConstraints(rb1, gbc);
  this.add(rb1);
  
    rb2 = new JRadioButton("MSB TO MSB");
  rb2.addActionListener(this);
  rb2.setActionCommand("mm");
  
  gbc.weightx = 1.0;
  layout.setConstraints(rb2, gbc);
  this.add(rb2);
  
    rb3 = new JRadioButton("LSB TO LSB");
  rb3.addActionListener(this);
  rb3.setActionCommand("ll");
  
  gbc.weightx = 1.0;
  layout.setConstraints(rb3, gbc);
  this.add(rb3);
  
    rb4 = new JRadioButton("LSB TO MSB");
  rb4.addActionListener(this);
  rb4.setActionCommand("lm");
  
  group.add(rb1);
group.add(rb2);
group.add(rb3);
group.add(rb4);

  gbc.weightx = 1.0;
  layout.setConstraints(rb4, gbc);
  this.add(rb4);
  
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(encodeBitsMinus, gbc);
  this.add(encodeBitsMinus);

  GridBagLayout imageGridbag = new GridBagLayout();
  GridBagConstraints imageGBC = new GridBagConstraints();

  imagePanel = new JPanel();
  imagePanel.setLayout(imageGridbag);

  JLabel hostImageLabel = new JLabel("Host image:");
  JLabel secretImageLabel = new JLabel("Secret image:");

  imagePanel.add(hostImageLabel);

  imageGBC.gridwidth = GridBagConstraints.REMAINDER;
  imageGridbag.setConstraints(secretImageLabel, imageGBC);
  imagePanel.add(secretImageLabel);

  hostCanvas = new ImageCanvas(this.getHostImage());  
  secretCanvas = new ImageCanvas(this.getSecretImage());

  imagePanel.add(hostCanvas);
  imagePanel.add(secretCanvas);

  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(imagePanel, gbc);
  this.add(imagePanel);

//  Steganography host = new Steganography(this.getHostImage());
//  host.encode(this.getSecretImage(), this.getBits());
//  hostCanvas.setImage(host.getImage());
//
//  Steganography secret = new Steganography(this.getSecretImage());
//  secret.getMaskedImage(this.getBits());
//  secretCanvas.setImage(secret.getImage());

  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.pack();

  this.setVisible(true);
 }

 public static void main(String[] args)
 {
  ImageHiding frame = new ImageHiding();
  frame.setVisible(true);
  println(group.getSelection().toString());
 }

 public class ImageCanvas extends JPanel
 { 
  Image img;

  public void paintComponent(Graphics g)
  {
   g.drawImage(img, 0, 0, this);
  }

  public void setImage(Image img)
  {
   this.img = img;
  }

  public ImageCanvas(Image img)
  {
   this.img = img;

   this.setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
  }
 }
}

class Steganography
{
 BufferedImage image;

 public void getMaskedImageml(int bits)
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

  for (int i = 0; i < imageRGB.length; i++)
  {
   imageRGB[i] = imageRGB[i] & mask;
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

 public void encodeml(BufferedImage encodeImage, int encodeBits)
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

  public void getMaskedImagemm(int bits)
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

  for (int i = 0; i < imageRGB.length; i++)
  {
   imageRGB[i] = imageRGB[i] & mask;
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

 public void encodemm(BufferedImage encodeImage, int encodeBits)
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }
 
  public void getMaskedImagelm(int bits)
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

  for (int i = 0; i < imageRGB.length; i++)
  {
   imageRGB[i] = imageRGB[i] & mask;
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

 public void encodelm(BufferedImage encodeImage, int encodeBits)
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }
 
  public void getMaskedImagell(int bits)
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

  for (int i = 0; i < imageRGB.length; i++)
  {
   imageRGB[i] = imageRGB[i] & mask;
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

 public void encodell(BufferedImage encodeImage, int encodeBits)
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }
 
 public Image getImage()
 {
  return image;
 }

 public Steganography(BufferedImage image)
 {
  this.image = image;
 }
}