import java.io.*;
import java.util.*;

/**
 * Read in bitstrings and keep track of only those strings that
 * increase the rank of the space. This is done by performing
 * Gaussian Elimination each time a new string is added.
 *
 * Get the resulting set of bitstrings in reduced row echelon form
 * and track the operations used to do this.
 * Apply those operations to each target bitstring. Iterate over 
 * the resulting bitstring to determine the largest index required
 * from the initial set of bitstrings.
 *
 * @author Finn Lidbetter
 */

public class basicbasis_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    
    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int m = Integer.parseInt(s[1]);
    int k = Integer.parseInt(s[2]);
    BitSet[] arr = new BitSet[k*4];
    for (int i=0; i<arr.length; i++) {
      arr[i] = new BitSet(n+5);
    }
    int minSkip = -1;
    HashMap<Integer, Integer> minIndex = new HashMap<>();
    ArrayList<Operation> ops = new ArrayList<>();
    int maxColumn = 0;
    for (int i=0; i<n; i++) {
      String line = br.readLine();
      int[] binaryArray = toBinaryArray(line);
      if (allZero(binaryArray)) {
        if (minSkip==-1)
          minSkip = i;
        continue;
      }
      applyOps(binaryArray, ops);
      for (int j=0; j<4*k; j++) {
        arr[j].set(maxColumn, binaryArray[j]==1);
      }
      if (maxColumn>0) {
        ArrayList<Operation> newOps = rref(arr, maxColumn+2, true);
        if (isConsistent(arr, maxColumn+1)) {
          reverseOps(arr, newOps);
          if (minSkip==-1)
            minSkip = i;
          continue;
        }
        for (Operation op: newOps) {
          ops.add(op);
        }
        minIndex.put(maxColumn, i);
        maxColumn++; 
      } else {
        minIndex.put(maxColumn, i);
        maxColumn++;
      }
    }
    //rref(arr, maxColumn+1, false);

    int[] setColumn = new int[4*k];
    Arrays.fill(setColumn, -1);
    for (int c=0; c<maxColumn; c++) {
      for (int r=0; r<4*k; r++) {
        if (arr[r].get(c)) {
          setColumn[r] = c;
        }
      }
    }
    for (int i=0; i<m; i++) {
      int[] binaryArray = toBinaryArray(br.readLine());
      boolean zeroCase = false;
      if (allZero(binaryArray)) {
        if (minSkip!=-1) {
          System.out.println(minSkip + 1);
        } else {
          System.out.println(-1);
        }
        continue;
      }
      applyOps(binaryArray, ops);
      for (int j=binaryArray.length-1; j>=0; j--) {
        if (binaryArray[j]==1) {
          int bitStringIndex = setColumn[j];
          if (bitStringIndex==-1) {
            System.out.println(-1);
          } else {
            System.out.println(minIndex.get(bitStringIndex) + 1);
          }
          break;
        }
      }
    }
  }
  static void printBitSetArr(BitSet[] arr, int columns) {
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<arr.length; i++) {
      sb = new StringBuilder();
      for (int j=0; j<columns; j++) {
        sb.append(arr[i].get(j) ? "1" : "0");
      }
      System.out.println(sb.toString());
    }
  }
  static boolean allZero(int[] binaryArray) {
    for (int i:binaryArray) {
      if (i!=0)
        return false;
    }
    return true;
  }
  static boolean isConsistent(BitSet[] arr, int columns) {
    boolean consistent = true;
    for (int i=0; i<arr.length; i++) {
      if (arr[arr.length-i-1].get(columns-1)) {
        consistent = false;
        for (int j=0; j<columns-1; j++) {
          if (arr[arr.length-i-1].get(j)) {
            consistent = true;
            break;
          }
        }
        break;
      }
    }
    return consistent;
  }
  static BitSet[] makeCopy(BitSet[] arr, int columns) {
    BitSet[] copy = new BitSet[arr.length];
    for (int i=0; i<arr.length; i++) {
      copy[i] = new BitSet(columns);
    }
    for (int i=0; i<arr.length; i++) {
      for (int j=0; j<columns; j++) {
        copy[i].set(j, arr[i].get(j));
      }
    }
    return copy;
  }
  static int[] toBinaryArray(String hexString) {
    int k = hexString.length();
    int[] arr = new int[k*4];
    char[] seq = hexString.toCharArray();
    for (int i=0; i<k; i++) {
      int val = Integer.parseInt(""+seq[i], 16);
      for (int j=0; j<4; j++) {
        arr[4*i+j] = ((val&(1<<j)) > 0) ? 1 : 0;
      }
    }
    return arr;
  }

  static ArrayList<Operation> rref(BitSet[] arr, int columns, boolean returnOperations) {
    int n = arr.length; 
    int m = columns;
    int r = 0;
    ArrayList<Operation> ops = new ArrayList<>();
    for (int i = 0; i < m-1 && r<n; i++) {
      if (!arr[r].get(i)) {
        for (int k = r+1; k < n; k++) {
          if (arr[k].get(i)) {
            BitSet t = arr[r];
            arr[r] = arr[k]; 
            arr[k] = t; 
            if (returnOperations)
              ops.add(new Operation(r,k,true));
            break; 
          }
        }
      }
      if (!arr[r].get(i)) 
        continue;

      for (int j = 0; j < n; j++) {
        if (j==r || !arr[j].get(i)) {
          continue;
        }
        arr[j].xor(arr[r]);
        arr[j].set(i, false);
        if (returnOperations)
          ops.add(new Operation(j,r,false));
      }
      r++; 
    } 
    if (returnOperations)
      return ops;
    return null;
  }
  static void applyOps(int[] arr, ArrayList<Operation> ops) {
    for (Operation op: ops) {
      if (op.isSwap) {
        int tmp = arr[op.r1];
        arr[op.r1] = arr[op.r2];
        arr[op.r2] = tmp;
      } else {
        arr[op.r1] ^= arr[op.r2];
      }
    }
  }
  static void reverseOps(BitSet[] arr, ArrayList<Operation> ops) {
    for (int i=0; i<ops.size(); i++) {
      Operation op = ops.get(ops.size()-1-i);
      if (op.isSwap) {
        BitSet tmp = arr[op.r1];
        arr[op.r1] = arr[op.r2];
        arr[op.r2] = tmp;
      } else {
        arr[op.r1].xor(arr[op.r2]);
      }
    }
  }
}
class Operation {
  int r1; 
  int r2;
  boolean isSwap;
  public Operation(int rr1, int rr2, boolean isSwap) {
    r1 = rr1;
    r2 = rr2;
    this.isSwap = isSwap;
  }
}
