using System.Drawing;

namespace ShoeStore.Service.Controllers.Entities
{
    public class ProductsFilter
    {
        public decimal MinimumPrice { get; set; }
        public decimal MaximumPrice { get; set; }
        public int ProductTypeId { get; set; }
        public int ProductBrandId { get; set; }
        public short Size { get; set; }
        public Color Color { get; set; }
    }
}
