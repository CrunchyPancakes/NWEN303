package gui;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import datasets.DataSetLoader;
import model.AbstractModel;
import model.Model;
@SuppressWarnings("serial")
public class Gui extends JFrame implements Runnable{
  private static int minTime=20;//use a bigger or smaller number for faster/slower simulation top speed
  //it will attempt to do a step every 20 milliseconds (less if the machine is too slow)

  public static final ScheduledThreadPoolExecutor schedulerRepaint = new ScheduledThreadPoolExecutor(1);
  public static final ScheduledThreadPoolExecutor schedulerSimulation = new ScheduledThreadPoolExecutor(1);
  AbstractModel m;
  Gui(AbstractModel m){this.m=m;}
  public void run() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    getRootPane().setLayout(new BorderLayout());
    JPanel p=new Canvas(m);
    p.setDoubleBuffered(true);
    getRootPane().add(p,BorderLayout.CENTER);
    pack();
    setVisible(true);
    schedulerRepaint.scheduleAtFixedRate(()->{
      if(!schedulerRepaint.getQueue().isEmpty()){System.out.println("Skipping a frame");return;}//still repainting
      try {SwingUtilities.invokeAndWait(()->repaint());}//You may want to explain why this is needed
      catch (InvocationTargetException | InterruptedException e) {//not a perfect solution, but
        e.printStackTrace();//makes sure you see the error and the program dies.
        System.exit(0);//the "right" solution is much more involved
	    }//and would require storing and passing the exception between different threads objects.
      },
      500,5, TimeUnit.MILLISECONDS
      );
    }
  private static final class MainLoop implements Runnable {
    AbstractModel m;MainLoop(AbstractModel m){this.m=m;}
    public void run() {
      try{
        while(true){
          long ut=System.currentTimeMillis();
          m.step();
          ut=System.currentTimeMillis()-ut;//used time
          //System.out.println("Particles: "+m.p.size()+" time:"+ut);//if you want to have an idea of the time consumption
          long sleepTime=minTime-ut;
          if(sleepTime>1){ Thread.sleep(sleepTime);}
          }//if the step was short enough, it wait to make it at least minTime long.
        }
      catch(Throwable t){//not a perfect solution, but
        t.printStackTrace();//makes sure you see the error and the program dies.
        System.exit(0);//the "right" solution is much more involved
        }//and would require storing and passing the exception between different threads objects.
      }
    }
  public static void main(String[] args) {
    //AbstractModel m=f//Try those configurations
//    AbstractModel m=DataSetLoader.getRandomRotatingGrid(0.02d,100, 800, 40);
    //AbstractModel m=DataSetLoader.getRandomRotatingGrid(0.02d,100, 800, 30);
//    AbstractModel m=DataSetLoader.getElaborateParallel(200, 700, 2,0.99);
    //AbstractModel m=DataSetLoader.getElaborate(200, 700, 2,0.99005);
    //AbstractModel m=DataSetLoader.getElaborate(200, 700, 2,0.99008);
    //AbstractModel m=DataSetLoader.getRandomSet(100, 800, 1000);
    //AbstractModel m=DataSetLoader.getRandomSet(100, 800, 100);
    //AbstractModel m=DataSetLoader.getRandomGrid(100, 800, 30);
//    AbstractModel m = DataSetLoader.getRegularGrid(500, 1500, 30);
    AbstractModel m = DataSetLoader.getRegularGridParallel(500, 1500, 30);
    schedulerSimulation.schedule(new MainLoop(m), 500, TimeUnit.MILLISECONDS);
    SwingUtilities.invokeLater(new Gui(m));

    }
  }