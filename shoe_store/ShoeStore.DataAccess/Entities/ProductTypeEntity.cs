using System.ComponentModel.DataAnnotations.Schema;

namespace ShoeStore.DataAccess.Entities
{
    [Table("productTypes")]
    public class ProductTypeEntity : BaseEntity
    {
        public string Name { get; set; }
        public virtual ICollection<ProductEntity> Products { get; set; }
    }
}