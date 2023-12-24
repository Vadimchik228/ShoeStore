namespace ShoeStore.Service.Controllers.Entities
{
    public class UpdateAdminRequest
    {
        public string PhoneNumber { get; set; }
        public string Email { get; set; }
        public string PasswordHash { get; set; }
    }
}
