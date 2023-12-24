using AutoMapper;
using ShoeStore.BL.Entities.Customers.Entities;
using ShoeStore.Service.Controllers.Entities;

namespace ShoeStore.Service.Mapper
{
    public class CustomersWebAPIProfile : Profile
    {
        public CustomersWebAPIProfile()
        {
            CreateMap<CustomersFilter, CustomerModelFilter>();
            CreateMap<CreateCustomerRequest, CreateCustomerModel>();
            CreateMap<UpdateCustomerRequest, UpdateCustomerModel>();
        }
    }
}
