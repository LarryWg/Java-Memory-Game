package src.main;

import javax.swing.SwingUtilities;

public class MemoryTest{
  
  public static void main(String[] args){
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        new Menu();
      }
    });
  }
}
