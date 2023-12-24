using System.ComponentModel.DataAnnotations;

namespace ShoeStore.Service.Controllers.Entities
{
    public class CreateAdminRequest
    {
        [Required]
        [MinLength(2)]
        public string Surname { get; set; }

        [Required]
        [MinLength(2)]
        public string Name { get; set; }

        [Required]
        [MinLength(11)]
        public string PhoneNumber { get; set; }
        public string Email { get; set; }

        [Required]
        [MinLength(10)]
        public string PasswordHash { get; set; }
    }
}
