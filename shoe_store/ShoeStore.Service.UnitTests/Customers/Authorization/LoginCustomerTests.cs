using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.DependencyInjection;
using Newtonsoft.Json;
using NUnit.Framework;
using ShoeStore.BL.Auth.Entities;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using FluentAssertions;
using IdentityModel.Client;

namespace ShoeStore.Service.UnitTests.Customers.Authorization
{
    public class LoginCustomerTests : ShoeStoreServiceTestsBaseClass
    {
        [Test]
        public async Task SuccessFullResult()
        {
            var user = new CustomerEntity()
            {
                UserName = "test@test",
                Surname = "Test",
                Name = "Test",
                Patronymic = "Test",
                PhoneNumber = "Test",
                Email = "test@test",
                PasswordHash = "Test",
            };
            var password = "Password1@";

            using var scope = GetService<IServiceScopeFactory>().CreateScope();
            var userManager = scope.ServiceProvider.GetRequiredService<UserManager<CustomerEntity>>();
            var result = await userManager.CreateAsync(user, password);

            //execute
            var query = $"?email={user.UserName}&password={password}";
            var requestUri =
                ShoeStoreApiEndpoints.AuthorizeCustomerEndpoint + query; // /auth/login?login=test@test&password=password
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, requestUri);
            var client = TestHttpClient;
            var response = await client.SendAsync(request);

            var responseContentJson = await response.Content.ReadAsStringAsync();
            var content = JsonConvert.DeserializeObject<TokensResponse>(responseContentJson);

            content.Should().NotBeNull();
            content.AccessToken.Should().NotBeNull();
            content.RefreshToken.Should().NotBeNull();


            var requestToGetAllProducts =
                new HttpRequestMessage(HttpMethod.Get, ShoeStoreApiEndpoints.GetAllProductsEndpoint);

            var clientWithToken = TestHttpClient;
            client.SetBearerToken(content.AccessToken);
            var getAllCustomersResponse = await client.SendAsync(requestToGetAllProducts);

            getAllCustomersResponse.StatusCode.Should().Be(HttpStatusCode.OK);
        }

        [Test]
        public async Task BadRequestUserNotFoundResultTest()
        {
            // prepare: (imagine_login, imagine_password) => execute (try to login) => assert (BadRequest user not found)
            //prepare
            var login = "not_existing@mail.ru";
            using var scope = GetService<IServiceScopeFactory>().CreateScope();
            var userRepository = scope.ServiceProvider.GetRequiredService<IRepository<CustomerEntity>>();
            var user = userRepository.GetAll().FirstOrDefault(x => x.UserName.ToLower() == login.ToLower());
            if (user != null)
            {
                userRepository.Delete(user);
            }

            var password = "password";
            //100% confidence
            //execute
            var query = $"?email={login}&password={password}";
            var requestUri =
                ShoeStoreApiEndpoints.AuthorizeCustomerEndpoint + query; // /auth/login?login=test@test&password=password
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, requestUri);
            var response = await TestHttpClient.SendAsync(request);

            //assert
            response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        }

        [Test]
        public async Task PasswordIsIncorrectResultTest()
        {
            var customer = new CustomerEntity()
            {
                Email = "test@test",
                UserName = "test@test",
            };
            var password = "password";

            using var scope = GetService<IServiceScopeFactory>().CreateScope();
            var userManager = scope.ServiceProvider.GetService<UserManager<CustomerEntity>>();
            await userManager.CreateAsync(customer, password);

            var incorrect_password = "kvhdbkvhbk";

            //execute
            var query = $"?email={customer.UserName}&password={incorrect_password}";
            var requestUri =
                ShoeStoreApiEndpoints.AuthorizeCustomerEndpoint + query; // /auth/login?login=test@test&password=kvhdbkvhbk
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, requestUri);
            var client = TestHttpClient;
            var response = await client.SendAsync(request);

            //assert
            response.StatusCode.Should().Be(HttpStatusCode.BadRequest); // with some message
        }

        [Test]
        [TestCase("", "")]
        [TestCase("qwe", "")]
        [TestCase("test@test", "")]
        [TestCase("", "password")]
        public async Task LoginOrPasswordAreInvalidResultTest(string login, string password)
        {
            //execute
            var query = $"?login={login}&password={password}";
            var requestUri =
                ShoeStoreApiEndpoints.AuthorizeCustomerEndpoint + query; // /auth/login?login=test@test&password=kvhdbkvhbk
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, requestUri);
            var client = TestHttpClient;
            var response = await client.SendAsync(request);

            //assert
            response.StatusCode.Should().Be(HttpStatusCode.BadRequest); // with some message
        }

    }
}
