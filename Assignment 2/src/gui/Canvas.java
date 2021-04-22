package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.AbstractModel;
import model.DrawableParticle;
import model.Model;

@SuppressWarnings("serial")
public class Canvas extends JPanel{
  AbstractModel m; Canvas(AbstractModel m){this.m=m;}
  @Override public void paint(Graphics gg){
	super.paint(gg);
    Graphics2D g=(Graphics2D)gg;
    g.setBackground(Color.DARK_GRAY);
    g.clearRect(0, 0, getWidth(),getHeight());
    for(DrawableParticle p: m.pDraw){p.draw(g);}
    Toolkit.getDefaultToolkit().sync();
  }
  @Override public Dimension getPreferredSize(){
    return new Dimension((int)AbstractModel.size, (int)AbstractModel.size);
    }
}
