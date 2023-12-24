using System.ComponentModel.DataAnnotations.Schema;

namespace ShoeStore.DataAccess.Entities
{
    public enum PaymentMethod
    {
        Cash,
        NonCash
    }

    public enum StatusOfOrder
    {
        Adopted, 
        ReadyToShip,
        OnTheWay, 
        ReadyToIssue 
    }

    [Table("orders")]
    public class OrderEntity : BaseEntity
    {
        public int ShoppingBasketId { get; set; }
        public ShoppingBasketEntity ShoppingBasket { get; set; }
        public int PointOfDeliveryId { get; set; }
        public PointOfDeliveryEntity PointOfDelivery { get; set; }
        public int? PromocodeId { get; set; }
        public PromocodeEntity? Promocode { get; set; }
        public StatusOfOrder Status { get; set; }
        public PaymentMethod Method { get; set; }
    }
}
