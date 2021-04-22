package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 *  The benefit of using CompletableFutures is that it is better at handling larger datasets (because it is parallel)
 *  and it makes use of work-stealing. By using work stealing, the algorithm becomes non-blocking because 'workers' are
 *  able to take 'tasks' from the other workers queues if a worker no longer has tasks in its own queue. This means that
 *  merge() does not block when we use the thenCombine() method to pass the left and right halves of the list into
 *  merge(). CompletableFutures can also be 'manually' completed, although that hasn't been used in this case. I've
 *  learnt that using a ThreadPool isn't necessary for CompletableFutures as this is handled automatically and it
 *  actually made my algorithm perform slower (my initial implementation of the CompletableFutures algorithm used a
 *  ThreadPool that I created).
 */
public class MParallelSorter2 implements Sorter {
  // Threshold to determine when to delegate to a sequential sorter
  private static final int threshold = 20;

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
    // Copy the list data into a new list.
    List<T> listCopy = new ArrayList<>(list);

    // There are 0 or 1 elements in the list, no need to sort
    if(listCopy.size() < 2){
      return listCopy;
    }

    // Delegate to MSequentialSorter for cases with elements less than the threshold
    if(listCopy.size() < threshold){
      MSequentialSorter sequentialSorter = new MSequentialSorter();
      return sequentialSorter.sort(listCopy);
    }

    // Find the mid point of the list for splitting purposes
    int mid = listCopy.size()/2;


    // Fork each half of the merge-sort.
    // Recursively calls sort() on the 'left' and 'right' halves.
    CompletableFuture<List<T>> left = CompletableFuture.supplyAsync(() -> sort(listCopy.subList(0,mid)));
    CompletableFuture<List<T>> right = CompletableFuture.supplyAsync(() -> sort(listCopy.subList(mid, listCopy.size())));
    CompletableFuture<List<T>> merged = left.thenCombine(right, (l,r) -> {
      return merge(l,r);
    });

    // Return the 2 halves of a list merged together.
    return merged.join();
  }

  /**
   * Takes two halves of a list (left and right) and merges them into one list while maintaining
   * ordering.
   * @param left
   * @param right
   * @param <T>
   * @return
   */
  public <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right){
    // Keep track of where in each list we are up to.
    int leftIndex = 0;
    int rightIndex = 0;
    // New list to return.
    List<T> merged = new ArrayList<>();

    // Loop until all of the left and right halves have been sorted and merged into a list.
    while (leftIndex < left.size() && rightIndex < right.size()) {
      if (left.get(leftIndex).compareTo(right.get(rightIndex)) < 0) {
        merged.add(left.get(leftIndex++));
      } else {
        merged.add(right.get(rightIndex++));
      }
    }

    // If for some reason there are elements left in each half, add them to the merged list.
    merged.addAll(left.subList(leftIndex, left.size()));
    merged.addAll(right.subList(rightIndex, right.size()));
    return merged;
  }
}