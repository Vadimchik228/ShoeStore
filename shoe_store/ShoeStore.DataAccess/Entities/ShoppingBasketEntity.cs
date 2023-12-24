using System.ComponentModel.DataAnnotations.Schema;

namespace ShoeStore.DataAccess.Entities
{
    [Table("shopping_baskets")]
    public class ShoppingBasketEntity : BaseEntity
    {
        public int CustomerId { get; set; }
        public CustomerEntity Customer { get; set; }

        public int ProductId { get; set; }
        public ProductEntity Product { get; set; }

        public int Quantity { get; set; }
        public int TotalAmount { get; set; }

        public virtual ICollection<OrderEntity> Orders { get; set; }
    }
}
