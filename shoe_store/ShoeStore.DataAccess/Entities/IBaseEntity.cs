using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShoeStore.DataAccess.Entities
{
    public interface IBaseEntity
    {
        public int Id { get; set; } //ключ в бд

        public Guid ExternalId { get; set; } // unique index - unique optional
        public DateTime ModificationTime { get; set; } // optional
        public DateTime CreationTime { get; set; } //optional 
    }
}
