using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShoeStore.DataAccess.Entities
{
    [Table("promocodes")]
    public class PromocodeEntity : BaseEntity
    {
        public string PromocodeStr { get; set; }
        public int DiscountProcent { get; set; }

        public ICollection<OrderEntity> Orders { get; set; }
    }
}
