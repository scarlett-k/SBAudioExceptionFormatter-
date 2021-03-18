/**
 * ExceptionLine class
 */
public class ExceptionLine  implements Comparable {

    private int count;
    private String exception;
    private String tag;

    public ExceptionLine(int count, String exception, String tag){
        this.count = count;
        this.exception = exception;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.format("%7d\t\t%20s\t\t%s%n", count, exception, tag);
    }

    @Override
    public int compareTo(Object o) {
        int compareCount=((ExceptionLine)o).getCount();
        /* For Ascending order*/
        return compareCount-this.count;
    }
    public int getCount(){
        return count;
    }

}
