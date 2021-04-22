package ass1;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *  The benefit of using ForkJoin is that it is better at handling larger datasets (because it is parallel), it makes
 *  use of work-stealing (like CompletableFutures), and it is good for naturally recursive tasks. Being good at
 *  naturally recursive tasks is convenient for us as merge-sort is generally implemented using a recursive algorithm.
 *  We also maintain control over the detail of how operations are executed (like Futures) while having some structure
 *  (like using a ParallelStream). I've learnt that having a good understand of generics is important when coding with
 *  RecursiveTask, as the main problem I had when implementing the algorithm using ForkJoin were issues to do with
 *  recognising the generic type.
 */
public class MParallelSorter3 implements Sorter {
  // Thread pool for using Futures
  private static final ForkJoinPool pool = new ForkJoinPool();

  /**
   * Uses a ForkJoinSorter which makes use of the ForkJoin library to do merge-sort in parallel.
   * @param <T>
   * @param list The list to be sorted using merge sort.
   * @return
   */
  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list){
    return pool.invoke(new ForkJoinSorter<T>(list));
  }
}