using ShoeStore.BL.Auth.Entities;

namespace ShoeStore.BL.Auth
{
    public interface IAuthProvider
    {
        Task<TokensResponse> AuthorizeCustomer(string email, string password);
        Task RegisterCustomer(string email, string password);
    }
}
