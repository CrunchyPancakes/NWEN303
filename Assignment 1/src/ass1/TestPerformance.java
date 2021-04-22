package ass1;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Had assistance from the following for understanding the TestPerformance class:
 *
 * "Warm up the code before start measuring the performance":
 * https://stackoverflow.com/questions/15126887/warm-up-the-code-before-start-measuring-the-performance
 *
 * "How do I write a correct micro-benchmark in Java?":
 * https://stackoverflow.com/questions/504103/how-do-i-write-a-correct-micro-benchmark-in-java
 *
 * An alternative to testing in the way that TestPerformance has been written is to use
 * Thread.sleep(), as this is better to test if parallel code is working properly.
 *
 */
public class TestPerformance {

  /**
   * Measures how long in milliseconds that a Runnable takes to run. The method has a
   * 'warm up' stage in so that algorithm can run a few thousand times before we start
   * measuring how long it takes for real. This is necessary to get the JIT correctly
   * optimise the algorithm (so all initialisations and compilations have been triggered).
   * @param r The runnable that we are measuring the run time of.
   * @param warmUp The number of warm up iterations to run.
   * @param runs The number of iterations to run and time the length of.
   * @return
   */
  long timeOf(Runnable r,int warmUp,int runs) {
    System.gc();
    for(int i=0;i<warmUp;i++) {r.run();}
    long time0=System.currentTimeMillis();
    for(int i=0;i<runs;i++) {r.run();}
    long time1=System.currentTimeMillis();
    return time1-time0;
  }

  /**
   * Takes a sorter implementation and sorts a dataset. The method measures how long
   * it takes for the sorter to sort the list using the timeOf() method. The method
   * then prints out the name of the implementation, and the time in seconds it took
   * to run.
   * @param s A sorter implementation used to sort a dataset.
   * @param name The name of the sort implementation used.
   * @param dataset The dataset to sort.
   * @param <T>
   */
  <T extends Comparable<? super T>>void msg(Sorter s,String name,T[][] dataset) {
    long time=timeOf(()->{
      for(T[]l:dataset){s.sort(Arrays.asList(l));}
      },20000,200);//realistically 20.000 to make the JIT do his job..
      System.out.println(name+" sort takes "+time/1000d+" seconds");
    }

  /**
   * Takes a dataset and calls the msg() method using each implementation of Sorter.
   * The method produces an output for how long each implementation of Sorter takes
   * to run (using MSequentialSorter(), MParallelSorter1(), MParallelSorter2(),
   * and MParallelSorter3()).
   * @param dataset A dataset to be sorted.
   * @param <T>
   */
  <T extends Comparable<? super T>>void msgAll(T[][] dataset) {
    //msg(new ISequentialSorter(),"Sequential insertion",TestBigInteger.dataset);//so slow
    //uncomment the former line to include performance of ISequentialSorter
    msg(new MSequentialSorter(),"Sequential merge sort",dataset);
    msg(new MParallelSorter1(),"Parallel merge sort (futures)",dataset);
    msg(new MParallelSorter2(),"Parallel merge sort (completablefutures)",dataset);
    msg(new MParallelSorter3(),"Parallel merge sort (forkJoin)",dataset);
    }
  @Test
  void testBigInteger() {
    System.out.println("On the data type BigInteger");
    msgAll(TestBigInteger.dataset);
    }
  @Test
  void testFloat() {
    System.out.println("On the data type Float");
    msgAll(TestFloat.dataset);
    }
  @Test
  void testPoint() {
    System.out.println("On the data type Point");
    msgAll(TestPoint.dataset);
    }
  @Test
  void testString() {
    System.out.println("On the data type String");
    msgAll(TestString.dataset);
  }
}
/*
With the model solutions, on a lab machine we may get those results:
On the data type Float
Sequential merge sort sort takes 1.178 seconds
Parallel merge sort (futures) sort takes 0.609 seconds
Parallel merge sort (completablefutures) sort takes 0.403 seconds
Parallel merge sort (forkJoin) sort takes 0.363 seconds
On the data type Point
Sequential merge sort sort takes 1.373 seconds
Parallel merge sort (futures) sort takes 0.754 seconds
Parallel merge sort (completablefutures) sort takes 0.541 seconds
Parallel merge sort (forkJoin) sort takes 0.48 seconds
On the data type BigInteger
Sequential merge sort sort takes 1.339 seconds
Parallel merge sort (futures) sort takes 0.702 seconds
Parallel merge sort (completablefutures) sort takes 0.452 seconds
Parallel merge sort (forkJoin) sort takes 0.492 seconds
*/