using ShoeStore.BL.Entities.Admins.Entities;

namespace ShoeStore.BL.Entities.Admins
{
    public interface IAdminsProvider
    {
        IEnumerable<AdminModel> GetAdmins(AdminModelFilter modelFilter = null);
        AdminModel GetAdminInfo(Guid id);
    }
}
