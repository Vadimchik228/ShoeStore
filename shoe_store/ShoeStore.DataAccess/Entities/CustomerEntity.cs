using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.AspNetCore.Identity;

namespace ShoeStore.DataAccess.Entities
{
    [Table("customers")]
    public class CustomerEntity : IdentityUser<int>, IBaseEntity
    {
        public Guid ExternalId { get; set; }
        public DateTime ModificationTime { get; set; }
        public DateTime CreationTime { get; set; }

        public string Surname { get; set; }
        public string Name { get; set; }
        public string Patronymic { get; set; }
        public string PhoneNumber { get; set; }
        public string Email { get; set; }
        public string PasswordHash { get; set; }

        public virtual ICollection<FeedbackEntity> Feedbacks { get; set; }
        public virtual ICollection<ShoppingBasketEntity> ShoppingBaskets { get; set; }
    }

    public class CustomerRoleEntity : IdentityRole<int>
    {
    }

}
