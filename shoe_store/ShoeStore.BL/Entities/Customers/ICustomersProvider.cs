using ShoeStore.BL.Entities.Customers.Entities;

namespace ShoeStore.BL.Entities.Customers
{
    public interface ICustomersProvider
    {
        IEnumerable<CustomerModel> GetCustomers(CustomerModelFilter modelFilter = null);
        CustomerModel GetCustomerInfo(Guid id);
    }
}
