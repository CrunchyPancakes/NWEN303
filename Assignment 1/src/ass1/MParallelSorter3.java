package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class MParallelSorter3 implements Sorter {
  // Thread pool for using Futures
  private static final ForkJoinPool pool = new ForkJoinPool();

  /**
   * Sorts a list by splitting it in half, and merging the halves while recursively calling sort()
   * on each half.
   * Uses Futures to fork the merge-sort by making the 'left' half of the list a Future.
   * @param <T>
   * @param list The list to be sorted using merge sort.
   * @return
   */
  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list){
    return pool.invoke(new ForkJoinSorter<T>(list));
  }
}