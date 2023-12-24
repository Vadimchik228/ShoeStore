using ShoeStore.BL.Entities.Admins.Entities;

namespace ShoeStore.BL.Entities.Admins
{
    public interface IAdminsManager
    {
        AdminModel CreateAdmin(CreateAdminModel model);
        void DeleteAdmin(Guid id);
        AdminModel UpdateAdmin(Guid id, UpdateAdminModel model);

    }
}
