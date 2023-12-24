namespace ShoeStore.BL.Entities.Admins.Entities
{
    public class CreateAdminModel
    {
        public string Surname { get; set; }
        public string Name { get; set; }
        public string PhoneNumber { get; set; }
        public string Email { get; set; }
        public string PasswordHash { get; set; }
    }
}
