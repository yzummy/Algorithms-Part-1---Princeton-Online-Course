import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
   
    private double[] trd;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
        int numOpen, row, column;
       if(n<0 || trials<0)
           throw new IllegalArgumentException("Invalid Arguments. Out of bound!");
       
       trd = new double[trials];
       
       for(int i=0;i<trials;i++){
           Percolation per = new Percolation(n);
           numOpen = 0;
           do{
               row = StdRandom.uniform(1,n+1);
               column = StdRandom.uniform(1,n+1);
               if(!per.isOpen(row,column)){
                   per.open(row,column);
               }
               numOpen++;     
               
           }while(!per.percolates());
           //StdOut.println("numOpen: "+numOpen);
           trd[i] = (double) numOpen / n / n;
           
       }
       
   }
   public double mean()                          // sample mean of percolation threshold
   {
       return StdStats.mean(trd);
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(trd);
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return mean()-1.96*stddev()/Math.sqrt(trd.length);
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return mean()+1.96*stddev()/Math.sqrt(trd.length);
   }
   public static void main(String[] args)    // test client (described below)
   {
       PercolationStats stat = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
       StdOut.println("mean                    = " + stat.mean());
       StdOut.println("stddev                  = " + stat.stddev());
       StdOut.println("95% confidence interval = " + stat.confidenceLo() +", " + stat.confidenceHi());
       
       
   }

}