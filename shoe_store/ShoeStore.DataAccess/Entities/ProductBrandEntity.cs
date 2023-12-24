using System.ComponentModel.DataAnnotations.Schema;

namespace ShoeStore.DataAccess.Entities
{
    [Table("productBrands")]
    public class ProductBrandEntity : BaseEntity
    {
        public string Name { get; set; }
        public virtual ICollection<ProductEntity> Products { get; set; }
    }
}