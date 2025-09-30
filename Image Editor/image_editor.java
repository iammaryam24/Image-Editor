// ImageEditor.java
import javax.swing.*; import java.awt.*; import java.awt.image.*; import java.io.*; import javax.imageio.*;
public class ImageEditor {
    static BufferedImage img;
    static JLabel lbl = new JLabel();
    public static BufferedImage load(File f) throws Exception { return ImageIO.read(f); }
    public static BufferedImage toGray(BufferedImage b) {
        BufferedImage r = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int y=0;y<b.getHeight();y++) for(int x=0;x<b.getWidth();x++){
            int p=b.getRGB(x,y); int a=(p>>24)&0xff, rC=(p>>16)&0xff, g=(p>>8)&0xff, bl=p&0xff;
            int gray=(rC+g+bl)/3; int np=(a<<24)|(gray<<16)|(gray<<8)|gray; r.setRGB(x,y,np);
        } return r;
    }
    public static BufferedImage invert(BufferedImage b) {
        for(int y=0;y<b.getHeight();y++) for(int x=0;x<b.getWidth();x++){
            int p=b.getRGB(x,y); int a=(p>>24)&0xff, rc=255-((p>>16)&0xff), g=255-((p>>8)&0xff), bl=255-(p&0xff);
            b.setRGB(x,y,(a<<24)|(rc<<16)|(g<<8)|bl);
        } return b;
    }
    public static void main(String[] args) throws Exception {
        JFrame f=new JFrame("ImageEditor"); JButton loadBtn=new JButton("Load"), gray=new JButton("Gray"), inv=new JButton("Invert");
        JPanel p=new JPanel(); p.add(loadBtn); p.add(gray); p.add(inv); f.add(p, BorderLayout.NORTH); f.add(new JScrollPane(lbl), BorderLayout.CENTER);
        loadBtn.addActionListener(e->{
            try{ JFileChooser fc=new JFileChooser(); if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){ img=load(fc.getSelectedFile()); lbl.setIcon(new ImageIcon(img)); f.pack(); }}
            catch(Exception ex){ ex.printStackTrace(); }
        });
        gray.addActionListener(e->{ if(img!=null){ img=toGray(img); lbl.setIcon(new ImageIcon(img)); f.pack(); }});
        inv.addActionListener(e->{ if(img!=null){ img=invert(img); lbl.setIcon(new ImageIcon(img)); f.pack(); }});
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.pack(); f.setVisible(true);
    }
}
