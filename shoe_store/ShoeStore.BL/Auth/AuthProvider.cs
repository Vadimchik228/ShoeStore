using IdentityModel.Client;
using Microsoft.AspNetCore.Identity;
using ShoeStore.BL.Auth.Entities;
using ShoeStore.DataAccess.Entities;

namespace ShoeStore.BL.Auth
{
    public class AuthProvider : IAuthProvider
    {
        private readonly SignInManager<CustomerEntity> _signInManager;
        private readonly UserManager<CustomerEntity> _customerManager;
        private readonly string _identityServerUri;
        private readonly string _clientId;
        private readonly string _clientSecret;

        public AuthProvider(SignInManager<CustomerEntity> signInManager, UserManager<CustomerEntity> customerManager,
            string identityServerUri,
            string clientId,
            string clientSecret)
        {
            _signInManager = signInManager;
            _customerManager = customerManager;
            _identityServerUri = identityServerUri;
            _clientId = clientId;
            _clientSecret = clientSecret;
        }

        public async Task<TokensResponse> AuthorizeCustomer(string email, string password)
        {
            var customer = await _customerManager.FindByEmailAsync(email); 
            if (customer is null)
            {
                throw new Exception();
            }


            var verificationPasswordResult = await _signInManager.CheckPasswordSignInAsync(customer, password, false);
            if (!verificationPasswordResult.Succeeded)
            {
                throw new Exception(); 
            }

            var client = new HttpClient();
            var discoveryDoc = await client.GetDiscoveryDocumentAsync(_identityServerUri); 
            if (discoveryDoc.IsError)
            {
                throw new Exception();
            }

            var tokenResponse = await client.RequestPasswordTokenAsync(new PasswordTokenRequest()
            {
                Address = discoveryDoc.TokenEndpoint,
                ClientId = _clientId,
                ClientSecret = _clientSecret,
                UserName = customer.UserName,
                Password = password,
                Scope = "api offline_access"
            });

            if (tokenResponse.IsError)
            {
                throw new Exception();
            }

            return new TokensResponse()
            {
                AccessToken = tokenResponse.AccessToken,
                RefreshToken = tokenResponse.RefreshToken
            };
        }

        public async Task RegisterCustomer(string email, string password)
        {
            CustomerEntity customerEntity = new CustomerEntity()
            {
                Email = email,
                UserName = email
            };

            var createCustomerResult = await _customerManager.CreateAsync(customerEntity, password);

        }
    }
}
