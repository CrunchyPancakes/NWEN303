package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSorter<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {

  private static final int threshold = 20;
  private List<T> toSort = new ArrayList<>();

  public ForkJoinSorter(List<T> list){
    this.toSort = list;
  }

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

  @Override
  protected List<T> compute() {
    // Delegate to MSequentialSorter for cases with less than 20 elements
    if(toSort.size() < threshold){
      return sequentialSort(toSort);
    }

    // Find the mid point of the list for splitting purposes
    int mid = toSort.size()/2;

    // Fork each half of the merge-sort.
    // Recursively calls sort() on the 'left' and 'right' halves.
    ForkJoinSorter<T> left = new ForkJoinSorter(toSort.subList(0,mid));
    ForkJoinSorter<T> right = new ForkJoinSorter(toSort.subList(mid,toSort.size()));

    // Return the 2 halves of a list merged together.
    invokeAll(left,right);
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
