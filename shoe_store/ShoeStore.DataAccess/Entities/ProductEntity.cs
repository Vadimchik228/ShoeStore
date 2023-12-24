using System.ComponentModel.DataAnnotations.Schema;
using System.Drawing;

namespace ShoeStore.DataAccess.Entities
{
    [Table("products")]
    public class ProductEntity : BaseEntity
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public decimal Price { get; set; }
        public string PictureUrl { get; set; }
        public ProductTypeEntity ProductType { get; set; }
        public int ProductTypeId { get; set; }
        public ProductBrandEntity ProductBrand { get; set; }
        public int ProductBrandId { get; set; }
        [NotMapped]
        public Color Color { get; set; }
        public short Size { get; set; }
        public virtual ICollection<ShoppingBasketEntity> ShoppingBaskets { get; set; }
        public virtual ICollection<FeedbackEntity> Feedbacks { get; set; }
    }

}
