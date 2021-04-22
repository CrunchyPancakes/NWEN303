package tests;

import datasets.DataSetLoader;
import model.AbstractModel;
import org.junit.jupiter.api.Test;

public class TestPerformance {
  private int warmUp = 100; // amount of steps to take to warm up before timing
  private int steps = 10000; // number of steps to take in a test simulation

  @Test
  void testRegularGridPerformance(){
    System.out.println("Testing for a regular grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRegularGrid(500, 1500, 30);
    AbstractModel par = DataSetLoader.getRegularGridParallel(500, 1500, 30);
    // warm up the simulations
    System.gc(); // expend extra effort to recycle unused objects
    for(int i=0;i<warmUp;i++){
      seq.step();
      par.step();
    }
    // variables to keep track of time
    long start, end;
    // run the sequential simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
        seq.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Sequential simulation took: "+(end-start)/1000d +" seconds\n");
    // run the parallel simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      par.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Parallel simulation took: "+(end-start)/1000d +" seconds\n");
  }

  @Test
  void testRandomGridPerformance(){
    System.out.println("Testing for a random grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomGrid(500, 1500, 30);
    AbstractModel par = DataSetLoader.getRandomGridParallel(500, 1500, 30);
    // warm up the simulations
    System.gc(); // expend extra effort to recycle unused objects
    for(int i=0;i<warmUp;i++){
      seq.step();
      par.step();
    }
    // variables to keep track of time
    long start, end;
    // run the sequential simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      seq.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Sequential simulation took: "+(end-start)/1000d +" seconds\n");
    // run the parallel simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      par.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Parallel simulation took: "+(end-start)/1000d +" seconds\n");
  }

  @Test
  void testRandomSetPerformance(){
    System.out.println("Testing for a random set...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomSet(500, 1500, 50);
    AbstractModel par = DataSetLoader.getRandomSetParallel(500, 1500, 50);
    // warm up the simulations
    System.gc(); // expend extra effort to recycle unused objects
    for(int i=0;i<warmUp;i++){
      seq.step();
      par.step();
    }
    // variables to keep track of time
    long start, end;
    // run the sequential simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      seq.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Sequential simulation took: "+(end-start)/1000d +" seconds\n");
    // run the parallel simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      par.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Parallel simulation took: "+(end-start)/1000d +" seconds\n");
  }

  @Test
  void testRandomRotatingGridPerformance(){
    System.out.println("Testing for a random rotating grid...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getRandomRotatingGrid(0.02d,500, 1500, 30);
    AbstractModel par = DataSetLoader.getRandomRotatingGridParallel(0.02d,500, 1500, 30);
    // warm up the simulations
    System.gc(); // expend extra effort to recycle unused objects
    for(int i=0;i<warmUp;i++){
      seq.step();
      par.step();
    }
    // variables to keep track of time
    long start, end;
    // run the sequential simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      seq.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Sequential simulation took: "+(end-start)/1000d +" seconds\n");
    // run the parallel simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      par.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Parallel simulation took: "+(end-start)/1000d +" seconds\n");
  }

  @Test
  void testElaboratePerformance(){
    System.out.println("Testing for an elaborate...\n");
    // create sequential and parallel versions of the models
    AbstractModel seq = DataSetLoader.getElaborate(500, 1500, 30,0.99005);
    AbstractModel par = DataSetLoader.getElaborateParallel(500, 1500, 30,0.99005);
    // warm up the simulations
    System.gc(); // expend extra effort to recycle unused objects
    for(int i=0;i<warmUp;i++){
      seq.step();
      par.step();
    }
    // variables to keep track of time
    long start, end;
    // run the sequential simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      seq.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Sequential simulation took: "+(end-start)/1000d +" seconds\n");
    // run the parallel simulation
    start = System.currentTimeMillis();
    for(int i = 0; i < steps; i++){
      par.step();
    }
    end = System.currentTimeMillis();
    System.out.println("Parallel simulation took: "+(end-start)/1000d +" seconds\n");
  }
}
