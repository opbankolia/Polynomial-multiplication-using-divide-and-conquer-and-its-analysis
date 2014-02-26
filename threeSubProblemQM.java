
import java.text.DecimalFormat;
import java.util.Random;


/**
 * Rohan D. Shah A01943549
 * IDE: NetBeans 7.4
 * Programming language = JAVA
 * Solution of polynomial multiplication by divide and conquer and generating 3 subproblems.
 * 
 * --------------------------------------------------------------------------------------------------------------------
 * Description:
 * --------------------------------------------------------------------------------------------------------------------
 * This program implements polynomial multiplication using divide and conquer thereby generating 3 subproblems and solving them 
 * recursively.
 * The degree of the polynomial is n-1 where n is the number of coefficients. 
 * To calculate the execution time, I have used profiling tool in the IDE. It starts and stops the stop watch
 * at defined points. I have defined the start point at 124 and end point at 161.
 * 
 * -----------------------------------------------------------------------------------------------------------------
 * Multiplication mechanism:
 * -----------------------------------------------------------------------------------------------------------------
 * The algorithm first breaks the polynomial into equal parts by halving the powers generating a polynomial which has low powers
 * and other has high. 
 * for eg: pLow = sum(pi.x^i), { 0 <= i <= (n/2)-1 }
 *        pHigh = sum(pi.x^i), { (n/2) <= i <= (n-1) }
 * Therefore, p = pLow + pHigh.x^(n/2)
 * Same is done to the q equation as well.
 * We break the polynomial until the number of coefficient is 1 and then we return the product of the only elements in the polynomials arrays.
 * The product, pq = pLow.qLow + (phigh.qLow + pLow.qHigh).x^n/2 + pHigh.qHigh.x^n
 * The index of the array represents the power of the unknown variable 'x' in the polynomial.
 * 
 * Now, if we see the middle term, we can represent that term in a different way which will reduce one multiplication operation
 * and increase one addition operation. 
 * pLow.qHigh + pHigh.qLow = [(pLow + phigh).(qLow + qHigh)] - plow.qLow - pHigh.qHigh
 * 
 * So now we have to compute and perform only 3 multiplication operation
 * This algorithm is faster than the "4 sub problem" algorithm as it invovles lesser number of multiplications (4 - 1 = 3).
 * The coefficients are randomly assigned and the values are between -1 and 1 inclusive.
 * @author Rohan
 */
public class threeSubProblemQM 
{
    // outputs the polynomial arrays
    static void printPoly(double []a, int length)
    {
        for(int z=0 ; z<length ; z++)
        {
            System.out.print(a[z]);
            if(z>0)
            System.out.print(" X^"+z);
            System.out.println();
          
        }
    }
    // the function which divides the polynomial into smaller pieces and solves them recursively
    // takes two polynomial arrays with the number of their coefficients as parameters
    // return the array which stores the product of the polynomial
    public static double [ ] polyMult(double [ ] p, double [ ] q, int n) 
    {
        // There are not more terms that could be divided and the problem couldnt be made any smaller
        if (n == 1) 
        {
            double [ ] temp = {p[0] * q[0]};
            return temp;
        }
        // dividing the problem to 'd' number of coefficients
        int d = n/2;
        //split p and q into arrays that store low and high order terms respectively
        double [ ] pHigh = new double[d];
        double [ ] qHigh = new double [d];        
        double [ ] pLow = new double[d-n%2];
        double [ ] qLow = new double[d-n%2];
        
        // assigning the respective coefficients to low and high arrays of p and q
        for (int i = 0; i < d; i++) 
        {
            pHigh[i] = p[i+d];
            qHigh[i] = q[i+d];
            pLow[i] = p[i];
            qLow[i] = q[i];
        }
        
        // the arrays which stores the addition which helps us in making the 
        // number of multiplication operations 3, for more information refer description
        double [ ] addLowHighP = new double[d] ;
        double [ ] addLowHighQ = new double[d];
        
        // initialising arrays
        for(int i = 0 ; i<(d) ; i++)
        {
            addLowHighP[i] = pLow[i]+pHigh[i];
            addLowHighQ[i] = qLow[i]+qHigh[i];
        }
        
        // These are the 3 subproblems
        
        // The pLow.qLow part of the multiplication
        double [ ] lowPQ = polyMult(pLow,qLow,d);  
        
        // middle term of the solution
        double [ ] middle = polyMult(addLowHighP,addLowHighQ,d);  
        
        // the last term pHigh.qHigh.x^n
        double [ ] highPQ = polyMult(pHigh,qHigh,d);
        
        // the array that stores the product of the polynomials
        double [ ] pq = new double[(2 * n)-1];
        
        // storing the collected values in the product array, refer description for more information
        for (int i = 0; i < n-1; i++) 
        {
            pq[i] += lowPQ[i];            
            pq[i+d] += middle[i]  - lowPQ[i] - highPQ[i];
            pq[i+2*d] += highPQ[i];
        }
        // returns the array that has mulitplication of two polynomials
        return pq;
    }
    
    public static void main(String[] args) 
    {
        int noOfCoefficient = 8192;
        double[] p = new double[noOfCoefficient];
        double[] q = new double[noOfCoefficient];
        double[] pq = new double[(2*noOfCoefficient)-1];
        double forDistribution;        
        //assign sizes and values randomly
        Random randomGenerator = new Random();
        
         //format to 5 decimal place
        DecimalFormat oneDigit = new DecimalFormat("#.#####");
        // Fill in size and values of items with random numbers between -1 and 1 inclusive
        for(int i=0 ; i<noOfCoefficient ; i++)
        {   
            // the nextdouble method gives values from 0 to 1 but we want to have values from -1 to 1
            // hence we multiply by 2 and then subtract 1 from it to get values between that range
            forDistribution = randomGenerator.nextDouble() * 2 - 1;
            p[i] = Double.valueOf(oneDigit.format(forDistribution));
           

            forDistribution = randomGenerator.nextDouble() * 2 - 1;            
            q[i] = Double.valueOf(oneDigit.format(forDistribution));
        }
        System.out.println("------------------------------------");
        System.out.println("The first polynomial=");
        System.out.println("------------------------------------");
        printPoly(p, p.length);
        
        System.out.println("------------------------------------");
         System.out.println("The second polynomial=");
         System.out.println("------------------------------------");
        printPoly(q, q.length);
        System.out.println("------------------------------------");
        
       
        System.out.println("The multiplication product");
        pq = polyMult(p,q,noOfCoefficient);
        printPoly(pq, pq.length);
        
    }
}
