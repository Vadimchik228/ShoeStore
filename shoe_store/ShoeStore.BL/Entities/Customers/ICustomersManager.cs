using ShoeStore.BL.Entities.Customers.Entities;

namespace ShoeStore.BL.Entities.Customers
{
    public interface ICustomersManager
    {
        CustomerModel CreateCustomer(CreateCustomerModel model);
        void DeleteCustomer(Guid id);
        CustomerModel UpdateCustomer(Guid id, UpdateCustomerModel model);
    }
}
