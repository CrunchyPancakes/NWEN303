package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MParallelSorter1 implements Sorter {
  // Thread pool for using Futures
  private static final ExecutorService pool = Executors.newCachedThreadPool();

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

    // Delegate to MSequentialSorter for cases with less than 20 elements
    if(listCopy.size() < 20){
      MSequentialSorter sequentialSorter = new MSequentialSorter();
      return sequentialSorter.sort(listCopy);
    }

    // Find the mid point of the list for splitting purposes
    int mid = listCopy.size()/2;

    // Fork each half of the merge-sort.
    // Recursively calls sort() on the 'left' and 'right' halves.
    Future<List<T>> left = pool.submit(() -> sort(listCopy.subList(0,mid)));
    List<T> right = sort(listCopy.subList(mid,listCopy.size()));

    // Return the 2 halves of a list merged together.
    return merge(get(left), right);
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

  /**
   * Personalised get method for Futures.
   * Based on method from lecture 4.
   * @param f
   * @param <T>
   * @return
   */
  public static <T extends Comparable<? super T>> List<T> get(Future<List<T>>f) {
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