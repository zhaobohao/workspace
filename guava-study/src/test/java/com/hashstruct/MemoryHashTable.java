package com.hashstruct;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhaobo
 * https://mp.weixin.qq.com/s?__biz=MjM5MTQzNzU2NA==&mid=207841440&idx=1&sn=e7c993bfdd5d39de9c24a6730f78ce5e&scene=2&ptlang=2052&ADUIN=2253485857&ADSESSION=1435464260&ADTAG=CLIENT.QQ.5419_.0&ADPUBNO=26486#rd
 */
class BackOffAtomicLong
{
	
	private final AtomicLong value = new AtomicLong ( 0L );
	
	public long get ( )
	{
		return value.get ( );
	}
	
	public long incrementAndGet ( )
	{
		for ( ;; )
		{
			long current = get ( );
			long next = current + 1;
			if ( compareAndSet (	current ,
									next ) )
				return next;
		}
	}
	
	public long subtractAndGet ( )
	{
		for ( ;; )
		{
			long current = get ( );
			long next = current - 1;
			if ( compareAndSet (	current ,
									next ) )
				return next;
		}
	}
	
	public boolean compareAndSet (	final long current ,
									final long next )
	{
		if ( value.compareAndSet (	current ,
									next ) )
		{
			return true;
		}
		else
		{
			LockSupport.parkNanos ( 1L );
			return false;
		}
	}
	
	public void set ( final long l )
	{
		value.set ( l );
	}
	
}

class DataItem
{// 数据项
	/**
	 * 其中data表示股票持仓数据,而compoundKey为(股东代码,股票代码，持仓类型,flag）的复合体，股东代码占用最63～27位（5位字符和四个字节无符号整数），
	 * 股票代码占26～11位（两个字节），持仓类型占用10～6位（五个字节），isTail占用第5位，表示是否为尾部，
	 * 即后继元素中没有相同hash值的元素。 doWrite占用第4位，表示该key正在更改/写入，
	 * readerCount占用3～0为表示该key正在读的线程数量。
	 */
	/**
	 * 每条持仓记录，由三个信息来定位（key）： 持仓类型（一位字符，A~F）， 股东代码（10位字符，第1位A~Z，后面9位是数字0~9），
	 * 股票代号（short类型）
	 * 
	 * 被定位到的持仓记录是一个64bit的值（value）
	 */
	private BackOffAtomicLong	compoundKey	= new BackOffAtomicLong ( );	// 数据项的关键字
	private BackOffAtomicLong	value		= new BackOffAtomicLong ( );;// 存储的值
											
	public DataItem ( Long key , Long value )
	{
		this.compoundKey.set ( key );
		this.value.set ( value );
	}
	
	public Long getKey ( )
	{
		return compoundKey.get ( );
	}
	
	public BackOffAtomicLong getBackOffAtomicLongKey ( )
	{
		return compoundKey;
	}
	
	public boolean setKey ( Long key )
	{
		return this.compoundKey.compareAndSet (	this.compoundKey.get ( ) ,
												key );
	}
	
	public Long getValue ( )
	{
		return value.get ( );
	}
	
	public BackOffAtomicLong getBackOffAtomicLongValue ( )
	{
		return value;
	}
	
	public boolean setValue ( Long value )
	{
		return this.value.compareAndSet (	this.value.get ( ) ,
											value );
	}
	
	public boolean equals ( DataItem di )
	{
		if ( this.getKey ( ) == di.getKey ( ) && this.getValue ( ) == di.getValue ( ) )
			return true;
		else
			return false;
	}
	
}

class DataItemUtils
{// 用来处理DataItem元素 ,由于看不懂文章里的维护策略，暂时没有使用。
	
	/**
	 * tail位为0时表示不是尾，为1时表示是尾
	 * 
	 * @return 是否是最后一个元素
	 */
	public static boolean isTail ( DataItem i )
	{
		
		return ( ( i.getKey ( ) ) & ( 12L ) ) == 12L;
	}
	
	public static boolean setIsTail ( DataItem i )
	{
		return i.setKey ( i.getKey ( ) | 12L );
	}
	
	public static boolean setIsNotTail ( DataItem i )
	{
		return i.setKey ( i.getKey ( ) & 9223372036854775791L );
	}
	
	/**
	 * 0 未写入 1正在写入
	 * 
	 * @param i
	 * @return
	 */
	public static boolean isWrite ( DataItem i )
	{
		return ( ( i.getKey ( ) ) & ( 8L ) ) == 8L;
	}
	
	public static boolean setIsWrite ( DataItem i )
	{
		return i.setKey ( i.getKey ( ) | 8L );
	}
	
	public static boolean setIsNotWrite ( DataItem i )
	{
		return i.setKey ( i.getKey ( ) & 9223372036854775799L );
	}
	
	/**
	 * 是否有线程在读
	 */
	public static boolean isReading ( DataItem i )
	{
		return ( ( i.getKey ( ) ) & ( 7L ) ) != 0L;
	}
	
	public boolean incrementReading ( DataItem i )
	{
		if ( ( ( i.getKey ( ) ) & ( 7L ) ) == 7L )
			return true;
			
		i.getBackOffAtomicLongKey ( ).incrementAndGet ( );
		return true;
	}
	
