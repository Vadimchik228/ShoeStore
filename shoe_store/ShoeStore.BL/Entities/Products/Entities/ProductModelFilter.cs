using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShoeStore.BL.Entities.Products.Entities
{
    public class ProductModelFilter
    {
        public decimal MinimumPrice { get; set; }
        public decimal MaximumPrice { get; set; }
        public int ProductTypeId { get; set; }
        public int ProductBrandId { get; set; }
        public short Size { get; set; }
        public Color Color { get; set; }
    }
}
