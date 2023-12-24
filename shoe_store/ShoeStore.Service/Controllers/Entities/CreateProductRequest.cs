using System.ComponentModel.DataAnnotations;

namespace ShoeStore.Service.Controllers.Entities
{
    public class CreateProductRequest
    {
        [Required]
        [MinLength(2)]
        public string Name { get; set; }

        [Required]
        public decimal Price { get; set; }

        [Required]
        public int ProductTypeId { get; set; }

        [Required]
        public int ProductBrandId { get; set; }

        [Required]
        public short Size { get; set; }
    }
}
