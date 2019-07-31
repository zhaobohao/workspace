using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;  

namespace EasyopenSDKTest
{
    class Program
    {
        static int i = 0;

        static void Main(string[] args)
        {
            Timer t = new Timer();
            t.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            t.Interval = 5000;
            t.Enabled = true;
            Console.Read();
        }

        private static void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            Console.Clear();
            Console.WriteLine(i++);
        }
    }  
}
