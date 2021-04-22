package tests;

import datasets.DataSetLoader;
import model.AbstractModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCorrectness {

  private int steps = 1000; // number of steps to take in a test simulation


  @Test
  void testRegularGridCorrectness(){
    System.out.println("Testing for a regular grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRegularGrid(500, 1500, 30);
    AbstractModel par = DataSetLoader.getRegularGridParallel(500, 1500, 30);
    // run the simulation
    for(int i = 0; i < steps; i++){
      // check the number of particles is both are same BEFORE stepping
      assertEquals(seq.p.size(), par.p.size());
      par.step();
      seq.step();
      // check the number of particles is both the same AFTER stepping
      assertEquals(seq.p.size(),par.p.size());
      for(int j = 0; j < seq.p.size(); j++){
        // check each particle's fields are the same in each model
        assertEquals(seq.p.get(j).getMass(), par.p.get(j).getMass());
        assertEquals(seq.p.get(j).getSpeedX(), par.p.get(j).getSpeedX());
        assertEquals(seq.p.get(j).getSpeedY(), par.p.get(j).getSpeedY());
        assertEquals(seq.p.get(j).getX(), par.p.get(j).getX());
        assertEquals(seq.p.get(j).getY(), par.p.get(j).getY());
      }
    }
  }

  @Test
  void testRandomGridCorrectness(){
    System.out.println("Testing for a random grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomGrid(100, 800, 30);
    AbstractModel par = DataSetLoader.getRandomGridParallel(100, 800, 30);
    // run the simulation
    for(int i = 0; i < steps; i++){
      // check the number of particles is both are same BEFORE stepping
      assertEquals(seq.p.size(), par.p.size());
      par.step();
      seq.step();
      // check the number of particles is both the same AFTER stepping
      assertEquals(seq.p.size(),par.p.size());
      for(int j = 0; j < seq.p.size(); j++){
        // check each particle's fields are the same in each model
        assertEquals(seq.p.get(j).getMass(), par.p.get(j).getMass());
        assertEquals(seq.p.get(j).getSpeedX(), par.p.get(j).getSpeedX());
        assertEquals(seq.p.get(j).getSpeedY(), par.p.get(j).getSpeedY());
        assertEquals(seq.p.get(j).getX(), par.p.get(j).getX());
        assertEquals(seq.p.get(j).getY(), par.p.get(j).getY());
      }
    }
  }

  @Test
  void testRandomSetCorrectness(){
    System.out.println("Testing for a random set...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomSet(100, 800, 100);
    AbstractModel par = DataSetLoader.getRandomSetParallel(100, 800, 100);
    // run the simulation
    for(int i = 0; i < steps; i++){
      // check the number of particles is both are same BEFORE stepping
      assertEquals(seq.p.size(), par.p.size());
      par.step();
      seq.step();
      // check the number of particles is both the same AFTER stepping
      assertEquals(seq.p.size(),par.p.size());
      for(int j = 0; j < seq.p.size(); j++){
        // check each particle's fields are the same in each model
        assertEquals(seq.p.get(j).getMass(), par.p.get(j).getMass());
        assertEquals(seq.p.get(j).getSpeedX(), par.p.get(j).getSpeedX());
        assertEquals(seq.p.get(j).getSpeedY(), par.p.get(j).getSpeedY());
        assertEquals(seq.p.get(j).getX(), par.p.get(j).getX());
        assertEquals(seq.p.get(j).getY(), par.p.get(j).getY());
      }
    }
  }

  @Test
  void testRandomRotatingGridCorrectness(){
    System.out.println("Testing for a random rotating grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomRotatingGrid(0.02d,100, 800, 40);
    AbstractModel par = DataSetLoader.getRandomRotatingGridParallel(0.02d,100, 800, 40);
    // run the simulation
    for(int i = 0; i < steps; i++){
      // check the number of particles is both are same BEFORE stepping
      assertEquals(seq.p.size(), par.p.size());
      par.step();
      seq.step();
      // check the number of particles is both the same AFTER stepping
      assertEquals(seq.p.size(),par.p.size());
      for(int j = 0; j < seq.p.size(); j++){
        // check each particle's fields are the same in each model
        assertEquals(seq.p.get(j).getMass(), par.p.get(j).getMass());
        assertEquals(seq.p.get(j).getSpeedX(), par.p.get(j).getSpeedX());
        assertEquals(seq.p.get(j).getSpeedY(), par.p.get(j).getSpeedY());
        assertEquals(seq.p.get(j).getX(), par.p.get(j).getX());
        assertEquals(seq.p.get(j).getY(), par.p.get(j).getY());
      }
    }
  }

  @Test
  void testElaborateCorrectness(){
    System.out.println("Testing for an elaborate...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getElaborate(200, 700, 2,0.99005);
    AbstractModel par = DataSetLoader.getElaborateParallel(200, 700, 2,0.99005);
    // run the simulation
    for(int i = 0; i < steps; i++){
      // check the number of particles is both are same BEFORE stepping
      assertEquals(seq.p.size(), par.p.size());
      par.step();
      seq.step();
      // check the number of particles is both the same AFTER stepping
      assertEquals(seq.p.size(),par.p.size());
      for(int j = 0; j < seq.p.size(); j++){
        // check each particle's fields are the same in each model
        assertEquals(seq.p.get(j).getMass(), par.p.get(j).getMass());
        assertEquals(seq.p.get(j).getSpeedX(), par.p.get(j).getSpeedX());
        assertEquals(seq.p.get(j).getSpeedY(), par.p.get(j).getSpeedY());
        assertEquals(seq.p.get(j).getX(), par.p.get(j).getX());
        assertEquals(seq.p.get(j).getY(), par.p.get(j).getY());
      }
    }
  }

}
