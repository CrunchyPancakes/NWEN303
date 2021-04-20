package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModelParallel extends Model {
  public static final double size=900;//just for the canvas
  public static final double gravitationalConstant=0.001;
  public static final double lightSpeed=10;//the smaller, the larger is the chunk of universe we simulate
  public static final double timeFrame=0.1;//the bigger, the shorter is the time of a step, the slower but more precise the simulation
  public List<Particle> p=new ArrayList<Particle>();
  public volatile List<DrawableParticle> pDraw=new ArrayList<DrawableParticle>();
  // Thread pool for using Futures
  //TODO CHANGE THIS TO USE A MORE EFFICIENT POOL
  private static final ExecutorService pool = Executors.newCachedThreadPool();
  public void step() {
    for(Particle p:this.p){p.interact(this);}
    for(Particle p:this.p){p.move();}
//    moveInParallel(); // uncomment to use parallel method
    mergeParticles();
    updateGraphicalRepresentation();
  }
  private void updateGraphicalRepresentation() {
    ArrayList<DrawableParticle> d=new ArrayList<DrawableParticle>();
    Color c=Color.ORANGE;
    for(Particle p:this.p){
      d.add(new DrawableParticle((int)p.x, (int)p.y, (int)Math.sqrt(p.mass),c ));
    }
    this.pDraw=d;//atomic update
  }
  public void mergeParticles(){
    Stack<Particle> deadPs=new Stack<Particle>();
    for(Particle p:this.p){ if(!p.impacting.isEmpty()){deadPs.add(p);}; }
    this.p.removeAll(deadPs);
    while(!deadPs.isEmpty()){
      Particle current=deadPs.pop();
      Set<Particle> ps=getSingleChunck(current);
      deadPs.removeAll(ps);
      this.p.add(mergeParticles(ps));
    }
  }
  private Set<Particle> getSingleChunck(Particle current) {
    Set<Particle> impacting=new HashSet<Particle>();
    impacting.add(current);
    while(true){
      Set<Particle> tmp=new HashSet<Particle>();
      for(Particle pi:impacting){tmp.addAll(pi.impacting);}
      boolean changed=impacting.addAll(tmp);
      if(!changed){break;}
    }
    //now impacting have all the chunk of collapsing particles
    return impacting;
  }
  public Particle mergeParticles(Set<Particle> ps){
    double speedX=0;
    double speedY=0;
    double x=0;
    double y=0;
    double mass=0;
    for(Particle p:ps){
      mass+=p.mass;
      x+=p.x*p.mass;
      y+=p.y*p.mass;
      speedX+=p.speedX*p.mass;
      speedY+=p.speedY*p.mass;
    }
    x/=mass;
    y/=mass;
    speedX/=mass;
    speedY/=mass;
    return new Particle(mass,speedX,speedY,x,y);
  }

  /**
   * Moves all the particles in the model in parallel.
   * Helper method.
   */
  public void moveInParallel(){

    // TODO MAKE SO IT COPIES THE p LIST RATHER THAN USE IT DIRECTLY

    // if there are less than 20 particles, then move sequentially
    if(p.size() < 20){
      for(Particle p:this.p){p.move();}
      return;
    }

    // Divide and Conquer algorithm.
    // Find the mid point of the particles list for splitting purposes.
    int mid = p.size()/2;
    // Fork each half of the list.
    Future<List<Particle>> left = pool.submit(() -> moveInParallel(p.subList(0,mid)));

  }

  /**
   * Moves all the particles in the model in parallel.
   * Recursive method.
   * @param particles
   * @return
   */
  public List<Particle> moveInParallel(List<Particle> particles){

    return null;

  }
}