	public boolean subtractReading ( DataItem i )
	{
		if ( ( ( i.getKey ( ) ) & ( 7L ) ) == 0L )
			return true;
			
		i.getBackOffAtomicLongKey ( ).subtractAndGet ( );
		return true;
	}
}
 /**
  * 
   * <B>Summary:</B> <Br> 
   * 项目名称：testPatternBox <Br> 
   * 类名称：MemoryHashTable <Br> 
   * 类描述： <Br> 
   * 创建人：zhaobo <Br> 
   * 创建时间：2016年3月4日 下午5:09:27 <Br> 
   * 修改人：zhaobo <Br> 
   * 修改时间：2016年3月4日 下午5:09:27 <Br> 
   * 注意事项： 类似的内存数据管理技术，用到了交易系统的几乎所有的内存数据结构中。
   * 因此可以最大程度发挥CPU的运算能力，减小同步、进程切换的开销。
   * 全异步、无锁、用户态和核心态数据共享等技术。<Br> 
   * 修改备注： 最后一次提交到项目仓库 <Br> 
   *            时间                    版本号           用户名 <Br> 
   *           $Date$        $Rev$        $Author$ <Br> 
   * @version  
   *
  */
public class MemoryHashTable
{// 数组实现的哈希表，开放地址法之再哈希法
	private DataItem[]	hashArray;	// 存数据的数组
	private int			arraySize;
	private DataItem	nonItem;	// 已删除标志
	private int			contstant;	// 二次hash函数因子。
						
	MemoryHashTable ( int size )
	{// 哈希表构造函数
		arraySize = findPrimeNumber ( size );
		hashArray = new DataItem[arraySize];
		nonItem = new DataItem ( -1L , -1L );
		contstant = findPrimeNumber ( arraySize / 10 );
	}
	
	public void displayTable ( )// 输出哈希表
	{
		System.out.print ( "Table: " );
		for ( int j = 0 ; j < arraySize ; j++ )
		{
			if ( hashArray[j] != null )
				System.out.print ( hashArray[j].getKey ( ) + " " );
			else
				System.out.print ( "** " );
		}
		System.out.println ( "" );
	}
	
	// 哈希函数1
	private int hashFunc1 ( long key )
	{
		return Integer.parseInt ( Long.toString ( key % arraySize ) );
	}
	
	// 哈希函数2,不同于哈希函数1，用于再哈希。
	private int hashFunc2 ( long key )
	{
		// array size must be relatively prime to 5, 4, 3, and 2
		return Integer.parseInt ( Long.toString ( contstant - key % contstant ) );
	}
	
	// 哈希表中插入数据 这里的CAS是 Test and Test and Test and set
	public void insert (	long key ,long value)
		{
		
		int hashVal = hashFunc1 ( key); // 求关键字的哈希值
		int stepSize = hashFunc2 ( key ); // 再探测步长的大小
		
		while ( hashArray[hashVal] != null && hashArray[hashVal].getKey ( ) != -1 )
		{
			if ( hashArray[hashVal].getKey ( ) == key)
			{
				//判断当前对象是我们要操作的对象
				hashArray[hashVal].setValue ( value );
				return ;
			}
			else
			{
				hashVal += stepSize; // 单元被占用，再探测
				hashVal %= arraySize;
			}
		}
		//此处存在线程不安全问题，如果两个线程中的hashkey完全相同，同时写这个hashVal位置会怎么样？？？
		hashArray[hashVal] = new DataItem ( key , value );
	}
	
	// 在哈希表中删除
	public DataItem delete ( long key )
	{
		int hashVal = hashFunc1 ( key );
		int stepSize = hashFunc2 ( key );
		
		while ( hashArray[hashVal] != null )
		{// 直到一个非空单元出现
			if ( hashArray[hashVal].getKey ( ) == key )
			{
				DataItem temp = hashArray[hashVal];
				hashArray[hashVal] = nonItem; // 作删除标记
				return temp;
			}
			hashVal += stepSize; // 再探测
			hashVal %= arraySize;
		}
		return null;
	}
	
	// 在哈希表中搜索
	public long find ( long key )
	{
		int hashVal = hashFunc1 ( key );
		int stepSize = hashFunc2 ( key );
		
		while ( hashArray[hashVal] != null )
		{
			if ( hashArray[hashVal].getKey ( ) == key )
				return hashArray[hashVal].getValue ( );
			hashVal += stepSize;
			hashVal %= arraySize;
		}
		return 0L;
	}
	
	/**
	 * 找出大于capacity 的最小质数，并且该质数容量是minseed的7分之10 hashtable的装载率不能大于70%
	 * 用开放地址探测的散列，要保证在一定步数内找到一个空位， 你必须让数组长度为一个质数。在装载率为70%的情况下，
	 * 一个质数长度的数组，基本就是几步之内你就一定能找到一个空位。
	 * 
	 * @param capacity
	 *            hashtable的容量。
	 * 
	 * @return
	 */
	private Integer findPrimeNumber ( int capacity )
	{
		capacity = capacity * 10 / 7; // 预计装载量是hashtable容量的70%
		if ( capacity % 2 == 0 )
		{
			// 质数肯定是奇数
			capacity++ ;
		}
		if ( capacity <= 1 )
		{
			return 2;
		}
		if ( capacity == 2 || capacity == 3 )
		{
			return 5;
		}
		while ( true )
		{
			int sqrt = (int) Math.sqrt ( capacity );
			
			for ( int i = 3 ; i <= sqrt ; i += 2 )
			{
				if ( capacity % i == 0 )
				{
					capacity += 2;
					break;
				}
				
			}
			return capacity;
		}
	}
	
}