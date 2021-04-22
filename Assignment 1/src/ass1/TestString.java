package ass1;

import java.util.Random;

import org.junit.jupiter.api.Test;


public class TestString {

    public static final String[][] dataset={
            {"squealing", "drive", "drive", "ants", "bread", "receptive", "cast", "staking", "ball", "ball", "corrod", "naughty", "hiss", "implode", "corrod", "drive", "cast", "address", "trains", "go", "staking", "ants"},
            {"squealing", "drive", "drive", "ants", "bread", "receptive", "cast", "staking", "ball", "ball", "corrod", "naughty", "hiss", "implode", "corrod", "drive", "cast", "address", "trains", "go", "staking", "ants"},
            {"squealing", "drive", "drive", "ants", "bread", "receptive", "cast", "staking", "ball", "ball", "corrod", "naughty", "hiss", "implode", "corrod", "drive", "cast", "address", "trains", "go", "staking", "ants"},
            {"squealing", "drive", "drive", "ants", "bread", "receptive", "cast", "staking", "ball", "ball", "corrod", "naughty", "hiss", "implode", "corrod", "drive", "cast", "address", "trains", "go", "staking", "ants"},
            {},
            manyOrdered(10000),
            manyReverse(10000),
            manyRandom(10000)
    };
    static private String[] manyRandom(int size) {
        Random r=new Random(0);
        String[] result=new String[size];
        for(int i=0;i<size;i++){
            int num = r.nextInt();
            result[i]=new String(String.valueOf(num));
        }
        return result;
    }
    static private String[] manyReverse(int size) {
        String[] result=new String[size];
        for(int i=0;i<size;i++){result[i]=new String("99999"+(size-i));}
        return result;
    }
    static private String[] manyOrdered(int size) {
        String[] result=new String[size];
        for(int i=0;i<size;i++){result[i]=new String("99999"+i);}
        return result;
    }

    @Test
    public void testISequentialSorter() {
        Sorter s=new ISequentialSorter();
        for(String[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMSequentialSorter() {
        Sorter s=new MSequentialSorter();
        for(String[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter1() {
        Sorter s=new MParallelSorter1();
        for(String[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter2() {
        Sorter s=new MParallelSorter2();
        for(String[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter3() {
        Sorter s=new MParallelSorter3();
        for(String[]l:dataset){TestHelper.testData(l,s);}
    }
}
