package model;

public class Model extends AbstractModel {

  @Override
  public void step() {
    for(Particle p:this.p){p.interact(this);}
    for(Particle p:this.p){p.move();}
    mergeParticles();
    updateGraphicalRepresentation();
  }
}
