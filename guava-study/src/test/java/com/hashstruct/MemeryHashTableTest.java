package com.hashstruct;
/** 哈希表中，一串连续的已填充单元叫做填充序列。增加越来越多的数据项时，填充序列变的越来越长，
这叫做聚集。
http://chuansong.me/n/1489885
  为了消除原始聚集和二次聚集，可以使用另外的一个方法：再哈希法：一种依赖关键字的探测序
列，而不是每个关键字都一样，那么，不同的关键字即使映射到相同的数组下标，也可以使用不同的
探测序列。
   方法是把关键字用不同的哈希函数再做一次哈希化，用这个结果作步长，对指定的关键字，步长在
整个探测中是不变的，不同的关键字使用不同的步长。

*/

import java.io.*;

 class MemeryHashTableTest{
   public static void main(String[] args) throws IOException{
	   long aKey;
      long size, n;
                      
      System.out.print("Enter size of hash table: ");
      size = getLong();
      System.out.print("Enter initial number of items: ");
      n = getLong();
                               
      MemoryHashTable theHashTable = new MemoryHashTable(Integer.parseInt(Long.toString(size)));

      for(int j=0; j<n; j++){
         aKey = (long)(Math.random() * 2 * size);
         theHashTable.insert(aKey, aKey);
      }

      while(true){
         System.out.print("Enter first letter of ");
         System.out.print("show, insert, delete, or find: ");
         char choice = getChar();
         switch(choice)
            {
            case 's':
               theHashTable.displayTable();
               break;
            case 'i':
               System.out.print("Enter key value to insert: ");
               aKey = getLong();
               theHashTable.insert(aKey, aKey);
               break;
            case 'd':
               System.out.print("Enter key value to delete: ");
               aKey = getLong();
               theHashTable.delete(aKey);
               break;
            case 'f':
               System.out.print("Enter key value to find: ");
               aKey = getLong();
               if(theHashTable.find(aKey) != 0L)
                  System.out.println("Found " + aKey);
               else
                  System.out.println("Could not find " + aKey);
               break;
            default:
               System.out.print("Invalid entry\n");
            }  
         }  
      }  

   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }

   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }

   public static long  getLong() throws IOException
      {
      String s = getString();
      return Long.parseLong(s);
      }

   } 
