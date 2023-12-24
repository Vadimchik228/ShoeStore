using AutoMapper;
using ShoeStore.BL.Entities.Admins.Entities;
using ShoeStore.BL.Entities.Customers.Entities;
using ShoeStore.DataAccess.Entities;

namespace ShoeStore.BL.Mapper
{
    public class CustomerBLProfile : Profile
    {
        public CustomerBLProfile()
        {
            CreateMap<CustomerEntity, CustomerModel>()
                .ForMember(x => x.Id, y => y.MapFrom(src => src.ExternalId));

            CreateMap<CreateCustomerModel, CustomerEntity>()
                .ForMember(x => x.Id, y => y.Ignore())
                .ForMember(x => x.ExternalId, y => y.Ignore())
                .ForMember(x => x.ModificationTime, y => y.Ignore())
                .ForMember(x => x.CreationTime, y => y.Ignore());

            //CreateMap<UpdateCustomerModel, CustomerEntity>()
            //    .ForMember(x => x.Id, y => y.Ignore())
            //    .ForMember(x => x.ExternalId, y => y.Ignore())
            //    .ForMember(x => x.ModificationTime, y => y.Ignore())
            //    .ForMember(x => x.CreationTime, y => y.Ignore());
        }
    }
}
