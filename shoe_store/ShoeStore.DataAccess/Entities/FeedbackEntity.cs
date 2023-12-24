using System.ComponentModel.DataAnnotations.Schema;

namespace ShoeStore.DataAccess.Entities
{
    [Table("feedbacks")]
    public class FeedbackEntity : BaseEntity
    {
        public double Rating { get; set; }
        public string Comment { get; set; }
        public int CustomerId { get; set; }
        public CustomerEntity Customer { get; set; }
        public int ProductId { get; set; }
        public ProductEntity Product { get; set; }
    }
}
