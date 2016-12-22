package util;

/// <summary>
///  PageÀà
/// </summary>

public class Page {

    public static int pageSize = 10;
    private int pageIndex;
    private int pageCount;
    private int count;

    public Page(int pageIndex,int count){

       if(count%pageSize==0){
           this.pageCount = count/pageSize;
       }else{
           this.pageCount = count/pageSize+1;
       }
       if(pageIndex>pageCount){
           pageIndex = pageCount;
       }
       if(pageIndex<1){
           pageIndex = 1;
       }
       this.pageIndex = pageIndex;
       this.count = count;

    }

    public Page(int pageSize,int pageIndex,int count){

       this.pageSize = pageSize;
       if(count%pageSize==0){
           this.pageCount = count/pageSize;
       }else{
           this.pageCount = count/pageSize+1;
       }
       if(pageIndex>pageCount){
           pageIndex = pageCount;
       }
       if(pageIndex<1){
           pageIndex = 1;
       }
       this.pageIndex = pageIndex;
       this.count = count;

    }

    public int getPageSize() {
       return pageSize;
    }

    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }

    public int getPageIndex() {
       return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
       this.pageIndex = pageIndex;
    }

    public int getPageCount() {
       return pageCount;
    }

    public void setPageCount(int pageCount) {
       this.pageCount = pageCount;
    }

    public int getCount() {
       return count;
    }

    public void setCount(int count) {
       this.count = count;
    }

}