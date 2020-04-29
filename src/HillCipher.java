import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class HillCipher {
    //int keymatrix[][];
    //int linematrix[];
    //int resultmatrix[];
    //String crypted, inverseKey;
    //HillCipher obj;
 
 
    public static int[][] keytomatrix(String key, int len)
    {
        int[][] keymatrix = new int[len][len];
        int c = 0;
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                keymatrix[i][j] = ((int) key.charAt(c)) - 97;
                c++;
            }
        }
        return keymatrix;
    }
 
    public static int[] linetomatrix(String line)
    {
       int[] linematrix = new int[line.length()];
        for (int i = 0; i < line.length(); i++)
        {
            linematrix[i] = ((int) line.charAt(i)) - 97;
        }
        return linematrix;
    }
 
    public static int[] linemultiplykey(int len, int[] linematrix, int[][] keymatrix)
    {
        int[] resultmatrix = new int[len];
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                resultmatrix[i] += keymatrix[i][j] * linematrix[j];
            }
            resultmatrix[i] %= 26;
        }
        return resultmatrix;
    }
 
    public static String result(int len, int[ ]resultmatrix)
    {
        String result = "";
        for (int i = 0; i < len; i++)
        {
            result += (char) (resultmatrix[i] + 97);
        }
        return result;
    }
 
    public static boolean check(String key, int len)
    {
        int[][] keymatrix = keytomatrix(key, len);
        int d = determinant(keymatrix, len);
        d = d % 26;
        if (d == 0)
        {
            System.out
                    .println("Invalid key!!! Key is not invertible because determinant=0...");
            return false;
        }
        else if (d % 2 == 0 || d % 13 == 0)
        {
            System.out
                    .println("Invalid key!!! Key is not invertible because determinant has common factor with 26...");
            return false;
        }
        else
        {
            return true;
        }
    }
 
    public static int determinant(int A[][], int N)
    {
        int res;
        if (N == 1)
            res = A[0][0];
        else if (N == 2)
        {
            res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
        }
        else
        {
            res = 0;
            for (int j1 = 0; j1 < N; j1++)
            {
                int m[][] = new int[N - 1][N - 1];
                for (int i = 1; i < N; i++)
                {
                    int j2 = 0;
                    for (int j = 0; j < N; j++)
                    {
                        if (j == j1)
                            continue;
                        m[i - 1][j2] = A[i][j];
                        j2++;
                    }
                }
                res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1]
                        * determinant(m, N - 1);
            }
        }
        return res;
    }
 
    public void cofact(int num[][], int f)
    {
        int b[][], fac[][];
        b = new int[f][f];
        fac = new int[f][f];
        int p, q, m, n, i, j;
        for (q = 0; q < f; q++)
        {
            for (p = 0; p < f; p++)
            {
                m = 0;
                n = 0;
                for (i = 0; i < f; i++)
                {
                    for (j = 0; j < f; j++)
                    {
                        b[i][j] = 0;
                        if (i != q && j != p)
                        {
                            b[m][n] = num[i][j];
                            if (n < (f - 2))
                                n++;
                            else
                            {
                                n = 0;
                                m++;
                            }
                        }
                    }
                }
                fac[q][p] = (int) Math.pow(-1, q + p) * determinant(b, f - 1);
            }
        }
        trans(fac, f, num);
    }
 
    static int[][] trans(int fac[][], int r, int[][] keymatrix)
    {
        int i, j;
        int b[][], inv[][];
        b = new int[r][r];
        inv = new int[r][r];
        int d = determinant(keymatrix, r);
        int mi = mi(d % 26);
        mi %= 26;
        if (mi < 0)
            mi += 26;
        for (i = 0; i < r; i++)
        {
            for (j = 0; j < r; j++)
            {
                b[i][j] = fac[j][i];
            }
        }
        for (i = 0; i < r; i++)
        {
            for (j = 0; j < r; j++)
            {
                inv[i][j] = b[i][j] % 26;
                if (inv[i][j] < 0)
                    inv[i][j] += 26;
                inv[i][j] *= mi;
                inv[i][j] %= 26;
            }
        }
        System.out.println("\nInverse key:");
        return inv;
        //matrixtoinvkey(inv, r);
    }
 
    public static int mi(int d)
    {
        int q, r1, r2, r, t1, t2, t;
        r1 = 26;
        r2 = d;
        t1 = 0;
        t2 = 1;
        while (r1 != 1 && r2 != 0)
        {
            q = r1 / r2;
            r = r1 % r2;
            t = t1 - (t2 * q);
            r1 = r2;
            r2 = r;
            t1 = t2;
            t2 = t;
        }
        return (t1 + t2);
    }
 
    public static String matrixtoinvkey(int inv[][], int n)
    {
        String invkey = "";
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                invkey += (char) (inv[i][j] + 97);
            }
        }
        return invkey;
    }
 
    
    
    
	public static String[] run(int endecrypt, String text, String key) throws IOException
    {
        //HillCipher obj = new HillCipher();
        String line = text;
        double sq = Math.sqrt(key.length());
        int[]linematrix, resultmatrix;
        int[][] keymatrix = null;
        String crypted = null, inverseKey = null, sub;
        
        int s = (int) sq;
        if (check(key, s)) {
            //obj.divide(line, s);   //Divide
            while (line.length() > s) {
                sub = line.substring(0, s);
                line = line.substring(s, line.length());
                linematrix = linetomatrix(line);
                keymatrix = keytomatrix(key, s);
                resultmatrix = linemultiplykey(line.length(), linematrix, keymatrix);
                crypted = result(line.length(), resultmatrix);
            }
            if (line.length() == s) {
                linematrix = linetomatrix(line);
        		keymatrix = keytomatrix(key, s);
        		resultmatrix = linemultiplykey(line.length(), linematrix, keymatrix);
        		crypted = result(line.length(), resultmatrix);
            } else if (line.length() < s) {
                for (int i = line.length(); i < s; i++)
                    line = line + 'x';
                linematrix = linetomatrix(line);
                keymatrix = keytomatrix(key, s);
                resultmatrix = linemultiplykey(line.length(), linematrix, keymatrix);
                crypted = result(line.length(), resultmatrix);
            }
            
            
            
            
            
            
            
            //obj.cofact(obj.keymatrix, s);
        int b[][], fac[][];
        b = new int[s][s];
        fac = new int[s][s];
        int p, q, m, n, i, j;
        for (q = 0; q < s; q++)
        {
            for (p = 0; p < s; p++)
            {
                m = 0;
                n = 0;
                for (i = 0; i < s; i++)
                {
                    for (j = 0; j < s; j++)
                    {
                        b[i][j] = 0;
                        if (i != q && j != p)
                        {
                            b[m][n] = keymatrix[i][j];
                            if (n < (s - 2))
                                n++;
                            else
                            {
                                n = 0;
                                m++;
                            }
                        }
                    }
                }
                fac[q][p] = (int) Math.pow(-1, q + p) * determinant(b, s - 1);
            }
        }
        int[][] inv = trans(fac, s, keymatrix);
        inverseKey = matrixtoinvkey(inv, s);
       
        }
        String[] result =  {crypted, inverseKey};
        return result;
        
    }
    
}