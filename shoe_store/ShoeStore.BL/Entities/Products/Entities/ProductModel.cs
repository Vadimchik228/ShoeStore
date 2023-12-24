using System.Drawing;

namespace ShoeStore.BL.Entities.Products.Entities
{
    public class ProductModel
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public decimal Price { get; set; }
        public string PictureUrl { get; set; }
        public int ProductTypeId { get; set; }
        public int ProductBrandId { get; set; }
        public Color Color { get; set; }
        public short Size { get; set; }
    }

}
