using ShoeStore.BL.Entities.Customers.Entities;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using AutoMapper;

namespace ShoeStore.BL.Entities.Customers
{
    public class CustomersProvider : ICustomersProvider
    {
        private readonly IRepository<CustomerEntity> _customerRepository;
        private readonly IMapper _mapper;

        public CustomersProvider(IRepository<CustomerEntity> customersRepository, IMapper mapper)
        {
            _customerRepository = customersRepository;
            _mapper = mapper;
        }

        public IEnumerable<CustomerModel> GetCustomers(CustomerModelFilter modelFilter = null)
        {
            var surname = modelFilter.Surname;
            var phoneNumber = modelFilter.PhoneNumber;

            var customers = _customerRepository.GetAll(x =>
            (surname == null || surname == x.Surname) &&
            (phoneNumber == null || phoneNumber == x.PhoneNumber));


            return _mapper.Map<IEnumerable<CustomerModel>>(customers);
        }

        public CustomerModel GetCustomerInfo(Guid id)
        {
            var customer = _customerRepository.GetById(id);
            if (customer is null)
                throw new ArgumentException("Customer not found.");

            return _mapper.Map<CustomerModel>(customer);
        }
    }
}
