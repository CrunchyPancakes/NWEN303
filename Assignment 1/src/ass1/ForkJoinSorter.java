package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSorter<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {

  // Threshold to determine when to delegate to a sequential sorter
  private static final int threshold = 20;
  // Need to keep the list to sort in a field as we cannot pass it into the compute() method
  private List<T> toSort;

  public ForkJoinSorter(List<T> list){
    this.toSort = list;
  }

  /**
   * Sort method taken from the ISequentialSorter class to use in cases with elements less than the threshold
   * @param list
   * @param <T>
   * @return
   */
  public <T extends Comparable<? super T>> List<T> sequentialSort(List<T> list) {
    List<T>result=new ArrayList<>();
    for(T l:list){insert(result,l);}
    return result;
  }
  <T extends Comparable<? super T>> void insert(List<T> list, T elem){
    for(int i=0;i<list.size();i++){
      if(list.get(i).compareTo(elem)<0){continue;}
      list.add(i,elem);
      return;
    }
    list.add(elem);
  }

  /**
   * Sorts a list by splitting it in half, and merging the halves while recursively calling sort()
   * on each half.
   * @return
   */
  @Override
  protected List<T> compute() {
    // Delegate to MSequentialSorter for cases with less than 20 elements
    if(toSort.size() < threshold){
      return sequentialSort(toSort);
    }

    // Find the mid point of the list for splitting purposes
    int mid = toSort.size()/2;

    // Fork each half of the merge-sort.
    // Recursively sorts the list by using the compute method of RecursiveTask
    ForkJoinSorter<T> left = new ForkJoinSorter(toSort.subList(0,mid));
    ForkJoinSorter<T> right = new ForkJoinSorter(toSort.subList(mid,toSort.size()));
    invokeAll(left,right);

    // Return the 2 halves of a list merged together.
    return merge(left.join(),right.join());
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
