using AutoMapper;
using ShoeStore.BL.Entities.Admins.Entities;
using ShoeStore.Service.Controllers.Entities;

namespace ShoeStore.Service.Mapper
{
    public class AdminsWebAPIProfile : Profile
    {
        public AdminsWebAPIProfile()
        {
            CreateMap<AdminsFilter, AdminModelFilter>();
            CreateMap<CreateAdminRequest, CreateAdminModel>();
            CreateMap<UpdateAdminRequest, UpdateAdminModel>();
        }
    }
}
