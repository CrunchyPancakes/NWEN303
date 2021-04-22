package model;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ModelParallel extends AbstractModel {

  private int threshold = 50; // Threshold to determine whether to use parallel or sequential algorithms.

  @Override
  public void step() {
    interactParticles();
    moveParticles();
    mergeParticles();
    updateGraphicalRepresentation();
  }

  /**
   * Interacts all the particles in the model.
   * Decides whether to intetact sequentially or in parallel.
   */
  public void interactParticles(){
    // if there are less than 30 particles, then interact sequentially.
    // else, interact the particles in parallel.
    if(this.p.size() < threshold){
      for(Particle p:this.p){p.interact(this);}
    } else{
      this.p.parallelStream().forEach(p -> p.interact(this));
    }
  }


  /**
   * Moves all the particles in the model.
   * Decides whether to move sequentially or in parallel.
   */
  public void moveParticles(){
    // if there are less than 30 particles, then move sequentially.
    // else, move the particles in parallel.
    if(this.p.size() < threshold){
      for(Particle p:this.p){p.move();}
    } else{
      this.p.parallelStream().forEach(p -> p.move());
    }
  }

  /**
   * Personalised get method for Futures.
   * Based on method from lecture 4.
   * @param f
   * @param <T>
   * @return
   */
  public static <T> List<T> get(Future<List<T>>f) {
    try {return f.get();}
    catch (InterruptedException e) {//we do not expect it
      Thread.currentThread().interrupt();//just do it :(
      throw new Error(e);//turn it into an error
    }
    catch (ExecutionException e) {
      Throwable t=e.getCause();//propagate unchecked exceptions
      if(t instanceof RuntimeException) {
        throw (RuntimeException)t;
      }//note: CancellationException is a RuntimeException
      if(t instanceof Error) {
        throw (Error)t;
      }
      throw new Error("Unexpected Checked Exception",t);
      //our callable/closure did throw a checked exception
    }
  }
}
