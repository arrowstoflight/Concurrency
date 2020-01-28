// Dustin White
// COP 4520
// Professor Dechev
// 20 January 2020

import java.text.DecimalFormat;
import java.io.*;

class Primality extends Thread
{
  private String name;
  private int startNum;
  private int endNum;
  private int numPrimes;
  private long sumPrimes;
  private boolean[] primes;

  public Primality(String n, int sNum, int eNum)
  {
    name = n;
    startNum = sNum;
    endNum = eNum;
    numPrimes = 0;
    sumPrimes = 0;
    primes = new boolean[100000001];
  }
    public void run()
    {
        try
        {
            // Only loop through odd numbers. StartNum is always odd
            for(int i = startNum; i < endNum; i+= 2)
            {
              // Adding 2 to the mix since we skip all evens
              if(i == 3)
              {
                numPrimes++;
                sumPrimes += 2;
              }
              if(isPrime(i))
              {
                primes[i] = true;
                numPrimes++;
                sumPrimes += i;
              }
            }
            System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }

    public boolean isPrime(int number)
    {
      if(number < 2)
        return false;
      else if(number == 2 || number == 3)
        return true;
      else if (number % 2 == 0 || number % 3 == 0)
      {
          return false;
      }

      int i = 5;

      while(i * i <= number)
      {
        if(number % i == 0 || number % (i + 2) == 0)
          return false;
        i+=6;
      }
      return true;

    } // end isPrime

    public int getNumPrimes()
    {
      return numPrimes;
    }

    public int getStartNum()
    {
      return startNum;
    }

    public int getEndNum()
    {
      return endNum;
    }

    public long getSum()
    {
      return sumPrimes;
    }

    public boolean[] getPrimes()
    {
      return primes;
    }
} // end Primality

// Main Class
public class Multithread
{
    public static void main(String[] args)
    {
      int totalPrimes = 0;
      long sum = 0;
      int topPrimes[] = new int[10];
      boolean prime[];
      Writer writer = null;

      long startTime = System.nanoTime();

      Primality t1 = new Primality("Thread 1", 1, 12500000);
      Primality t2 = new Primality("Thread 2", 12500001, 25000000);
      Primality t3 = new Primality("Thread 3", 25000001, 37500000);
      Primality t4 = new Primality("Thread 4", 37500001, 50000000);
      Primality t5 = new Primality("Thread 5", 50000001, 62500000);
      Primality t6 = new Primality("Thread 6", 62500001, 75000000);
      Primality t7 = new Primality("Thread 7", 75000001, 87500000);
      Primality t8 = new Primality("Thread 8", 87500001, 100000000);
      try
      {
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
      }
      catch(Exception e)
      { System.out.println("Caught exception: " + e);}
      finally
      {
        long endTime = System.nanoTime();
        double elapsedTime = ((double)endTime - (double)startTime) / 1000000000;

        int counter = 9;
        prime = t8.getPrimes();
        // Stores the top 10 Primes in the topPrime array
        for(int i = 100000000; counter >= 0; i--)
        {
          if(prime[i] == true)
          {
            topPrimes[counter--] = i;
          }
        }

        /*
        System.out.println(t1.getNumPrimes());
        System.out.println(t2.getNumPrimes());
        System.out.println(t3.getNumPrimes());
        System.out.println(t4.getNumPrimes());
        System.out.println(t5.getNumPrimes());
        System.out.println(t6.getNumPrimes());
        System.out.println(t7.getNumPrimes());
        System.out.println(t8.getNumPrimes());
        */

        try
        {
          sum = t1.getSum() + t2.getSum() + t3.getSum() + t4.getSum() + t5.getSum() + t6.getSum() + t7.getSum() + t8.getSum();
          totalPrimes = t1.getNumPrimes() + t2.getNumPrimes() + t3.getNumPrimes() + t4.getNumPrimes() + t5.getNumPrimes() + t6.getNumPrimes() + t7.getNumPrimes() + t8.getNumPrimes();
          writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("primes.txt"), "utf-8"));

          writer.write("<Runtime: " + String.format("%.2f", elapsedTime) + " seconds><Total Primes: " + totalPrimes+ "><Sum: " + sum + ">\n");
          writer.write("<Top 10 Primes: ");
          for(int i = 0; i < 10; i++)
          {
            writer.write(topPrimes[i] + ", ");
          }
          writer.write(">\n");
        }
        catch (IOException ex)
        {/* Report*/}
        finally
        {
          try
          {
            writer.close();
          }
          catch (Exception ex)
          {/*ignore*/}
        } // end finally

      } // end finally
    } // end main
} // end Multithread class
