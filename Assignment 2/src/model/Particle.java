package model;

import java.util.HashSet;
import java.util.Set;

public class Particle {
  public Particle(double mass, double speedX, double speedY, double x, double y){
    this.mass = mass;
    this.speedX = speedX;
    this.speedY = speedY;
    this.x = x;
    this.y = y;
  }
  public Set<Particle> impacting=new HashSet<Particle>();
  public double mass;
  public double speedX;
  public double speedY;
  public double x;
  public double y;
  public void move(){
    x+=speedX/(AbstractModel.timeFrame);
    y+=speedY/(AbstractModel.timeFrame);
    //uncomment the following to have particle bouncing on the boundary
    //if(this.x<0){this.speedX*=-1;}
    //if(this.y<0){this.speedY*=-1;}
    //if(this.x>AbstractModel.size){this.speedX*=-1;}
    //if(this.y>AbstractModel.size){this.speedY*=-1;}
  }
  public  boolean isImpact(double dist,double otherMass){
    if(Double.isNaN(dist)){return true;}
    double distMass=Math.sqrt(mass)+Math.sqrt(otherMass);
    if(dist<distMass*distMass){return true;}
    return false;
  }
  public  boolean isImpact(Iterable<Particle> ps){
    for(Particle p:ps){
      if(this==p){continue;}
      double dist=distance2(p);
      if(isImpact(dist,p.mass)){return true;}
    }
    return false;
  }
  public double distance2(Particle p){
    double distX=this.x-p.x;
    double distY=this.y-p.y;
    return distX*distX+distY*distY;
  }
  public void interact(AbstractModel m){
    for(Particle p:m.p){
      if(p==this) {continue;}
      double dirX=-Math.signum(this.x-p.x);
      double dirY=-Math.signum(this.y-p.y);
      double dist=distance2(p);//this is already distance^2
      if(isImpact(dist,p.mass)){this.impacting.add(p);continue;}
      dirX=p.mass*AbstractModel.gravitationalConstant*dirX/(dist*AbstractModel.timeFrame);
      dirY=p.mass*AbstractModel.gravitationalConstant*dirY/(dist*AbstractModel.timeFrame);
      assert this.speedX<=AbstractModel.lightSpeed:this.speedX;
      assert this.speedY<=AbstractModel.lightSpeed:this.speedY;
      double newSpeedX=this.speedX+dirX;
      newSpeedX/=(1+(this.speedX*dirX)/AbstractModel.lightSpeed);
      double newSpeedY=this.speedY+dirY;
      newSpeedY/=(1+(this.speedY*dirY)/AbstractModel.lightSpeed);
      if(!Double.isNaN(dirX)){this.speedX=newSpeedX;}
      if(!Double.isNaN(dirY)){this.speedY=newSpeedY;}
    }
  }

  /**
   * Used for testing purposes.
   * @return
   */
  public double getMass(){
    return this.mass;
  }

  public double getSpeedX(){
    return this.speedX;
  }

  public double getSpeedY(){
    return this.speedY;
  }

  public double getX(){
    return this.x;
  }

  public double getY(){
    return this.y;
  }


}