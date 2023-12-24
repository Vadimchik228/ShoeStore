namespace ShoeStore.BL.Entities.Customers.Entities
{
    public class CreateCustomerModel
    {
        public string Surname { get; set; }
        public string Name { get; set; }
        public string PhoneNumber { get; set; }
        public string PasswordHash { get; set; }
    }
}
