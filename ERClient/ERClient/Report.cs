using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ERClient
{
    public class Report
    {
        public int emergencyId { get; set; }
        public int userId { get; set; }
        public double latitude { get; set; }
        public double longitude { get; set; }
        public string location { get; set; }
        public int numVictims { get; set; }
        public int numWounded { get; set; }
        public string type { get; set; }
        public string description { get; set; }
        public DateTime datetime { get; set; }
    }
}
