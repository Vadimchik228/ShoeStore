using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using AutoMapper;
using ShoeStore.BL.Entities.Admins.Entities;

namespace ShoeStore.BL.Entities.Admins
{
    public class AdminsProvider : IAdminsProvider
    {
        private readonly IRepository<AdminEntity> _adminRepository;
        private readonly IMapper _mapper;

        public AdminsProvider(IRepository<AdminEntity> adminsRepository, IMapper mapper)
        {
            _adminRepository = adminsRepository;
            _mapper = mapper;
        }

        public IEnumerable<AdminModel> GetAdmins(AdminModelFilter modelFilter = null)
        {
            var surname = modelFilter.Surname;
            var phoneNumber = modelFilter.PhoneNumber;
            var email = modelFilter.Email;

            var admins = _adminRepository.GetAll(x =>
            (surname == null || surname == x.Surname) &&
            (phoneNumber == null || phoneNumber == x.PhoneNumber) &&
            (email == null || email == x.Email));


            return _mapper.Map<IEnumerable<AdminModel>>(admins);
        }

        public AdminModel GetAdminInfo(Guid id)
        {
            var admin = _adminRepository.GetById(id);
            if (admin is null)
                throw new ArgumentException("Admin not found.");

            return _mapper.Map<AdminModel>(admin);
        }

    }
}
