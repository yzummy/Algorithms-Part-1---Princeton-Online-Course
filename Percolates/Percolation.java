/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    none
 *  Dependencies: none
 *
 *  This class contructs a percolation model.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   
    private WeightedQuickUnionUF tbUF, tUF;
    private boolean[] status;
    private int n;
    
   public Percolation(int n)               // create n-by-n grid, with all sites blocked
   {
       int numSites = n*n;
       this.n = n;
       
       //index 0 and numSites+1 are for top and bottom virtual sites
       tbUF = new WeightedQuickUnionUF(numSites + 2);
       tUF = new WeightedQuickUnionUF(numSites + 1);
       status = new boolean[numSites + 2];
       
       //Initialize all sites block
       for(int i = 1; i < numSites + 1; i++)
       {
           status[i] = false;
       }
       
       //Initialize top and bottom sites open
       status[0] = true;
       status[numSites+1] = true;
   }
 
   public int GridToIndex(int i, int j) //Convert grids to index
   {
       if(i<=0 || i>n)
           throw new IndexOutOfBoundsException("row i out of bound");
       if(j<=0 || j>n)
           throw new IndexOutOfBoundsException("row j out of bound");
       
       return (i-1) * n + j;
   }
   
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
       int index = GridToIndex(i,j); 
       status[index] = true;
       
       if(i!=1 && isOpen(i-1,j))
       {
           tbUF.union(index, GridToIndex(i-1,j));
           tUF.union(index, GridToIndex(i-1,j));
       }
       if(i!=n && isOpen(i+1,j))
       {
           tbUF.union(index, GridToIndex(i+1,j));
           tUF.union(index, GridToIndex(i+1,j));
       }
       if(j!=1 && isOpen(i,j-1))
       {
           tbUF.union(index, GridToIndex(i,j-1));
           tUF.union(index, GridToIndex(i,j-1));
       }
       if(j!=n && isOpen(i,j+1))
       {
           tbUF.union(index, GridToIndex(i,j+1));
           tUF.union(index, GridToIndex(i,j+1));
       }
       
       if(GridToIndex(i,j)>=1 && GridToIndex(i,j)<=n)
       {
           tbUF.union(index,0);
           tUF.union(index,0);
       }
       
       if(GridToIndex(i,j)>=(n-1)*n+1)
       {
           tbUF.union(index,status.length-1);
       }
       
   }
   
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
       return status[GridToIndex(i,j)];
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
       return tbUF.connected(0,GridToIndex(i,j)) && tUF.connected(0,GridToIndex(i,j));
   }
   public boolean percolates()             // does the system percolate?
   {
       boolean per = false;
       for(int i = 1; i<= n;i++)
       {
           if(isFull(n,i))
           {
               per = true;
               break;
           }
       }
       return per;
   }
   public static void main(String[] args)  // test client (optional)
   {
   }
}